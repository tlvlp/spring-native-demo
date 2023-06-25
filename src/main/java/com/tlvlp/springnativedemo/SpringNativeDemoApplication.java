package com.tlvlp.springnativedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tlvlp")
public class SpringNativeDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringNativeDemoApplication.class, args);
	}

}
