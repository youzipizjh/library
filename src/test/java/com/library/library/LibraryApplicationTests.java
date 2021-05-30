package com.library.library;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.library.entity.*;
import com.library.library.mapper.*;
import com.library.library.request.reqbook;
import com.library.library.request.reqborrow;
import com.library.library.response.resorder;
import com.library.library.service.*;
import com.library.library.service.impl.ReaderServiceImpl;
import com.library.library.util.dateformat;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.time.Duration;
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
    @Autowired
    private BorrowService borrowService;

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
        order.setIsbn("123-456-789");
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
        /*QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("rid",order.getRid());
        List <Order> orderList=orderMapper.selectList(wrapper);
        System.out.println(orderList);*/
        List<Order> orderList=orderService.selectOrderList(order);
        System.out.println(orderList);
        if(orderList==null){
        }
        else {
            List<resorder> resorders = new ArrayList<>();
            Shumu shumu = new Shumu();
            for (Order o : orderList) {
                resorder ro = new resorder();
                shumu = shumuService.selectShumubyISBN(o.getIsbn());
                ro.setBid(o.getBid());
                ro.setBname(shumu.getBname());
                ro.setAuthor(shumu.getAuthor());
                ro.setIsbn(o.getIsbn());
                ro.setOrderdate(o.getOrderdate());
                ro.setDeadline(o.getDeadline());
                resorders.add(ro);
            }
            System.out.println(resorders);
        }
    }
    @Test
    void test8(){
        List<Borrow> borrowList=borrowService.list();
        System.out.println(borrowList);
    }

    @Test
    void test9(){
        dateformat df=new dateformat();
        reqborrow req=new reqborrow();
        req.setRid("10001");
        req.setBid("26-516-1-121.1");
        req.setDate("2021-05-30 11:11:11");
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
            System.out.println("您的书逾期"+days+"天未还，请缴纳"+money+"元罚金");
        }else{
            System.out.println("还书成功!");
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
    }







}
