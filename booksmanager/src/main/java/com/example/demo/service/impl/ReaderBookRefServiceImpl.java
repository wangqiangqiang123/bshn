package com.example.demo.service.impl;



import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.BaseResult;
import com.example.demo.common.Constant;
import com.example.demo.domain.dto.BookLendBackDTO;
import com.example.demo.domain.dto.LendFindPageDTO;
import com.example.demo.domain.dto.LoginTokenUserDTO;

import com.example.demo.domain.vo.ReaderBookRefVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.LendRecordMapper;
import com.example.demo.mapper.ReaderBookRefMapper;
import com.example.demo.model.Book;
import com.example.demo.model.LendRecord;
import com.example.demo.model.ReaderBookRef;
import com.example.demo.service.ReaderBookRefService;
import com.example.demo.utils.DateUtils;
import com.example.demo.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 读者图书关联关系表 服务实现类
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Slf4j
@Service
public class ReaderBookRefServiceImpl  implements ReaderBookRefService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ReaderBookRefMapper readerBookRefMapper;

    @Autowired
    private LendRecordMapper lendRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResult<?> lend(BookLendBackDTO bookLendBackDTO) {
        if(StringUtils.isBlank(bookLendBackDTO.getBookNo())){
            log.error("readerBookRefServiceImpl.lend:入参错误");
            throw new BusinessException(-1,"入参错误");
        }
        log.info("readerBookRefServiceImpl.lend.inputparam={}", JSON.toJSONString(bookLendBackDTO));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Constant.HEADER_TOKEN);
        LoginTokenUserDTO loginTokenUser= TokenUtils.getUserBytoken(token);
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Book::getBookNo,bookLendBackDTO.getBookNo());
        Book bk = bookMapper.selectOne(wrapper);
        if (bk == null){
            return BaseResult.error(-1,"图书已下架!");
        }
        LambdaQueryWrapper<ReaderBookRef>  wr= Wrappers.lambdaQuery();
        wr.eq(ReaderBookRef::getBookId,bk.getId());
        ReaderBookRef bkf = readerBookRefMapper.selectOne(wr);
        if (bkf != null){
            return BaseResult.error(-1,"图书已借出!");
        }
        ReaderBookRef readerBookRef=new ReaderBookRef();
        readerBookRef.setBookId(bk.getId());
        readerBookRef.setBookNo(bookLendBackDTO.getBookNo());
        readerBookRef.setReaderId(loginTokenUser.getId());
        Date now=new Date();
        readerBookRef.setLendTime(now);
        readerBookRef.setDeadTime(DateUtils.add(now,30));
        readerBookRef.setCreateTime(now);
        readerBookRef.setUpdateTime(now);
        readerBookRef.setUpdateId(loginTokenUser.getId());
        readerBookRef.setCreateId(loginTokenUser.getId());
        readerBookRef.setUpdateBy(loginTokenUser.getUsername());
        readerBookRef.setCreateBy(loginTokenUser.getUsername());
        LendRecord lr=new LendRecord();
        BeanUtils.copyProperties(readerBookRef,lr);
        lr.setStatus("0");
        readerBookRefMapper.insert(readerBookRef);
        lendRecordMapper.insert(lr);
        return BaseResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult<?> back(BookLendBackDTO bookLendBackDTO) {
        if(StringUtils.isBlank(bookLendBackDTO.getBookNo())){
            log.error("readerBookRefServiceImpl.lend:入参错误");
            throw new BusinessException(-1,"入参错误");
        }
        log.info("readerBookRefServiceImpl.back.inputparam={}", JSON.toJSONString(bookLendBackDTO));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Constant.HEADER_TOKEN);
        LoginTokenUserDTO loginTokenUser= TokenUtils.getUserBytoken(token);
        LambdaQueryWrapper<ReaderBookRef> wr = Wrappers.lambdaQuery();
        wr.eq(ReaderBookRef::getBookNo,bookLendBackDTO.getBookNo());
        ReaderBookRef bk = readerBookRefMapper.selectOne(wr);
        if(bk==null){
            return BaseResult.error(-1,"图书未借出!");
        }
        LambdaQueryWrapper<ReaderBookRef> wrapper = new LambdaQueryWrapper<ReaderBookRef>();
        wrapper.eq(ReaderBookRef::getBookNo,bookLendBackDTO.getBookNo());
        LendRecord lr=new LendRecord();
        lr.setBookId(bk.getBookId());
        lr.setBookNo(bookLendBackDTO.getBookNo());
        lr.setReaderId(loginTokenUser.getId());
        Date now=new Date();
        lr.setCreateTime(now);
        lr.setUpdateTime(now);
        lr.setReturnTime(now);
        lr.setUpdateId(loginTokenUser.getId());
        lr.setCreateId(loginTokenUser.getId());
        lr.setUpdateBy(loginTokenUser.getUsername());
        lr.setCreateBy(loginTokenUser.getUsername());
        lr.setStatus("1");
        lendRecordMapper.insert(lr);
        readerBookRefMapper.delete(wrapper);
        return BaseResult.success();
    }

    @Override
    public BaseResult<?> findPage(LendFindPageDTO lendFindPageDTO) {
        log.info("readerBookRefServiceImpl.findPage.inputparam={}", JSON.toJSONString(lendFindPageDTO));
        if(lendFindPageDTO.getPageNum()==null){
            lendFindPageDTO.setPageNum(1);
        }
        if(lendFindPageDTO.getPageSize()==null){
            lendFindPageDTO.setPageSize(10);
        }
        lendFindPageDTO.setStartIndex((lendFindPageDTO.getPageNum()-1)*lendFindPageDTO.getPageSize());
        Page<ReaderBookRefVO> bps=new Page();
        int count= readerBookRefMapper.selectCount(lendFindPageDTO);
        if(count==0){
           return  BaseResult.success(bps);
        }
        List<ReaderBookRefVO> list= readerBookRefMapper.selectlist(lendFindPageDTO);
        bps.setSize(lendFindPageDTO.getPageSize());
        bps.setTotal(count);
        bps.setCurrent(lendFindPageDTO.getPageNum());
        bps.setSize(lendFindPageDTO.getPageSize());
        bps.setRecords(list);
        return  BaseResult.success(bps);
    }
}
