package com.library.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.library.entity.*;
import com.library.library.mapper.BookMapper;
import com.library.library.request.reqborrow;
import com.library.library.response.Response;
import com.library.library.response.resborrow;
import com.library.library.response.resorder;
import com.library.library.service.*;
import com.library.library.util.dateformat;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@EnableScheduling
public class BorrowController {

    @Autowired
    OrderService orderService=null;
    @Autowired
    BorrowService borrowService=null;
    @Autowired
    BookService bookService=null;
    @Autowired
    ShumuService shumuService=null;
    @Autowired
    ReaderService readerService=null;
    @Autowired
    private MailSender mailSender;
    @Autowired
    BookMapper bookMapper;

    //通过bid，rid，isbn，date借阅已预约的书
    @RequestMapping("/borrowwithorder")
    public Response borrowwithorder(@RequestBody reqborrow req, HttpServletRequest httpServletRequest){
        Response res=new Response();
        if(req.getBid()==null){
            res.setCode(500);
            res.setMessage("借阅失败！");
        }else{
            Book book=new Book();
            book=bookService.getById(req.getBid());
            book.setStatus("已借出");
            bookService.updateBook(book);
            Order order=new Order();
            order.setRid(req.getRid());
            order.setIsbn(req.getIsbn());
            order.setOrderdate(req.getDate());
            orderService.deleteOrder(order);
            Borrow borrow=new Borrow();
            borrow.setBid(req.getBid());
            borrow.setRid(req.getRid());
            borrowService.insertBorrow(borrow);
            res.setCode(200);
            res.setMessage("借阅成功！");
            res.setObj(borrow);
        }

        return res;
    }

    //通过rid，isbn借阅未预约的书
    @RequestMapping("/borrowwithoutorder")
    public Response borrowwithoutorder(@RequestBody reqborrow req, HttpServletRequest httpServletRequest){
        Response res=new Response();
            Book book=new Book();
            book=bookService.getById(bookMapper.getabook(req.getIsbn()));
            book.setStatus("已借出");
            bookService.updateBook(book);
            Borrow borrow=new Borrow();
            borrow.setBid(book.getBid());
            borrow.setRid(req.getRid());
            borrowService.insertBorrow(borrow);
            res.setCode(200);
            res.setMessage("借阅成功！");
            res.setObj(borrow);
        return res;
    }

    //通过rid查询借阅记录
    @RequestMapping("/borrowselect")
    public Response borrowselect(@RequestBody reqborrow req,HttpServletRequest httpServletRequest){
        Response res=new Response();
        Borrow borrow=new Borrow();
        borrow.setRid(req.getRid());
        List<Borrow> borrowList=borrowService.selectBorrowList(borrow);
        if(borrowList==null){
            res.setCode(500);
            res.setMessage("没有查到预约记录");
        }
        else{
            List<resborrow> resborrows=new ArrayList<>();
            Shumu shumu=new Shumu();
            for (Borrow b:borrowList){
                resborrow rb=new resborrow();
                QueryWrapper<Book> wrapper=new QueryWrapper<>();
                wrapper.eq("bid",b.getBid());
                Book book=bookMapper.selectOne(wrapper);
                shumu=shumuService.selectShumubyISBN(book.getIsbn());
                rb.setBid(b.getBid());
                rb.setBname(shumu.getBname());
                rb.setAuthor(shumu.getAuthor());
                rb.setIsbn(shumu.getIsbn());
                rb.setBorrowdate(b.getBorrowdate());
                rb.setShouldreturn(b.getShouldreturn());
                rb.setReturndate(b.getReturndate());
                resborrows.add(rb);
            }
            res.setCode(200);
            res.setObj(resborrows);
        }
        return res;
    }
    //通过rid,bid,date(borrowdate)还书
    @RequestMapping("/bookreturn")
    public Response bookreturn(@RequestBody reqborrow req,HttpServletRequest httpServletRequest){
        Response res=new Response();
        dateformat df=new dateformat();
        QueryWrapper<Borrow> wrapper1=new QueryWrapper<>();
        wrapper1.eq("rid",req.getRid());
        wrapper1.eq("bid",req.getBid());
        wrapper1.eq("borrowdate",req.getDate());
        Borrow borrow=borrowService.getOne(wrapper1);
        borrow.setReturndate(df.localdatetimetostring(LocalDateTime.now()));
        borrowService.update(borrow,wrapper1);
        LocalDateTime shouldreturn=df.stringtolocaldatetime(borrow.getShouldreturn());
        LocalDateTime returndate=df.stringtolocaldatetime(borrow.getReturndate());
        if(returndate.isAfter(shouldreturn)){
            Duration duration=Duration.between(shouldreturn,returndate);
            long days=duration.toDays();
            double money=0.1*(int)days;
            res.setMessage("您的书逾期"+days+"天未还，请缴纳"+money+"元罚金");
        }else{
            res.setMessage("还书成功!");
        }
        QueryWrapper<Book> wrapper2=new QueryWrapper<>();
        wrapper2.eq("bid",req.getBid());
        Book book=bookService.getOne(wrapper2);
        QueryWrapper<Order> wrapper3=new QueryWrapper<>();
        wrapper3.eq("isbn",book.getIsbn());
        wrapper3.isNull("bid");
        Order order=orderService.getOne(wrapper3);
        if(order!=null){
            order.setBid(req.getBid());
            wrapper3.eq("orderdate",order.getOrderdate());
            orderService.update(order,wrapper3);
            book.setStatus("已预约");
            bookService.update(book,wrapper2);
            Reader reader=readerService.selectReader(order.getRid());
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("zhoujiaohao2000@163.com");
            message.setTo(reader.getEmail());
            message.setSubject("图书预约提醒");
            message.setText("尊敬的读者\n您在我图书馆预约的一本图书现已可借，请及时前来借阅\n  xxx图书馆");
            mailSender.send(message);
        }else{
            book.setStatus("未借出");
            bookService.update(book,wrapper2);
        }
        res.setCode(200);
        return res;
    }

    @Scheduled(cron = "* * 0/1 * * ?")
    public void autorun(){
        System.out.println("定时任务执行！");
        List<Borrow> borrowList=borrowService.list();
        LocalDateTime now=LocalDateTime.now();
        dateformat df=new dateformat();
        for(Borrow b:borrowList){
            LocalDateTime sr=df.stringtolocaldatetime(b.getShouldreturn());
            if(now.isAfter(sr)&&b.getReturndate()==null){
                Reader reader=readerService.selectReader(b.getRid());
                SimpleMailMessage message=new SimpleMailMessage();
                message.setFrom("zhoujiaohao2000@163.com");
                message.setTo(reader.getEmail());
                message.setSubject("图书归还提醒");
                message.setText("尊敬的读者\n您在我图书馆借阅的一本图书逾期未还，书号为"+b.getBid()+",请及时前来归还\n  xxx图书馆");
                mailSender.send(message);
            }
        }
        List<Order> orderList=orderService.list();
        for(Order o:orderList){
            LocalDateTime ddl=df.stringtolocaldatetime(o.getDeadline());
            if(now.isAfter(ddl)){
                if(o.getBid()==null){
                    orderService.deleteOrder(o);
                }
                else{
                    QueryWrapper<Book> wrapper2=new QueryWrapper<>();
                    wrapper2.eq("bid",o.getBid());
                    Book book=bookService.getOne(wrapper2);
                    book.setStatus("未借出");
                    bookService.update(book,wrapper2);
                    orderService.deleteOrder(o);
                }
            }
        }
    }



}

