package ru.danil.simple.converter.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarDTO {

	long carId;

	String make;

	String modelName;
}
