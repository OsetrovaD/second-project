package com.osetrova.project.entity.enumonly;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    USER("Пользователь"),
    ADMIN("Администратор"),
    SUPER_ADMIN("Суперадминистратор");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public static Role getByName(String name) {
        return Arrays.stream(values())
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
