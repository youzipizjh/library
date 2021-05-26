package com.library.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.library.entity.Reader;
import com.library.library.mapper.ReaderMapper;
import com.library.library.service.ReaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
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
public class ReaderServiceImpl extends ServiceImpl<ReaderMapper, Reader> implements ReaderService {

    @Autowired
    private ReaderMapper readerMapper;
    @Autowired
    private MailSender mailSender;

    public Reader selectReader(String rid){
        Reader reader=new Reader();
        reader=readerMapper.selectById(rid);
        if(reader!=null){
            System.out.println(reader);
        }
        else{
            System.out.println("没这个人");
        }
        return reader;
    }

    @Override
    public Reader insertReader(Reader reader) {
        reader.setId(String.valueOf(Integer.parseInt(readerMapper.getLastReaderID())+1));
        if(selectReader(reader.getId())==null){
            readerMapper.insert(reader);
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("zhoujiaohao2000@163.com");
            message.setTo(reader.getEmail());
            message.setSubject("注册成功");
            message.setText("您已成功在我图书馆注册读者信息\n您的id为："+reader.getId());
            mailSender.send(message);
            System.out.println("插入读者成功");
            return reader;
        }
        else{
            System.out.println("插入读者失败");
            return null;
        }

    }

    @Override
    public boolean updateReader(Reader reader) {
        if(selectReader(reader.getId())==null){
            System.out.println("修改失败");
            return false;
        }else{
            readerMapper.updateById(reader);
            System.out.println("修改成功");
            return true;
        }

    }

    @Override
    public boolean deleteReader(Reader reader) {
        if(selectReader(reader.getId())==null){
            System.out.println("删除失败");
            return false;
        }else{
            readerMapper.deleteById(reader.getId());
            System.out.println("删除成功");
            return true;
        }
    }

    @Override
    public List<Reader> selectAllReader() {
        List<Reader> readers=readerMapper.selectList(new QueryWrapper<Reader>());
        return readers;
    }


}
