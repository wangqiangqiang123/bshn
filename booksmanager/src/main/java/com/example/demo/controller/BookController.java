package com.example.demo.controller;


import com.example.demo.common.BaseResult;
import com.example.demo.domain.dto.BookDTO;
import com.example.demo.domain.dto.BookBatchDTO;
import com.example.demo.domain.dto.BookFindPageDTO;
import com.example.demo.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *   前端控制器
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Api(tags="图书管理")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;


    @ApiOperation(value = "图书新增接口",notes = "图书新增")
    @PostMapping("/save")
    public BaseResult<?> save(@RequestBody BookDTO bookDTO){
       return bookService.save(bookDTO);
    }

    @ApiOperation(value = "根据图书编号修改图书接口",notes = "图书修改接口")
    @PostMapping("/update")
    public BaseResult<?> update(@RequestBody BookDTO bookDTO){
        return bookService.update(bookDTO);
    }

    //批量删除
    @ApiOperation(value = "图书批量删除接口",notes = "图书批量删除")
    @PostMapping("/deleteBatch")
    public BaseResult<?> deleteBatch(@RequestBody BookBatchDTO bookBatchDTO){
        bookService.deleteBatch(bookBatchDTO);
        return BaseResult.success();
    }

    //查看详情
    @ApiOperation(value = "图书查看详情接口",notes = "图书查看详情")
    @PostMapping("/detail")
    public BaseResult<?> detail(@RequestBody BookBatchDTO bookBatchDTO){
        return bookService.detail(bookBatchDTO);
    }
    //列表分页查询
    @ApiOperation(value = "图书列表分页查询接口",notes = "图书列表分页查询")
    @PostMapping("/findPage")
    public BaseResult<?> findPage(@RequestBody BookFindPageDTO bookFindPageDTO){
       return bookService.findPage(bookFindPageDTO);
    }
}
