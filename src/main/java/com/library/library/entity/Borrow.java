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

    private String borrowdate;

    private String shouldreturn;

    private String returndate;

    private String bid;

    private String rid;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBorrowdate() {
        return borrowdate;
    }

    public void setBorrowdate(String borrowdate) {
        this.borrowdate = borrowdate;
    }

    public String getShouldreturn() {
        return shouldreturn;
    }

    public void setShouldreturn(String shouldreturn) {
        this.shouldreturn = shouldreturn;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
