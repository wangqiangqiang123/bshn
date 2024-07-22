package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.Auth;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Mapper
public interface AuthMapper extends BaseMapper<Auth> {

}
