package com.linkun.api.auth.remote;

import com.linkun.api.auth.bean.AuthSession;
import com.linkun.api.auth.exception.AuthorizeException;

public interface IAuthorizeSessionRemoteService {

    /**
     * 功能描述: <br>
     * 获取随机数,返回的sessionId是一个随机码，登录的时候会用到
     *
     * @param ip
     * @param platformSystem 平台系统
     * @return 返回AuthSession，包含sessionId和nonce
     */
    AuthSession createSession(String ip, String platformSystem);

    /**
     * 登录授权
     *
     * @param sessionId 存储nonce的token
     * @param ip 登录的IP
     * @param requestURI 授权请求的uri
     * @param loginName 用户名 不能为空
     * @param authorizeCode 授权的加密串
     * @param isRememberLogin 是否记住登录状态
     * @return 返回AuthSession对象
     *
     */
    AuthSession login(String sessionId, String ip, String requestURI, String loginName, String authorizeCode,
                      boolean isRememberLogin) throws AuthorizeException;

    /**
     * 获取session
     * 
     * @param sessionId
     * @return 返回sessionId对应的session
     */
    AuthSession getSession(String sessionId);

    /**
     * 
     * 功能描述: <br>
     * 更新session，刷新session的过期时间
     *
     * @param session
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void updateSession(AuthSession session);

    /**
     * 登出
     *
     * @param sessionId
     */
    void logoutSession(String sessionId);

//    /**
//     * 根据用户id登出除了excludeSessionToken以外的所有sessionToken
//     *
//     * @param userId 用户id
//     * @param excludeSessionId 排除的sessionId
//     */
//    void logoutUser(String userId, String excludeSessionId);

}
