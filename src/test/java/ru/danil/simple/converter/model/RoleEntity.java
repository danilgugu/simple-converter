package ru.danil.simple.converter.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleEntity {

	public static RoleEntity build(
			long id,
			UUID uuid,
			String name
	) {
		return new RoleEntity(
				id,
				uuid,
				name
		);
	}

	@NotNull
	private Long id;

	@NotNull
	private UUID uuid;

	@NotBlank
	private String name;
}
