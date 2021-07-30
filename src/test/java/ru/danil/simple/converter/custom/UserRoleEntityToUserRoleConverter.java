package ru.danil.simple.converter.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.danil.simple.converter.SimpleObjectsConverter;
import ru.danil.simple.converter.model.Role;
import ru.danil.simple.converter.model.User;
import ru.danil.simple.converter.model.UserRole;
import ru.danil.simple.converter.model.UserRoleEntity;

@Component
public class UserRoleEntityToUserRoleConverter implements Converter<UserRoleEntity, UserRole> {

	@Autowired
	private SimpleObjectsConverter simpleObjectsConverter;

	@Override
	public UserRole convert(UserRoleEntity input) {
		return UserRole.builder()
				.user(simpleObjectsConverter.convert(input.getUserEntity(), User.class))
				.role(simpleObjectsConverter.convert(input.getRoleEntity(), Role.class))
				.build();
	}
}
