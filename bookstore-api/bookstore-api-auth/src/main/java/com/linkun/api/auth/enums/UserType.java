package com.linkun.api.auth.enums;

public enum UserType {

    C(1);

    private int type;

    private UserType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static UserType getByType(int type) {
        for (UserType userType : UserType.values()) {
            if (userType.getType() == type) {
                return userType;
            }
        }
        return null;
    }
}
