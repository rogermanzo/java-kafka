package com.api.SaludApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaServer
@EnableJpaRepositories(basePackages = "com.api.SaludApp.user.repository")
public class SaludApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaludApplication.class, args);
	}

}
