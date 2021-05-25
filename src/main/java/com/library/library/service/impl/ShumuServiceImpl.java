package com.library.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.library.entity.Shumu;
import com.library.library.mapper.ShumuMapper;
import com.library.library.service.ShumuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ShumuServiceImpl extends ServiceImpl<ShumuMapper, Shumu> implements ShumuService {

    @Autowired
    private ShumuMapper shumuMapper;

    @Override
    public Shumu selectShumubyISBN(String isbn) {
        Shumu shumu=new Shumu();
        shumu=shumuMapper.selectById(isbn);
        if(shumu!=null){
            System.out.println(shumu);
        }
        else{
            System.out.println("没这个书目");
        }
        return shumu;
    }

    @Override
    public List<Shumu> selectShumubyKeyWord(String word) {
        List<Shumu> shumus=new ArrayList<>();
        if(word==null||word.equals("")){
            shumus=shumuMapper.selectList(new QueryWrapper<Shumu>());
            return shumus;
        }
        String []arr=word.split("\\s+");
        if(arr.length==1){
            String bookname=arr[0];
            QueryWrapper<Shumu> wrapper=new QueryWrapper<>();
            wrapper.like("bname",bookname);
            shumus=shumuMapper.selectList(wrapper);
            wrapper.clear();
            wrapper.like("author",bookname);
            shumus.addAll(shumuMapper.selectList(wrapper));
        }else if(arr.length==2){
            String bookname=arr[0];
            String authorname=arr[1];
            QueryWrapper<Shumu> wrapper=new QueryWrapper<>();
            wrapper.like("bname",bookname);
            wrapper.like("author",authorname);
            shumus=shumuMapper.selectList(wrapper);
        }
        return shumus;
    }

    @Override
    public boolean updateShumu(Shumu shumu) {
        if(selectShumubyISBN(shumu.getIsbn())==null){
            System.out.println("修改失败");
            return false;
        }else{
            shumuMapper.updateById(shumu);
            System.out.println("修改成功");
            return true;
        }
    }

    @Override
    public boolean insertShumu(Shumu shumu) {
        if(selectShumubyISBN(shumu.getIsbn())==null){
            shumuMapper.insert(shumu);
            System.out.println("插入书目成功");
            return true;
        }
        else{
            System.out.println("插入书目失败");
            return false;
        }
    }

}
