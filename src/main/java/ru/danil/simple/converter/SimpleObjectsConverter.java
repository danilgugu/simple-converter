package ru.danil.simple.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import ru.danil.simple.converter.custom.Converter;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SimpleObjectsConverter {

	private final ObjectMapper objectMapper;
	private final Map<Pair<Class<?>, Class<?>>, Converter<?, ?>> convertersMap;

	public <I, O> O convert(I input, Class<? extends O> outputClass) {
		Converter<?, ?> customConverter = convertersMap.get(Pair.of(input.getClass(), outputClass));
		if (customConverter != null) {
			return ((Converter<I, O>) customConverter).convert(input);
		}
		return convertUsingObjectMapper(input, outputClass);
	}

	private <I, O> O convertUsingObjectMapper(I input, Class<? extends O> outputClass) {
		try {
			String valueAsString = objectMapper.writeValueAsString(input);
			return objectMapper.readValue(valueAsString, outputClass);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
