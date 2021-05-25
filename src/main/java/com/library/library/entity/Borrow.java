package com.library.library.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨宇辰
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Borrow implements Serializable {

    private static final long serialVersionUID=1L;

    private LocalDateTime borrowdate;

    private LocalDateTime shouldreturn;

    private LocalDateTime returndate;

    private String bid;

    private String rid;


}
