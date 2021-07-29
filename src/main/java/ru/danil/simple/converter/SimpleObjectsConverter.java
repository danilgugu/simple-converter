package ru.danil.simple.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.danil.simple.converter.custom.Converter;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.repository.ClassRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SimpleObjectsConverter {

	private final ObjectMapper objectMapper;
	private final List<Converter<?, ?>> customConverters;

	public <I, O> O convert(I input, Class<? extends O> outputClass) {
		try {
			Method getGenericInfo = Class.class.getDeclaredMethod("getGenericInfo");
			getGenericInfo.setAccessible(true);
			Optional<Converter<?, ?>> customConverter = customConverters.stream()
					.filter(converter -> {
						Class<? extends Converter> converterClass = converter.getClass();
						try {
							ClassRepository genericInfo = (ClassRepository) getGenericInfo.invoke(converterClass);
							ParameterizedTypeImpl superInterface = (ParameterizedTypeImpl) genericInfo.getSuperInterfaces()[0];
							Type converterInputType = superInterface.getActualTypeArguments()[0];
							Type converterOutputType = superInterface.getActualTypeArguments()[1];
							return converterInputType.getTypeName().equals(input.getClass().getName()) && converterOutputType.getTypeName().equals(outputClass.getName());
						} catch (IllegalAccessException | InvocationTargetException e) {
							e.printStackTrace();
							return false;
						}
					})
					.findFirst();
			if (customConverter.isPresent()) {
				return ((Converter<I, O>) customConverter.get()).convert(input);
			}
			String valueAsString = objectMapper.writeValueAsString(input);
			return objectMapper.readValue(valueAsString, outputClass);
		} catch (JsonProcessingException | NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
