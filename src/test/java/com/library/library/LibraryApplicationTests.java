package com.library.library;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.library.entity.Admin;
import com.library.library.entity.Book;
import com.library.library.entity.Reader;
import com.library.library.entity.Shumu;
import com.library.library.mapper.AdminMapper;
import com.library.library.mapper.BookMapper;
import com.library.library.mapper.ReaderMapper;
import com.library.library.mapper.ShumuMapper;
import com.library.library.service.ReaderService;
import com.library.library.service.impl.ReaderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

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







}
