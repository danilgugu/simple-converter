package ru.danil.simple.converter.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {

	public static UserEntity build(
			long id,
			UUID uuid,
			String sex,
			String firstName,
			String lastName,
			LocalDate birthday,
			String login,
			String email
	) {
		return new UserEntity(
				id,
				uuid,
				sex,
				firstName,
				lastName,
				birthday,
				login,
				email
		);
	}

	@NotNull
	private Long id;

	@NotNull
	private UUID uuid;

	private String sex;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	private LocalDate birthday;

	private String login;

	@NotBlank
	private String email;
}
