package com.library.library.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.library.library.entity.Order;
import com.library.library.entity.Shumu;
import com.library.library.request.reqborrow;
import com.library.library.response.Response;
import com.library.library.response.resorder;
import com.library.library.service.OrderService;
import com.library.library.service.ShumuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
public class OrderController {

    @Autowired
    OrderService orderService=null;
    @Autowired
    ShumuService shumuService=null;

    //通过rid查预约记录
    @RequestMapping("/orderselect")
    public Response orderselect(@RequestBody reqborrow req, HttpServletRequest httpServletRequest){
        Response res=new Response();
        Order order=new Order();
        order.setRid(req.getRid());
        List<Order> orderList=orderService.selectOrderList(order);
        if(orderList==null){
            res.setCode(500);
            res.setMessage("没有查到预约记录");
        }
        else{
            List<resorder> resorders=new ArrayList<>();
            Shumu shumu=new Shumu();
            for (Order o:orderList){
                resorder ro=new resorder();
                shumu=shumuService.selectShumubyISBN(o.getIsbn());
                ro.setBid(o.getBid());
                ro.setBname(shumu.getBname());
                ro.setAuthor(shumu.getAuthor());
                ro.setIsbn(o.getIsbn());
                ro.setOrderdate(o.getOrderdate());
                ro.setDeadline(o.getDeadline());
                resorders.add(ro);
            }
            res.setCode(200);
            res.setObj(resorders);
        }
        return res;

    }

    //通过rid、isbn预约一本书
    @RequestMapping("/orderinsert")
    public Response orderinsert(@RequestBody reqborrow req,HttpServletRequest httpServletRequest){
        Response res=new Response();
        Order order=new Order();
        order.setRid(req.getRid());
        order.setIsbn(req.getIsbn());
        order=orderService.insertOrder(order);
        if(order!=null){
            res.setCode(200);
            res.setMessage("预约成功");
        }
        else{
            res.setCode(500);
            res.setMessage("预约失败");
        }
        return res;
    }



}

