package ru.danil.simple.converter.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class User {

	long id;

	@NotNull
	UUID uuid;

	@NotBlank
	String firstName;

	@NotBlank
	String lastName;

	LocalDate birthday;

	String login;

	@NotBlank
	String email;
}
