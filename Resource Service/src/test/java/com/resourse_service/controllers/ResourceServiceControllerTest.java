package com.resourse_service.controllers;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class ResourceServiceControllerTest {

    @Autowired
    ResourceServiceController controller;

    @Autowired
    MockMvc mvc;
    private static Integer id;

    @Test
    @Order(0)
    public void contextLoads(){
        assertNotNull(controller);
    }

    @Test
    @Order(1)
    void hey() throws Exception {
        this.mvc.perform(get("/reser/hey")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello there")));
    }

    @Test
    @Order(2)
    void uploadSongv2() throws Exception {
        MockMultipartFile file = new MockMultipartFile("data", "testMusic.mp3", "audio/mpeg", "some song".getBytes());
        MvcResult result =  mvc.perform(MockMvcRequestBuilders.multipart("/reser/uploadv2").file("file",file.getBytes()).header("test","true"))
                .andExpect(status().is(200)).andReturn();
        id = Integer.valueOf(result.getResponse().getContentAsString().replaceAll("[^0-9]",""));
    }

    @Test
    @Order(3)
    void downloadSongV2() throws Exception {
        mvc.perform(get("/reser/downloadv2?id="+ id).contentType("application/json"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @Order(4)
    void deleteSong() throws Exception {
        mvc.perform(delete("/reser/delete")
                        .content("[" + id.toString() + "]")
                        .contentType("application/json")
                        .header("test","true"))
                .andExpect(status().is(200));
    }
}