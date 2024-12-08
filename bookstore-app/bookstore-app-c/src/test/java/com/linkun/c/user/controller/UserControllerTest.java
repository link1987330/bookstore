package com.linkun.c.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.linkun.api.auth.dto.LoginDto;
import com.linkun.response.JsonResult;
import com.linkun.utils.JsonUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void userInfoTest() throws Exception {

        String sessionId = "fc7045bfc9b74fb499c351554c467f60"; // 登录前从link中获取到的
        String response = mockMvc.perform(get("/user")
                    .header("accessToken", sessionId) // header中放accessToken，或者直接放cookie中
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("=======userInfoTest return:" + response);
        if (StringUtils.isBlank(response)) {
            System.out.println("=======userInfoTest return null=======");
            Assert.assertTrue(false);
        }
        // 成功获得返回值，转为对象实例
        JsonResult result = JsonUtil.toObject(response, JsonResult.class);
        // 包装对象中isSuccess为true则表明业务处理成功
        Assert.assertTrue(result.isSuccess());

        System.out.println("=======userInfoTest complete=======");
    }

}
