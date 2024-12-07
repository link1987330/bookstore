package com.linkun.api.auth.redis;

import com.linkun.nosql.KeyGenerator;

public class AuthRedisKeyGenerator extends KeyGenerator {

    private static final String PROJECT_NAME = "linglie-api-auth";

    protected static String genKey(String primaryKey, String... funcs) {
        return genKeyInProject(PROJECT_NAME, primaryKey, funcs);
    }

}
