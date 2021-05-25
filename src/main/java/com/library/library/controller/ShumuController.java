package com.library.library.controller;


import com.library.library.entity.Book;
import com.library.library.entity.Shumu;
import com.library.library.request.reqlogin;
import com.library.library.response.Response;
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
public class ShumuController {

    @Autowired
    ShumuService shumuService=null;

    @RequestMapping("/booksearch")
    public Response booksearch(@RequestBody reqlogin req, HttpServletRequest request){
        Response res=new Response();
        List<Shumu> shumus=new ArrayList<>();
        shumus=shumuService.selectShumubyKeyWord(req.getId());
        if (shumus == null) {
            res.setCode(500);
            res.setMessage("未查询到相关图书！");
        }
        else {
            res.setCode(200);
            res.setObj(shumus);
        }
        return res;
    }





}

