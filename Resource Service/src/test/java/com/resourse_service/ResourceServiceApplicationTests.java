package com.resourse_service;

import com.resourse_service.controllers.ResourceServiceController;
import com.resourse_service.db.entities.Song;
import com.resourse_service.db.services.SongService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
@RunWith(SpringRunner.class)
class ResourceServiceApplicationTests {

    @Autowired
    private ResourceServiceController controller;

    @Autowired
    WebApplicationContext context;

    @MockBean
    SongService songService;


    @Before
    public void setup() {
        Song song = new Song();
        song.setId(87);
        song.setSongPath("SomePath");
        Mockito.when(songService.getById(87)).thenReturn(song);
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(controller);
        RestAssuredMockMvc.webAppContextSetup(context);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}
