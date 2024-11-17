package com.multibite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EntityScan(basePackages = "com.multibite.model")
@EnableSwagger2
public class multibiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(multibiteApplication.class, args);
	}

}
