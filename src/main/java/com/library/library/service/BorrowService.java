package com.library.library.service;

import com.library.library.entity.Borrow;
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
public interface BorrowService extends IService<Borrow> {

    Borrow insertBorrow(Borrow borrow);
    List<Borrow> selectBorrowList(Borrow borrow);

}
