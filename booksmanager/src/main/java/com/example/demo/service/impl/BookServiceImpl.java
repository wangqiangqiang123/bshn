package com.example.demo.service.impl;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.BaseResult;
import com.example.demo.common.Constant;
import com.example.demo.domain.dto.BookDTO;
import com.example.demo.domain.dto.BookBatchDTO;
import com.example.demo.domain.dto.BookFindPageDTO;
import com.example.demo.domain.dto.LoginTokenUserDTO;
import com.example.demo.domain.vo.BookVO;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.ReaderBookRefMapper;
import com.example.demo.model.Book;
import com.example.demo.model.ReaderBookRef;
import com.example.demo.service.BookService;
import com.example.demo.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WQQ
 * @since 2024-07-21
 */
@Slf4j
@Service
public class BookServiceImpl  implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ReaderBookRefMapper readerBookRefMapper;
    public BaseResult<?> save(BookDTO bookDTO){
        log.info("bookServiceImpl.save.inputparam={}", JSON.toJSONString(bookDTO));
        if(StringUtils.isBlank(bookDTO.getBookNo())){
            return BaseResult.error(-1,"图书编号为空!");
        }
        if(bookDTO.getPrice()!=null && (0>bookDTO.getPrice().compareTo(BigDecimal.ZERO))){
            return BaseResult.error(-1,"价格不小于0.0!");
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Constant.HEADER_TOKEN);
        LoginTokenUserDTO loginTokenUser= TokenUtils.getUserBytoken(token);
        Book book=new Book();
        BeanUtils.copyProperties(bookDTO,book);
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Book::getBookNo,book.getBookNo());
        Book bk = bookMapper.selectOne(wrapper);
        if (bk != null){
            return BaseResult.error(-1,"图书编号已存在!");
        }
        book.setCreateBy(loginTokenUser.getUsername());
        book.setCreateId(loginTokenUser.getId());
        book.setUpdateBy(loginTokenUser.getUsername());
        book.setUpdateId(loginTokenUser.getId());
        Date now=new Date();
        book.setCreateTime(now);
        book.setUpdateTime(now);
        bookMapper.insert(book);
        return BaseResult.success();
    }

    @Override
    public BaseResult<?> update(BookDTO bookDTO) {
        log.info("bookServiceImpl.update.inputparam={}", JSON.toJSONString(bookDTO));
        if(bookDTO.getPrice()!=null && (0>bookDTO.getPrice().compareTo(BigDecimal.ZERO))){
            return BaseResult.error(-1,"价格不小于0.0!");
        }
        if(StringUtils.isBlank(bookDTO.getBookNo())){
            return BaseResult.error(-1,"图书编号为空!");
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Constant.HEADER_TOKEN);
        LoginTokenUserDTO loginTokenUser= TokenUtils.getUserBytoken(token);
        Book book=new Book();
        BeanUtils.copyProperties(bookDTO,book);
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Book::getBookNo,book.getBookNo());
        Book bk = bookMapper.selectOne(wrapper);
        if (bk == null){
            return BaseResult.error(-1,"图书不存在!");
        }
        book.setId(bk.getId());
        book.setUpdateBy(loginTokenUser.getUsername());
        book.setUpdateId(loginTokenUser.getId());
        Date now=new Date();
        book.setUpdateTime(now);
        bookMapper.updateById(book);
        return BaseResult.success();
    }

    @Override
    public BaseResult<?> deleteBatch(BookBatchDTO deleteBatchDTO) {
        if(deleteBatchDTO==null || deleteBatchDTO.getIds()==null || deleteBatchDTO.getIds().size()==0){
            log.error("bookServiceImpl.deleteBatch:入参错误");
            throw new BusinessException(-1,"入参错误");
        }
        log.info("bookServiceImpl.deleteBatch.inputparam={}", JSON.toJSONString(deleteBatchDTO));
        for (Long id:deleteBatchDTO.getIds()){
            if(id==null){
                continue;
            }
            LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Book::getId,id);
            Book book = bookMapper.selectOne(wrapper);
            if(book==null){
                continue;
            }
            LambdaQueryWrapper<ReaderBookRef> wr = Wrappers.lambdaQuery();
            wr.eq(ReaderBookRef::getBookId,book.getId());
            ReaderBookRef readerBookRef = readerBookRefMapper.selectOne(wr);
            if (readerBookRef != null){
                return BaseResult.error(-1,"书籍在借阅中,无法下架");
            }
        }
       bookMapper.deleteBatchIds(deleteBatchDTO.getIds());
       return BaseResult.success();
    }

    @Override
    public BaseResult<?> detail(BookBatchDTO bookBatchDTO) {
        if(bookBatchDTO==null || bookBatchDTO.getIds()==null || bookBatchDTO.getIds().size()==0 || bookBatchDTO.getIds().size()>1){
            log.error("bookServiceImpl.detail:入参错误");
            throw new BusinessException(-1,"入参错误");
        }
        log.info("bookServiceImpl.detail.inputparam={}", JSON.toJSONString(bookBatchDTO));
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Book::getId,bookBatchDTO.getIds().get(0));
        Book book = bookMapper.selectOne(wrapper);
        if (book == null){
            return BaseResult.error(-1,"书籍已被下架");
        }
        BookVO bookVO=new BookVO();
        BeanUtils.copyProperties(book,bookVO);
        LambdaQueryWrapper<ReaderBookRef> wr = Wrappers.lambdaQuery();
        wr.eq(ReaderBookRef::getBookId,book.getId());
        ReaderBookRef readerBookRef = readerBookRefMapper.selectOne(wr);
        if (readerBookRef != null){
            bookVO.setStatus("1");
        }else{
            bookVO.setStatus("0");
        }
        return BaseResult.success(bookVO);
    }

    @Override
    public BaseResult<?> findPage(BookFindPageDTO bookFindPageDTO) {
        log.info("bookServiceImpl.findPage.inputparam={}", JSON.toJSONString(bookFindPageDTO));
        if(bookFindPageDTO.getPageNum()==null){
            bookFindPageDTO.setPageNum(1);
        }
        if(bookFindPageDTO.getPageSize()==null){
            bookFindPageDTO.setPageSize(10);
        }
        LambdaQueryWrapper<Book> wrappers = Wrappers.lambdaQuery();
        if(StringUtils.isNotBlank(bookFindPageDTO.getBookNo())){
            wrappers.like(Book::getBookNo,bookFindPageDTO.getBookNo());
        }
        if(StringUtils.isNotBlank(bookFindPageDTO.getName())){
            wrappers.like(Book::getName,bookFindPageDTO.getName());
        }
        if(StringUtils.isNotBlank(bookFindPageDTO.getAuthor())){
            wrappers.like(Book::getAuthor,bookFindPageDTO.getAuthor());
        }
        wrappers.orderByDesc(Book::getPublishTime);
        Page<Book> bookPage =bookMapper.selectPage(new Page<>(bookFindPageDTO.getPageNum(),bookFindPageDTO.getPageSize()), wrappers);
        Page<BookVO> bps=new Page();
        List<BookVO> list=new ArrayList<BookVO>();
        if(bookPage!=null && bookPage.getRecords()!=null && bookPage.getRecords().size()>0){
            bps.setCurrent(bookPage.getCurrent());
            bps.setTotal(bookPage.getPages());
            bps.setSize(bookPage.getSize());
            for(Book bk:bookPage.getRecords()){
                BookVO bv=new BookVO();
                BeanUtils.copyProperties(bk,bv);
                LambdaQueryWrapper<ReaderBookRef> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(ReaderBookRef::getBookId,bk.getId());
                ReaderBookRef readerBookRef = readerBookRefMapper.selectOne(wrapper);
                if (readerBookRef != null){
                    bv.setStatus("1");
                }else{
                    bv.setStatus("0");
                }
                list.add(bv);
            }
        }
        bps.setRecords(list);
        return BaseResult.success(bps);
    }
}
