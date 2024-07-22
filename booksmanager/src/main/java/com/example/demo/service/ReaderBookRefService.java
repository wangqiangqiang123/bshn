package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.BaseResult;
import com.example.demo.domain.dto.BookLendBackDTO;
import com.example.demo.domain.dto.LendFindPageDTO;
import com.example.demo.model.ReaderBookRef;

/**
 * <p>
 * 读者图书关联关系表 服务类
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
public interface ReaderBookRefService  {
    BaseResult<?> lend(BookLendBackDTO bookLendBackDTO);
    BaseResult<?> back( BookLendBackDTO bookLendBackDTO);

    BaseResult<?> findPage( LendFindPageDTO lendFindPageDTO);
}
