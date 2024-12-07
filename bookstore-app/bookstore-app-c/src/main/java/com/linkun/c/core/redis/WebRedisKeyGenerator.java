package com.linkun.c.core.redis;

import com.linkun.nosql.KeyGenerator;

public class WebRedisKeyGenerator extends KeyGenerator {

    private static final String PROJECT_NAME = "bookstore-app-c";

    public static String genKey(String primaryKey, String... funcs) {
        return genKeyInProject(PROJECT_NAME, primaryKey, funcs);
    }

}
