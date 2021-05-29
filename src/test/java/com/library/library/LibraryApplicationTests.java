package com.library.library;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.library.entity.*;
import com.library.library.mapper.*;
import com.library.library.request.reqbook;
import com.library.library.service.BookService;
import com.library.library.service.OrderService;
import com.library.library.service.ReaderService;
import com.library.library.service.ShumuService;
import com.library.library.service.impl.ReaderServiceImpl;
import com.library.library.util.dateformat;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@MapperScan("com.library.library.mapper")
@SpringBootTest
class LibraryApplicationTests {



    @Autowired
    private BookMapper mapper;
    @Autowired
    private AdminMapper adminmapper;
    @Autowired
    private ReaderMapper readerMapper;
    @Autowired
    private ShumuMapper shumuMapper;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private ShumuService shumuService;
    @Autowired
    private BookService bookService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insertadmin(){
        Admin admin=new Admin();
        admin.setAname("周佳昊");
        admin.setApsw("1");
        admin.setEmail("zjh@163.com");
        admin.setTele("13000000000");
        int a=adminmapper.insert(admin);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(a);

    }

    @Test
    public  void testSelect(){
        Book b=new Book();
        //b.setBid("100025");
        b.setIsbn("595-85-252-25");
        b.setLocation("图书阅览室");
        mapper.insert(b);

    }

    @Test
    public void testInsert(){
        Reader reader=new Reader();
        String id=String.valueOf(Integer.parseInt(readerMapper.getLastReaderID())+1);
        reader.setId("10003");
        reader.setName("王天宇");
        int a=readerMapper.insert(reader);
        System.out.println("Aaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(a);
    }

    @Autowired
    private ReaderServiceImpl readerService;


    @Test
    public void test1(){
        Shumu shumu=new Shumu();
        shumu=shumuMapper.selectById("978-7-302-55901-6");
        if(shumu!=null){
            System.out.println(shumu);
        }
        else{
            System.out.println("没这个书目");
        }
    }

    @Test
    void test2(){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("zhoujiaohao2000@163.com");
        message.setTo("yangyuchen816@gmail.com");
        message.setSubject("给爷还书!");
        message.setText("一天不还罚你一百块，\n罚的你倾家荡产！");
        mailSender.send(message);
    }

    @Test
    void test3(){
        List<Shumu> shumus=new ArrayList<>();
        String word="郑 杨";
        String []arr=word.split("\\s+");
        if(arr.length==1){
            String bookname=arr[0];
            QueryWrapper<Shumu> wrapper=new QueryWrapper<>();
            wrapper.like("bname",bookname);
            shumus=shumuMapper.selectList(wrapper);
            wrapper.clear();
            wrapper.like("author",bookname);
            shumus.addAll(shumuMapper.selectList(wrapper));
            for(Shumu s : shumus){
                System.out.println(s);
            }
        }else if(arr.length==2){
            String bookname=arr[0];
            String authorname=arr[1];
            QueryWrapper<Shumu> wrapper=new QueryWrapper<>();
            wrapper.like("bname",bookname);
            wrapper.like("author",authorname);
            shumus=shumuMapper.selectList(wrapper);
            System.out.println(shumus);
        }
    }

    @Test
    void test4(){
        String a="";
        if(a.equals("")){
            List<Shumu> shumus=new ArrayList<>();
            shumus=shumuMapper.selectList(new QueryWrapper<Shumu>());
            for(Shumu s : shumus){
                System.out.println(s);
            }
        }
    }

    @Test
    void test5(){
        reqbook req=new reqbook();
        req.setIsbn("26-251-21-69");
        req.setBname("三国演义");
        req.setAuthor("罗贯中");
        Shumu shumu;
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
        }
        else{
            shumu =new Shumu();
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
        }
    }

    @Test
    void tset6(){
        Order order=new Order();
        dateformat df=new dateformat();
        order.setIsbn("978-7-302-55901-6");
        order.setRid("10001");
        LocalDateTime now=LocalDateTime.now();
        String s1=df.localdatetimetostring(now);
        String s2=df.localdatetimetostring(now.plusDays(14L));
        if(order!=null&&order.getIsbn()!=null&&order.getRid()!=null) {
            order.setOrderdate(s1);
            order.setDeadline(s2);
            orderMapper.insert(order);
            System.out.println("预约成功");
        }
        else {
            System.out.println("预约失败");
        }
    }

    @Test
    void test7(){
        Order order=new Order();
        order.setRid("10001");
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("rid",order.getRid());
        List <Order> orderList=orderMapper.selectList(wrapper);
        System.out.println(orderList);
    }







}
