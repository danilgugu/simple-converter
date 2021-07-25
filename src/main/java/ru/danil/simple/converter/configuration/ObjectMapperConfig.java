package ru.danil.simple.converter.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ObjectMapperConfig implements InitializingBean {

	private final ObjectMapper mapper;

	@Override
	public void afterPropertiesSet() throws Exception {
		mapper.registerModule(new JavaTimeModule());
		SimpleModule module = new SimpleModule();
		mapper.registerModule(module);
	}
}

