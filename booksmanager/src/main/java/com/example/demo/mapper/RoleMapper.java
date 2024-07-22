package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
