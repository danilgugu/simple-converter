package ru.danil.simple.converter.custom;

public interface Converter<I, O> {

	O convert(I input);
}
