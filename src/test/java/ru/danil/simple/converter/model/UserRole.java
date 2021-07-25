package ru.danil.simple.converter.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserRole {

	User user;

	Role role;
}
