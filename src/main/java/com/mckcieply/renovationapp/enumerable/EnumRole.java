package com.mckcieply.renovationapp.enumerable;

public enum EnumRole {

    USER,
    ADMIN;

    /**
     * Converts a string to the corresponding EnumRole.
     *
     * @param name the string representation of the role
     * @return the EnumRole corresponding to the given string
     * @throws IllegalArgumentException if the string does not match any role
     */
    public static EnumRole fromString(String name) {

        for (EnumRole role : EnumRole.values()) {
            if (role.toString().equals(name))
                return role;
        }
        throw new IllegalArgumentException("Invalid role name");
    }
}
