package com.linkun.c.auth.redis;


import com.linkun.c.core.redis.WebRedisKeyGenerator;

public class AccountRedisKeyGenerator extends WebRedisKeyGenerator {

    public static final String REGISTER_VOUCHER = "register_voucher";

    public static String genRegisterVoucher(String key) {
        return genKey(REGISTER_VOUCHER, key);
    }
}
