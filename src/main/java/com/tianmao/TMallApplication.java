package com.tianmao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 胡建德
 */
@SpringBootApplication
@MapperScan("com.tianmao.mapper")
public class TMallApplication {

    public static void main(String[] args){
        SpringApplication.run(TMallApplication.class,args);
    }
}
