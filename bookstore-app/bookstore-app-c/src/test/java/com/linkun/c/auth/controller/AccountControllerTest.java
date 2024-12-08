package com.linkun.c.auth.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public void init() {
    }

    @Test
    public void testLogin() throws Exception {
        this.init();

        mockMvc.perform(post("/account/v1/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"linkun1\",\"phone\":\"13795339533\"},\"password\":\"e10adc3949ba59abbe56e057f20f883e\""))
                .andExpect(status().isOk());
    }
}
