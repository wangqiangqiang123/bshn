package com.example.demo.controller;


import com.example.demo.common.BaseResult;
import com.example.demo.domain.dto.BookLendBackDTO;
import com.example.demo.domain.dto.LendFindPageDTO;

import com.example.demo.service.LendRecordService;
import com.example.demo.service.ReaderBookRefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * <p>
 * 读者图书关联关系表 前端控制器
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Api(tags="借阅管理")
@RestController
@RequestMapping("/readerbookref")
public class ReaderBookRefController {

    @Autowired
    private LendRecordService lendRecordService;

    @Autowired
    private ReaderBookRefService readerBookRefService;


    @ApiOperation(value = "图书借阅接口",notes = "图书借阅")
    @PostMapping("/lend")
    public BaseResult<?> lend(@RequestBody BookLendBackDTO bookLendBackDTO){
        return readerBookRefService.lend(bookLendBackDTO);
    }
    @ApiOperation(value = "图书归还接口",notes = "图书归还")
    @PostMapping("/back")
    public BaseResult<?> back(@RequestBody BookLendBackDTO bookLendBackDTO){
        return readerBookRefService.back(bookLendBackDTO);
    }
    @ApiOperation(value = "查看当前借阅图书人员接口",notes = "当前借阅图书人员")
    @PostMapping("/findPage")
    public BaseResult<?> findPage(@RequestBody LendFindPageDTO lendFindPageDTO){
    return readerBookRefService.findPage(lendFindPageDTO);
    }
}
