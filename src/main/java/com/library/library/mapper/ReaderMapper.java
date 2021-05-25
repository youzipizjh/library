package com.library.library.mapper;

import com.library.library.entity.Reader;
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
public interface ReaderMapper extends BaseMapper<Reader> {

    @Select("select rid from reader order by rid desc limit 1")
    public String getLastReaderID();

}
