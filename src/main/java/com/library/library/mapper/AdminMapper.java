package com.library.library.mapper;

import com.library.library.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨宇辰
 * @since 2021-05-18
 */
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("select aid from admin order by aid desc limit 1")
    public String getLastAdminID();

}
