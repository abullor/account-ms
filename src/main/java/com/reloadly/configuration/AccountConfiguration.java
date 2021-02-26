package com.reloadly.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	Queue queue() {
		return new Queue("NotificationsQ", false);
	}
}