package ru.danil.simple.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.jodah.typetools.TypeResolver;
import org.springframework.stereotype.Component;
import ru.danil.simple.converter.custom.Converter;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SimpleObjectsConverter {

	private final ObjectMapper objectMapper;
	private final List<Converter<?, ?>> customConverters;

	public <I, O> O convert(I input, Class<? extends O> outputClass) {
		Optional<Converter<?, ?>> customConverter = getCustomConverter(input.getClass(), outputClass);
		if (customConverter.isPresent()) {
			return ((Converter<I, O>) customConverter.get()).convert(input);
		}
		return convertUsingObjectMapper(input, outputClass);
	}

	private <I, O> Optional<Converter<?, ?>> getCustomConverter(Class<? extends I> requestedInputClass, Class<? extends O> requestedOutputClass) {
		return customConverters.stream()
				.filter(converter -> {
					Class<?>[] rawArguments = TypeResolver.resolveRawArguments(Converter.class, converter.getClass());
					Class<?> converterInputClass = rawArguments[0];
					Class<?> converterOutputClass = rawArguments[1];
					return converterInputClass.equals(requestedInputClass) && converterOutputClass.equals(requestedOutputClass);
				})
				.findFirst();
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
