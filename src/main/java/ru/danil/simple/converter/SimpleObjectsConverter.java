package ru.danil.simple.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.jodah.typetools.TypeResolver;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.danil.simple.converter.custom.Converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SimpleObjectsConverter implements InitializingBean {

	private final ObjectMapper objectMapper;
	private final List<Converter<?, ?>> customConverters;
	private Map<Pair<Class<?>, Class<?>>, Converter<?, ?>> converterMap;

	public <I, O> O convert(I input, Class<? extends O> outputClass) {
		Converter<?, ?> customConverter = converterMap.get(Pair.of(input.getClass(), outputClass));
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

	@Override
	public void afterPropertiesSet() throws Exception {
		converterMap = customConverters.stream()
				.collect(Collectors.toMap(
						converter -> {
							Class<?>[] rawArguments = TypeResolver.resolveRawArguments(Converter.class, converter.getClass());
							Class<?> converterInputClass = rawArguments[0];
							Class<?> converterOutputClass = rawArguments[1];
							return Pair.of(converterInputClass, converterOutputClass);
						},
						converter -> converter
				));
	}
}
