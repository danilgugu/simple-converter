package ru.danil.simple.converter.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Role {

	long id;

	UUID uuid;

	String name;
}
