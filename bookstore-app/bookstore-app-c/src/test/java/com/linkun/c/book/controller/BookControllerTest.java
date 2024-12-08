package com.linkun.c.book.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.linkun.api.auth.dto.LoginDto;
import com.linkun.response.JsonResult;
import com.linkun.utils.JsonUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private Logger logger = LoggerFactory.getLogger(BookControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getByIdTest() throws Exception {
//        String response = mockMvc.perform(post("/books/")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content("{\"username\":\"linkun3\",\"phone\":\"13795339535\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\"}"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        System.out.println("=======registerTest return:" + response);
//        if (StringUtils.isBlank(response)) {
//            System.out.println("=======registerTest return null=======");
//            Assert.assertTrue(false);
//        }
//        // 成功获得返回值，转为对象实例
//        JsonResult result = JsonUtil.toObject(response, JsonResult.class);
//        // 包装对象中isSuccess为true则表明业务处理成功
//        Assert.assertTrue(result.isSuccess());
//
//        System.out.println("=======registerTest complete=======");
    }

}
