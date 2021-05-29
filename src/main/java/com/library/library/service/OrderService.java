package com.library.library.service;

import com.library.library.entity.Order;
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
public interface OrderService extends IService<Order> {

    List<Order> selectOrderList(Order order);
    Order insertOrder(Order order);
    boolean deleteOrder(Order order);

}
