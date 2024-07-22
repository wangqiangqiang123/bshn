package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.UserRoleRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 Mapper 接口
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Mapper
public interface UserRoleRefMapper extends BaseMapper<UserRoleRef> {

    List<String> selectMenuList(@Param("userId") Long userId);
}
