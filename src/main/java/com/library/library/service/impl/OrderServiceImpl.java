package com.library.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.library.entity.Order;
import com.library.library.mapper.OrderMapper;
import com.library.library.service.OrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> selectOrderList(Order order) {
       QueryWrapper<Order> wrapper=new QueryWrapper<>();
       wrapper.eq("rid",order.getRid());
       List <Order> orderList=orderMapper.selectList(wrapper);
        return orderList;
    }

    @Override
    public Order insertOrder(Order order) {
        dateformat df=new dateformat();
        if(order!=null&&order.getIsbn()!=null&&order.getRid()!=null) {
            LocalDateTime now=LocalDateTime.now();
            order.setOrderdate(df.localdatetimetostring(now));
            order.setDeadline(df.localdatetimetostring(now.plusDays(14L)));
            orderMapper.insert(order);
            System.out.println("预约成功");
        }
        else {
            System.out.println("预约失败");
        }
        return order;
    }

    @Override
    public boolean deleteOrder(Order order) {

        if(order!=null&&order.getIsbn()!=null&&order.getRid()!=null){
            QueryWrapper<Order> wrapper=new QueryWrapper<>();
            wrapper.eq("rid",order.getRid());
            wrapper.eq("isbn",order.getIsbn());
            wrapper.eq("orderdate",order.getOrderdate());
            orderMapper.delete(wrapper);
            System.out.println("取消成功");
            return true;
        }else{
            System.out.println("取消失败");
            return false;
        }
    }





}
