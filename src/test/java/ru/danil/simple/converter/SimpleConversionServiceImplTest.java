package ru.danil.simple.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.danil.simple.converter.impl.SimpleConversionServiceImpl;
import ru.danil.simple.converter.model.User;
import ru.danil.simple.converter.model.UserEntity;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SimpleConversionServiceImplTest {

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

	@TestConfiguration
	static class TestConfig {

		@Bean
		public ConversionService conversionService(ObjectMapper mapper) {
			return new SimpleConversionServiceImpl(mapper);
		}
	}
}
