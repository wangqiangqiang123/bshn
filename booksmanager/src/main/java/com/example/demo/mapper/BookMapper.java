package com.example.demo.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

}
