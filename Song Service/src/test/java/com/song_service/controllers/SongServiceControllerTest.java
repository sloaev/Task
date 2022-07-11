package com.song_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.song_service.dto.SongDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
class SongServiceControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private SongServiceController controller;

    private static Integer id;

    @Test
    @Order(0)
    public void contextLoads(){
        assertNotNull(controller);
    }

    @Test
    @Order(1)
    void postInfo() throws Exception {
        SongDto dto = new SongDto("Haski","mur","Marmelad","10:23",17,2017);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(dto);
        MvcResult result = mvc.perform(post("/soser/postInfo").content(json).contentType("application/json"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        id = Integer.valueOf(result.getResponse().getContentAsString().replaceAll("[^0-9]",""));
    }

    @Test
    @Order(2)
    void getInfo() throws Exception {
        mvc.perform(get("/soser/getInfo?id="+ id).contentType("application/json"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @Order(3)
    void deleteInfo() throws Exception {
        mvc.perform(delete("/soser/delete")
                        .content("[17]")
                        .contentType("application/json"))
                .andExpect(status().is(200));
    }
}