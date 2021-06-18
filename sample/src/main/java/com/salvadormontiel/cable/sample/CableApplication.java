package com.salvadormontiel.cable.sample;

import com.salvadormontiel.cable.Cable;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@SpringBootApplication
public class CableApplication {

	public static void main(String[] args) {
		SpringApplication.run(CableApplication.class, args);
		Cable.init();
		//Cable.registerComponent("create-report", CreateUser.class);
	}

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect(){
		return new SpringSecurityDialect();
	}
}
