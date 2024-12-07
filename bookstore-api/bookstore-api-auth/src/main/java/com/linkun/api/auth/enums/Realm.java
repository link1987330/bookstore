package com.linkun.api.auth.enums;

public enum Realm {

    C(UserType.C);

    private UserType userType;

    private Realm(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public static Realm getRealmByUserType(UserType userType) {
        if (null == userType) {
            return null;
        }
        for (Realm realm : Realm.values()) {
            if (realm.getUserType().equals(userType)) {
                return realm;
            }
        }
        return null;
    }
}
