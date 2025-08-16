package com.application.pennypal;

import com.application.pennypal.infrastructure.config.properties.AuthProperties;
import com.application.pennypal.infrastructure.config.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties({
		JwtProperties.class,
		AuthProperties.class
})
@EnableCaching
public class PennypalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PennypalApplication.class, args);
	}

}
