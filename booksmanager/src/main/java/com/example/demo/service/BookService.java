package com.example.demo.service;



import com.example.demo.common.BaseResult;
import com.example.demo.domain.dto.BookDTO;
import com.example.demo.domain.dto.BookBatchDTO;
import com.example.demo.domain.dto.BookFindPageDTO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
public interface BookService {
    BaseResult<?> save(BookDTO bookDTO);
    BaseResult<?> update(BookDTO bookDTO);
    BaseResult<?> deleteBatch(BookBatchDTO deleteBatchDTO);
    BaseResult<?> detail(BookBatchDTO bookBatchDTO);

    BaseResult<?> findPage( BookFindPageDTO bookFindPageDTO);
}
