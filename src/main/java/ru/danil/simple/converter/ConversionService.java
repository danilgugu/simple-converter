package ru.danil.simple.converter;

public interface ConversionService {

	<I, O> O convert(I input, Class<? extends O> outputClass);
}
