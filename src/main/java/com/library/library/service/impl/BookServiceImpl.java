package com.library.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.library.entity.Book;
import com.library.library.mapper.BookMapper;
import com.library.library.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨宇辰
 * @since 2021-05-18
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    public boolean isEmpty(String s){
        if(s!=null&&!s.trim().equals("")){
            return false;
        }
        else {
            return true;
        }
    }


    @Override
    public List<Book> selectBook(Book book) {

        QueryWrapper<Book> wrapper=new QueryWrapper<>();
        if(!isEmpty(book.getBid())){
            wrapper.eq("bid",book.getBid());
        }
        if(!isEmpty(book.getIsbn())){
            wrapper.eq("isbn",book.getIsbn());
        }
        if(!isEmpty(book.getStatus())){
            wrapper.eq("status",book.getStatus());
        }
        if(!isEmpty(book.getLocation())){
            wrapper.eq("location",book.getLocation());
        }
        List<Book> books;
        books=bookMapper.selectList(wrapper);
        return books;
    }

    @Override
    public boolean insertBook(Book book) {
        if(book.getBid()!=null&&!book.getBid().trim().equals("")&&bookMapper.selectById(book.getBid())==null){
            bookMapper.insert(book);
            return true;
        }
        else{

            return false;
        }
    }

    @Override
    public boolean updateBook(Book book) {
        if(selectBook(book)==null){
            System.out.println("修改书本失败");
            return false;
        }else{
            bookMapper.updateById(book);
            System.out.println("修改书本成功");
            return true;
        }
    }

    @Override
    public boolean deleteBook(Book book) {
        if(selectBook(book)==null){
            System.out.println("删除书本失败");
            return false;
        }else{
            bookMapper.deleteById(book.getBid());
            System.out.println("删除书本成功");
            return true;
        }
    }
}
