package com.library.library.mapper;

import com.library.library.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨宇辰
 * @since 2021-05-18
 */
public interface BookMapper extends BaseMapper<Book> {

    @Select("select count(*) from book where isbn=#{isbn}")
    public int getbooknum(@Param("isbn") String isbn);
}
