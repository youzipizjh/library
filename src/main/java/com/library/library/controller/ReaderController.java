package com.library.library.controller;


import com.library.library.entity.Reader;
import com.library.library.request.reqlogin;
import com.library.library.response.Response;
import com.library.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
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
public class ReaderController {

    @Autowired
    ReaderService readerService=null;


    @RequestMapping("/readerlogin")
    public Response readerlogin(@RequestBody reqlogin req, HttpServletRequest request){
        Response res=new Response();
        System.out.println(req);
        System.out.println(req.getId());
        Reader reader = readerService.selectReader(req.getId());
        if (reader == null) {
            res.setCode(500);
            res.setMessage("该读者不存在！");
        }
        else {
            res.setCode(200);
            res.setObj(reader);
        }
        return res;
    }

    @RequestMapping("/readerinsert")
    public Response readerinsert(@RequestBody Reader reader,HttpServletRequest request){
        Response res=new Response();
        System.out.println(reader);
        reader=readerService.insertReader(reader);
        if(reader!=null){
            res.setCode(200);
            res.setMessage("创建读者成功！");
            res.setObj(reader);
        }
        else {
            res.setCode(500);
            res.setMessage("创建读者失败！");
        }
        return res;
    }

    @RequestMapping("/readerupdate")
    public Response readerupdate(@RequestBody Reader reader,HttpServletRequest request){
        Response res=new Response();
        System.out.println(reader);
        if(readerService.updateReader(reader)){
            res.setCode(200);
            res.setMessage("修改读者信息成功！");
        }
        else {
            res.setCode(500);
            res.setMessage("修改读者信息失败！");
        }
        return res;
    }

    @RequestMapping("/readerdelete")
    public Response readerdelete(@RequestBody Reader reader,HttpServletRequest request){
        Response res=new Response();
        System.out.println(reader);
        if(readerService.deleteReader(reader)){
            res.setCode(200);
            res.setMessage("删除读者成功！");
        }
        else {
            res.setCode(500);
            res.setMessage("删除读者失败！");
        }
        return res;
    }

    @RequestMapping("/readerselect")
    public Response readerselect(HttpServletRequest request){
        Response res=new Response();
        List<Reader> readers=readerService.selectAllReader();
        res.setCode(200);
        res.setObj(readers);
        return res;
    }


}

