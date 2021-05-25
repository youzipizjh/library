package com.library.library.request;


import lombok.Data;
import java.io.Serializable;

@Data
public class reqmail implements Serializable {

    /**
     * 接受邮箱账户
     */

    private String mail;

    /**
     * 邮箱标题
     */

    private String title;

    /**
     * 要发送的内容
     */

    private String content;
}
