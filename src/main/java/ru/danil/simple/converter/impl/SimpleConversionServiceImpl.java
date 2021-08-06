package ru.danil.simple.converter.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.danil.simple.converter.ConversionService;

@Component
@RequiredArgsConstructor
public class SimpleConversionServiceImpl implements ConversionService {

	private final ObjectMapper objectMapper;

	public <I, O> O convert(I input, Class<? extends O> outputClass) {
		try {
			String valueAsString = objectMapper.writeValueAsString(input);
			return objectMapper.readValue(valueAsString, outputClass);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
