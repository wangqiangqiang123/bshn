package com.example.demo.service.impl;



import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.BaseResult;
import com.example.demo.common.Constant;
import com.example.demo.domain.dto.*;
import com.example.demo.domain.vo.TokenVO;
import com.example.demo.domain.vo.UserVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserRoleRefMapper;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserRoleRef;
import com.example.demo.service.UserService;
import com.example.demo.utils.RegexUtils;
import com.example.demo.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Slf4j
@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleRefMapper userRoleRefMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult<?> register(UserDTO userDTO) {
        log.info("UserServiceImpl.register.inputParam={}", JSON.toJSONString(userDTO));
        if (RegexUtils.isPasswordInvalid(userDTO.getUsername())) {
            return BaseResult.error(-1, "用户名必须是6~12,数字、字母、下划线");
        }
        if (RegexUtils.isPasswordInvalid(userDTO.getPassword())) {
            return BaseResult.error(-1, "密码必须是6~12,数字、字母、下划线");
        }
        if (RegexUtils.isPhoneInvalid(userDTO.getPhone())) {
            return BaseResult.error(-1,"手机号格式错误");
        }
        if (StringUtils.isNotBlank(userDTO.getEmail())) {
            if (RegexUtils.isEmailInvalid(userDTO.getEmail())) {
                return BaseResult.error(-1,"邮箱格式错误");
            }
        }

        if ((!"男".equals(userDTO.getSex()) && !"女".equals(userDTO.getSex()))) {
            return BaseResult.error(-1,"性别必须是男或女");
        }

        if (!"1".equals(userDTO.getRole()) && !"2".equals(userDTO.getRole())){
                return BaseResult.error(-1,"角色必须选择图书管理员或或者普通用户!");

        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (res != null) {
            log.error("用户名已重复,username={}", user.getUsername());
            return BaseResult.error(-1, "用户名已重复");
        }
        Date now=new Date();
        user.setUpdateTime(now);
        user.setCreateTime(now);
        user.setUpdateBy("系统");
        user.setCreateBy("系统");
        user.setCreateId(-1L);
        user.setUpdateId(-1L);
        userMapper.insert(user);

        UserRoleRef userRoleRef=new UserRoleRef();
        userRoleRef.setUserId(user.getId());
        userRoleRef.setUpdateTime(now);
        userRoleRef.setCreateTime(now);
        userRoleRef.setUpdateBy("系统");
        userRoleRef.setCreateBy("系统");
        userRoleRef.setCreateId(-1L);
        userRoleRef.setUpdateId(-1L);
        String roleCode="";
        if("1".equals(userDTO.getRole())){
            roleCode="manager";
        }else{
            roleCode="general";
        }
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<Role>();
        wrapper.eq(Role::getRolecode,roleCode);
        Role role=roleMapper.selectOne(wrapper);
        userRoleRef.setRoleId(role.getId());
        userRoleRefMapper.insert(userRoleRef);
        LoginTokenUserDTO loginTokenUser=new LoginTokenUserDTO();
        BeanUtils.copyProperties(user,loginTokenUser);
        List<String> menuList=userRoleRefMapper.selectMenuList(user.getId());
        loginTokenUser.setMenuList(menuList);
        String token = TokenUtils.genToken(loginTokenUser);
        redisTemplate.opsForValue().set(Constant.TOKEN_USER_KEY+loginTokenUser.getUsername(), token, Constant.EXPIRE_TIME, TimeUnit.SECONDS);
        TokenVO tokenVO=new TokenVO();
        tokenVO.setToken(token);
        tokenVO.setExpireTime(Constant.EXPIRE_TIME*1000L);
        return BaseResult.success(tokenVO);
    }

    @Override
    public BaseResult<?> login(LoginUserDTO loginUserDTO) {
        log.info("UserServiceImpl.login.inputParam={}", JSON.toJSONString(loginUserDTO));
        User user = new User();
        BeanUtils.copyProperties(loginUserDTO, user);
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if (res == null) {
            return BaseResult.error(-1, "用户名或密码错误");
        }
        LoginTokenUserDTO loginTokenUser=new LoginTokenUserDTO();
        BeanUtils.copyProperties(res,loginTokenUser);
        List<String> menuList=userRoleRefMapper.selectMenuList(res.getId());
        loginTokenUser.setMenuList(menuList);
        String token = TokenUtils.genToken(loginTokenUser);
        redisTemplate.opsForValue().set(Constant.TOKEN_USER_KEY+user.getUsername(), token, Constant.EXPIRE_TIME, TimeUnit.SECONDS);
        TokenVO tokenVO=new TokenVO();
        tokenVO.setToken(token);
        tokenVO.setExpireTime(Constant.EXPIRE_TIME*1000L);
        return BaseResult.success(tokenVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult<?> deleteBatch(UserBatchDTO userBatchDTO) {
        if(userBatchDTO==null || userBatchDTO.getIds()==null || userBatchDTO.getIds().size()==0){
            log.error("bookServiceImpl.deleteBatch:入参错误");
            throw new BusinessException(-1,"入参错误");
        }
        log.info("userServiceImpl.deleteBatch.inputparam={}", JSON.toJSONString(userBatchDTO));
        userMapper.deleteBatchIds(userBatchDTO.getIds());
        LambdaQueryWrapper<UserRoleRef> wrapper = new LambdaQueryWrapper<UserRoleRef>();
        wrapper.in(UserRoleRef::getUserId,userBatchDTO.getIds());
        userRoleRefMapper.delete(wrapper);
        return BaseResult.success();
    }

    @Override
    public BaseResult<?> findPage(UserFindPageDTO userFindPageDTO) {
        log.info("userServiceImpl.findPage.inputparam={}", JSON.toJSONString(userFindPageDTO));
        if(userFindPageDTO.getPageNum()==null){
            userFindPageDTO.setPageNum(1);
        }
        if(userFindPageDTO.getPageSize()==null){
            userFindPageDTO.setPageSize(10);
        }
        LambdaQueryWrapper<User> wrappers = Wrappers.lambdaQuery();
        if(StringUtils.isNotBlank(userFindPageDTO.getUsername())){
            wrappers.like(User::getUsername,userFindPageDTO.getUsername());
        }
        if(StringUtils.isNotBlank(userFindPageDTO.getNickname())){
            wrappers.like(User::getNickName,userFindPageDTO.getNickname());
        }
        if(StringUtils.isNotBlank(userFindPageDTO.getPhone())){
            wrappers.like(User::getPhone,userFindPageDTO.getPhone());
        }
        wrappers.orderByDesc(User::getCreateTime);
        Page<User> bookPage =userMapper.selectPage(new Page<>(userFindPageDTO.getPageNum(),userFindPageDTO.getPageSize()), wrappers);
        Page<UserVO> bps=new Page();
        List<UserVO> list=new ArrayList<UserVO>();
        if(bookPage!=null && bookPage.getRecords()!=null && bookPage.getRecords().size()>0){
            bps.setCurrent(bookPage.getCurrent());
            bps.setTotal(bookPage.getPages());
            bps.setSize(bookPage.getSize());
            for(User us:bookPage.getRecords()){
                UserVO uo=new UserVO();
                BeanUtils.copyProperties(us,uo);
                list.add(uo);
            }
        }
        bps.setRecords(list);
        return BaseResult.success(bps);
    }

    @Override
    public BaseResult<?> password(UserPasswordDTO userPasswordDTO) {
        log.info("UserServiceImpl.password.inputParam={}", JSON.toJSONString(userPasswordDTO));
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getPassword,userPasswordDTO.getOldPassword()).eq(User::getUsername,userPasswordDTO.getUserName());
        User user = userMapper.selectOne(wrapper);
        if(user==null){
            log.error("用户名或旧密码错误");
            return BaseResult.error(-1, "用户名或旧密码错误");
        }
        if (RegexUtils.isPasswordInvalid(userPasswordDTO.getNewPassword())) {
            return BaseResult.error(-1, "新密码必须是6~12,数字、字母、下划线");
        }
        if(userPasswordDTO.getOldPassword().equals(userPasswordDTO.getNewPassword())){
            return BaseResult.error(-1, "旧密码和新密码不能相同!");
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Constant.HEADER_TOKEN);
        LoginTokenUserDTO loginTokenUser= TokenUtils.getUserBytoken(token);
        user.setPassword(userPasswordDTO.getNewPassword());
        user.setUpdateId(loginTokenUser.getId());
        user.setUpdateBy(loginTokenUser.getUsername());
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
        return BaseResult.success();
    }

    @Override
    public BaseResult<?> update(UserUpdateDTO userUpdateDTO) {
        log.info("UserServiceImpl.update.inputParam={}", JSON.toJSONString(userUpdateDTO));
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getId,userUpdateDTO.getId());
        User user = userMapper.selectOne(wrapper);
        if(user==null){
            return BaseResult.error(-1, "用户不存在");
        }
        if(StringUtils.isNotBlank(userUpdateDTO.getPhone())){
            if (RegexUtils.isPhoneInvalid(userUpdateDTO.getPhone())) {
                return BaseResult.error(-1,"手机号格式错误");
            }
            user.setPhone(userUpdateDTO.getPhone());
        }
        if (StringUtils.isNotBlank(userUpdateDTO.getEmail())) {
            if (RegexUtils.isEmailInvalid(userUpdateDTO.getEmail())) {
                return BaseResult.error(-1,"邮箱格式错误");
            }
            user.setEmail(userUpdateDTO.getEmail());
        }

        if (StringUtils.isNotBlank(userUpdateDTO.getSex())) {
            if (!"男".equals(userUpdateDTO.getSex()) && !"女".equals(userUpdateDTO.getSex())) {
                return BaseResult.error(-1,"性别必须是男或女");
            }
            user.setSex(userUpdateDTO.getSex());
        }

        if (StringUtils.isNotBlank(userUpdateDTO.getNickName())) {
            user.setNickName(userUpdateDTO.getNickName());
        }

        if (StringUtils.isNotBlank(userUpdateDTO.getAddress())) {
            user.setAddress(userUpdateDTO.getAddress());
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Constant.HEADER_TOKEN);
        LoginTokenUserDTO loginTokenUser= TokenUtils.getUserBytoken(token);
        user.setUpdateId(loginTokenUser.getId());
        user.setUpdateBy(loginTokenUser.getUsername());
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
        return BaseResult.success();
    }
}
