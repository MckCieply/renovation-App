package com.mckcieply.renovationapp.enumerable;

public enum EnumRole {

    USER,
    ADMIN;

    public static EnumRole fromString(String name) {

        for (EnumRole role : EnumRole.values()) {
            if (role.toString().equals(name))
                return role;
        }
        throw new IllegalArgumentException("Invalid role name");
    }
}
