package com.example.Sale_Information_System;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.Sale_Information_System.Mapper")
public class SaleInformationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaleInformationSystemApplication.class, args);
	}

}

