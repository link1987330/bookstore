package com.linkun.c.auth.controller;

import com.linkun.api.auth.dto.LoginDto;
import com.linkun.response.JsonResult;
import com.linkun.utils.JsonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    private Logger logger = LoggerFactory.getLogger(AccountControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerTest() throws Exception {
        String response = mockMvc.perform(post("/account/v1/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"linkun3\",\"phone\":\"13795339535\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("=======registerTest return:" + response);
        if (StringUtils.isBlank(response)) {
            System.out.println("=======registerTest return null=======");
            Assert.assertTrue(false);
        }
        // 成功获得返回值，转为对象实例
        JsonResult result = JsonUtil.toObject(response, JsonResult.class);
        // 包装对象中isSuccess为true则表明业务处理成功
        Assert.assertTrue(result.isSuccess());

        System.out.println("=======registerTest complete=======");
    }

    @Test
    public void linkTest() throws Exception {
        String response = mockMvc.perform(get("/account/v1/link"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("=======linkTest return:" + response);
        if (StringUtils.isBlank(response)) {
            System.out.println("=======linkTest return null=======");
            Assert.assertTrue(false);
        }
        // 成功获得返回值，转为对象实例
        JsonResult result = JsonUtil.toObject(response, JsonResult.class);
        // 包装对象中isSuccess为true则表明业务处理成功
        Assert.assertTrue(result.isSuccess());

        System.out.println("=======linkTest complete=======");
    }

    @Test
    public void loginTest() throws Exception {
        String account = "13795339533";
        String password = "e10adc3949ba59abbe56e057f20f883e";
        String sessionId = "fc7045bfc9b74fb499c351554c467f60"; // 应该要改成调用link并从其结果中拿
        String nonce = "c472447903299d84eece85b1904445a5"; // 应该要改成调用link并从其结果中拿

        String authCode = this.generateAuthorizeCode(account, "BOOKSTORE_C", password, "/account/v1/login", nonce);
        LoginDto loginDto = new LoginDto();
        loginDto.setAccount(account);
        loginDto.setAuthorizeCode(authCode);

        String response = mockMvc.perform(post("/account/v1/login")
                .header("accessToken", sessionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(loginDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("=======loginTest return:" + response);
        if (StringUtils.isBlank(response)) {
            System.out.println("=======loginTest return null=======");
            Assert.assertTrue(false);
        }
        // 成功获得返回值，转为对象实例
        JsonResult result = JsonUtil.toObject(response, JsonResult.class);
        // 包装对象中isSuccess为true则表明业务处理成功
        Assert.assertTrue(result.isSuccess());

        System.out.println("=======loginTest complete=======");
    }

    private String generateAuthorizeCode(String loginName, String realm, String md5Password, String requestURI,
                                         String nonce) {
        StringBuilder a1 = new StringBuilder();
        a1.append(loginName).append(":").append(realm).append(":").append(md5Password);

        StringBuilder a2 = new StringBuilder();
        a2.append("POST:").append(requestURI);

        String ha1 = DigestUtils.md5Hex(a1.toString());

        String ha2 = DigestUtils.md5Hex(a2.toString());

        return DigestUtils.md5Hex(ha1 + ":" + nonce + ":" + ha2);
    }
}
