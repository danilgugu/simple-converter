package ru.danil.simple.converter.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Car {

	long id;

	String make;

	String model;
}
