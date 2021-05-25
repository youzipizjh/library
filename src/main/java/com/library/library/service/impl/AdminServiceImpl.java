package com.library.library.service.impl;

import com.library.library.entity.Admin;
import com.library.library.mapper.AdminMapper;
import com.library.library.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨宇辰
 * @since 2021-05-18
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public boolean insertadmin(){
        Admin admin=new Admin();
        admin.setAname("周佳昊");
        admin.setApsw("1");
        admin.setEmail("zjh@163.com");
        admin.setTele("13000000000");
        int a=adminMapper.insert(admin);
        System.out.println(a);
        return true;

    }
}
