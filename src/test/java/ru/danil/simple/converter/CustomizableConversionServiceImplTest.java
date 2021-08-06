package ru.danil.simple.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.danil.simple.converter.custom.Converter;
import ru.danil.simple.converter.impl.CustomizableConversionServiceImpl;
import ru.danil.simple.converter.model.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomizableConversionServiceImplTest {

	@Autowired
	private ConversionService conversionService;

	@Test
	void convertPlaneClass() {
		UserEntity input = UserEntity.build(
				1L,
				UUID.randomUUID(),
				"male",
				"Petrov",
				"Andrey",
				LocalDate.of(1990, 10, 1),
				"petrov_a",
				"a.petrov@rambler.ru"
		);

		User output = conversionService.convert(input, User.class);

		assertEquals(output.getId(), input.getId());
		assertEquals(output.getUuid(), input.getUuid());
		assertEquals(output.getFirstName(), input.getFirstName());
		assertEquals(output.getLastName(), input.getLastName());
		assertEquals(output.getBirthday(), input.getBirthday());
		assertEquals(output.getLogin(), input.getLogin());
		assertEquals(output.getEmail(), input.getEmail());
	}

	@Test
	void convertClassUsingCustomConverter() {
		Car input = Car.builder()
				.id(1L)
				.make("Tesla")
				.model("model S")
				.build();

		CarDTO output = conversionService.convert(input, CarDTO.class);

		assertEquals(input.getId(), output.getCarId());
		assertEquals(input.getMake(), output.getMake());
		assertEquals(input.getModel(), output.getModelName());
	}

	@Test
	void convertClassWithNestedStructure() {
		UserRoleEntity input = UserRoleEntity.create(
				UserEntity.build(
						1L,
						UUID.randomUUID(),
						"male",
						"Petrov",
						"Andrey",
						LocalDate.of(1990, 10, 1),
						"petrov_a",
						"a.petrov@rambler.ru"
				),
				RoleEntity.build(
						1L,
						UUID.randomUUID(),
						"admin"
				)
		);

		UserRole output = conversionService.convert(input, UserRole.class);

		UserEntity inputUser = input.getUserEntity();
		RoleEntity inputRole = input.getRoleEntity();
		User outputUser = output.getUser();
		Role outputRole = output.getRole();

		assertEquals(outputUser.getId(), inputUser.getId());
		assertEquals(outputUser.getUuid(), inputUser.getUuid());
		assertEquals(outputUser.getFirstName(), inputUser.getFirstName());
		assertEquals(outputUser.getLastName(), inputUser.getLastName());
		assertEquals(outputUser.getBirthday(), inputUser.getBirthday());
		assertEquals(outputUser.getLogin(), inputUser.getLogin());
		assertEquals(outputUser.getEmail(), inputUser.getEmail());
		assertEquals(outputRole.getId(), inputRole.getId());
		assertEquals(outputRole.getUuid(), inputRole.getUuid());
		assertEquals(outputRole.getName(), inputRole.getName());
	}

	@TestConfiguration
	static class TestConfig {

		@Bean
		ConversionService conversionService(ObjectMapper mapper, Map<Pair<Class<?>, Class<?>>, Converter<?, ?>> convertersMap) {
			return new CustomizableConversionServiceImpl(mapper, convertersMap);
		}
	}
}
