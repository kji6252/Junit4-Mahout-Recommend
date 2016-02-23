package com.mahouttest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
public class JpaConfig {
	
	@Bean
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

}
