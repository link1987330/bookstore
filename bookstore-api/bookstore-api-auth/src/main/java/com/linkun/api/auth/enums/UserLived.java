package com.linkun.api.auth.enums;

public enum UserLived {
    NORMAL(0), FREEZED(1), DELETED(2);

    private int live;

    public int getLive() {
        return live;
    }

    private UserLived(int live) {
        this.live = live;
    }

}
