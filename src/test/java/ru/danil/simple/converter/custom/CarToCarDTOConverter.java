package ru.danil.simple.converter.custom;

import org.springframework.stereotype.Component;
import ru.danil.simple.converter.model.Car;
import ru.danil.simple.converter.model.CarDTO;

@Component
public class CarToCarDTOConverter implements Converter<Car, CarDTO> {

	@Override
	public CarDTO convert(Car input) {
		return CarDTO.builder()
				.carId(input.getId())
				.make(input.getMake())
				.modelName(input.getModel())
				.build();
	}
}
