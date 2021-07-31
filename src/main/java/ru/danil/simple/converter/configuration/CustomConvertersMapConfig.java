package ru.danil.simple.converter.configuration;

import net.jodah.typetools.TypeResolver;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.danil.simple.converter.custom.Converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class CustomConvertersMapConfig {

	@Bean
	public Map<Pair<Class<?>, Class<?>>, Converter<?, ?>> convertersMap(List<Converter<?, ?>> customConverters) {
		return customConverters.stream()
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
