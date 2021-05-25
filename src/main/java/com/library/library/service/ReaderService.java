package com.library.library.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.library.library.entity.Reader;
import com.baomidou.mybatisplus.extension.service.IService;
import com.library.library.service.impl.ReaderServiceImpl;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨宇辰
 * @since 2021-05-18
 */
public interface ReaderService extends IService<Reader> {

    Reader selectReader(String rid);
    boolean insertReader(Reader reader);
    boolean updateReader(Reader reader);
    boolean deleteReader(Reader reader);

}
