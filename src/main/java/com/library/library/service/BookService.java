package com.library.library.service;

import com.library.library.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨宇辰
 * @since 2021-05-18
 */
public interface BookService extends IService<Book> {

    List<Book> selectBook(Book book);
    Book insertBook(Book book);
    boolean updateBook(Book book);
    boolean deleteBook(Book book);
}
