package com.cloudalibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.cloudalibaba.Dao"})
public class MyBatisConfig {


}


