package ru.danil.simple.converter.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.danil.simple.converter.ConversionService;
import ru.danil.simple.converter.model.Role;
import ru.danil.simple.converter.model.User;
import ru.danil.simple.converter.model.UserRole;
import ru.danil.simple.converter.model.UserRoleEntity;

@Component
public class UserRoleEntityToUserRoleConverter implements Converter<UserRoleEntity, UserRole> {

	@Autowired
	private ConversionService conversionService;

	@Override
	public UserRole convert(UserRoleEntity input) {
		return UserRole.builder()
				.user(conversionService.convert(input.getUserEntity(), User.class))
				.role(conversionService.convert(input.getRoleEntity(), Role.class))
				.build();
	}
}
