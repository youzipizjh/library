package com.library.library.service;

import com.library.library.entity.Shumu;
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
public interface ShumuService extends IService<Shumu> {

    Shumu selectShumubyISBN(String isbn);
    List<Shumu> selectShumubyKeyWord(String word);
    boolean updateShumu(Shumu shumu);
    boolean insertShumu(Shumu shumu);
    int getKejie(String isbn);

}
