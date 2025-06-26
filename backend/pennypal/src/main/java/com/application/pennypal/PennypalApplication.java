package com.application.pennypal;

import com.application.pennypal.infrastructure.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		JwtProperties.class
})
public class PennypalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PennypalApplication.class, args);
	}

}
