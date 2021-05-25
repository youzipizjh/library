package com.library.library.controller;


import com.library.library.entity.Book;
import com.library.library.entity.Shumu;
import com.library.library.request.reqbook;
import com.library.library.response.Response;
import com.library.library.service.BookService;
import com.library.library.service.ShumuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨宇辰
 * @since 2021-05-18
 */
@RestController
@CrossOrigin
public class BookController {

    @Autowired
    ShumuService shumuService=null;
    @Autowired
    BookService bookService=null;

    @RequestMapping("/bookinsert")
    public Response bookinsert(@RequestBody reqbook req, HttpServletRequest request){
        Response res=new Response();
        Shumu shumu =new Shumu();
        shumu=shumuService.selectShumubyISBN(req.getIsbn());
        if(shumu!=null){
            Book book=new Book();
            book.setBid(req.getBid());
            book.setIsbn(req.getIsbn());
            book.setLocation(req.getLocation());
            book.setStatus(req.getStatus());
            book.setAdmin(req.getAdmin());
            bookService.insertBook(book);
            shumu.setNum(String.valueOf(Integer.parseInt(shumu.getNum())+1));
            shumuService.updateShumu(shumu);
            res.setCode(200);
            res.setMessage("图书插入成功！");
        }
        else{
            shumu.setIsbn(req.getIsbn());
            shumu.setBname(req.getBname());
            shumu.setAuthor(req.getAuthor());
            shumu.setPublisher(req.getPublisher());
            shumu.setPublishdate(req.getPublishdate());
            shumu.setNum("0");
            shumu.setAdmin(req.getAdmin());
            shumuService.insertShumu(shumu);//新增书目
            Book book=new Book();
            book.setBid(req.getBid());
            book.setIsbn(req.getIsbn());
            book.setLocation(req.getLocation());
            book.setStatus(req.getStatus());
            book.setAdmin(req.getAdmin());
            bookService.insertBook(book);
            shumu.setNum(String.valueOf(Integer.parseInt(shumu.getNum())+1));
            shumuService.updateShumu(shumu);
            res.setCode(200);
            res.setMessage("书目和图书插入成功！");
        }
        return res;
    }

    //拿ISBN来查
    @RequestMapping("/bookselect")
    public Response bookselect(@RequestBody Shumu shumu,HttpServletRequest httpServletRequest){
        Response response=new Response();
        Book book=new Book();
        book.setIsbn(shumu.getIsbn());
        List<Book> books=bookService.selectBook(book);

        return response;
    }


}

