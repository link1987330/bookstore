/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.linkun.nosql;

import org.apache.commons.lang3.StringUtils;

public abstract class KeyGenerator {

    public static final String CACHE_KEY_SEPARATOR = ".";

    protected static String genKeyInProject(String projectName, long primaryKey, String... funcs) {
        return genKeyInProject(projectName, String.valueOf(primaryKey), funcs);
    }

    protected static String genKeyInProject(String projectName, String primaryKey, String... funcs) {
        StringBuilder sb = new StringBuilder();
        sb.append(projectName).append(CACHE_KEY_SEPARATOR).append(primaryKey);
        for (String func : funcs) {
            if (StringUtils.isNotEmpty(func)) {
                sb.append(CACHE_KEY_SEPARATOR).append(func);
            }
        }
        return sb.toString();
    }
}
