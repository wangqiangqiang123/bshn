package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.dto.LendFindPageDTO;
import com.example.demo.domain.vo.ReaderBookRefVO;
import com.example.demo.model.ReaderBookRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 读者图书关联关系表 Mapper 接口
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Mapper
public interface ReaderBookRefMapper extends BaseMapper<ReaderBookRef> {

    List<ReaderBookRefVO> selectlist(@Param("data") LendFindPageDTO lendFindPageDTO);
    int selectCount(@Param("data") LendFindPageDTO lendFindPageDTO);
}
