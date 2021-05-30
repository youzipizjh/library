package com.library.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.library.entity.Book;
import com.library.library.entity.Borrow;
import com.library.library.mapper.BookMapper;
import com.library.library.mapper.BorrowMapper;
import com.library.library.service.BorrowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.library.util.dateformat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements BorrowService {

    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Borrow insertBorrow(Borrow borrow) {
        dateformat df=new dateformat();
        LocalDateTime now=LocalDateTime.now();
        borrow.setBorrowdate(df.localdatetimetostring(now));
        borrow.setShouldreturn(df.localdatetimetostring(now.plusMonths(1)));
        borrowMapper.insert(borrow);
        System.out.println("借阅成功！");
        return borrow;
    }

    @Override
    public List<Borrow> selectBorrowList(Borrow borrow) {
        QueryWrapper<Borrow> wrapper=new QueryWrapper<>();
        wrapper.eq("rid",borrow.getRid());
        List <Borrow> borrowList=borrowMapper.selectList(wrapper);
        return borrowList;
    }
}
