package com.library.library;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.library.library.mapper")
@SpringBootApplication
public class LibraryApplication {


    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

}
