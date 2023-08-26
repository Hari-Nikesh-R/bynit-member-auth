package com.dosmartie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class BynitMemberAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BynitMemberAuthApplication.class, args);
	}

}
