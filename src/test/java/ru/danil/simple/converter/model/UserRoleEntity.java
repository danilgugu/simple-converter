package ru.danil.simple.converter.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRoleEntity {

	public static UserRoleEntity create(UserEntity userEntity, RoleEntity roleEntity) {
		return new UserRoleEntity(
				userEntity,
				roleEntity
		);
	}

	@NotNull
	private UserEntity user;

	@NotNull
	private RoleEntity role;
}
