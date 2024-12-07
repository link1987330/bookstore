package com.linkun.api.auth.redis;


public class RedisKeyGenerator extends AuthRedisKeyGenerator {

    private static final String SESSION_KEY_SUFFIX = "session";

    /**
     * 用户session的key
     *
     * @param sessionId
     * @return
     */
    public static String genSessionKey(String sessionId) {
        return genKey(sessionId, SESSION_KEY_SUFFIX);
    }

    /**
     * 用户sessionId列表的key
     *
     * @param userId
     * @return
     */
    public static String genSessionIdListKey(String userId) {
        return genKey(userId, SESSION_KEY_SUFFIX);
    }

    /**
     * 
     * 功能描述: <br>
     * 某ip登录失败的次数
     *
     * @param ip
     * @return
     */
    public static String genIpLoginFailsKey(String ip) {
        return genKey(ip, "loginfails");
    }

    /**
     * 用户当前session的key
     * 
     * @param userId
     * @param realm
     * @return
     */
    public static String genCurrentSessionKey(String userId, String realm, String userType) {
        return genKey(userId, realm, userType, SESSION_KEY_SUFFIX);
    }

}
