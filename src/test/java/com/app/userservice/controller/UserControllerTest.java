package com.app.userservice.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.userservice.components.requestBodies.User;
import com.app.userservice.components.responses.Error;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;
    private MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;

    private User VALID_USER_WITH_REQUIRED = new User("some.one@email.com", "s0m3s3cr3tp455w0rd");

    @Autowired
    public UserControllerTest(MockMvc mvc) {
        this.mvc = mvc;
        this.mapper = (new ObjectMapper()).setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test
    public void createUserSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .content(mapper.writeValueAsBytes(VALID_USER_WITH_REQUIRED))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isCreated());
        ;
    }
    @Test
    public void createUserWithOptionalNamesSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .content(mapper.writeValueAsBytes(VALID_USER_WITH_REQUIRED.setFirstName("Some")
                                .setLastName("One")))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isCreated());
        ;
    }

    @Test
    public void createUserWithoutPasswordBadRequest() throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("email", VALID_USER_WITH_REQUIRED.getEmail());

        mvc.perform(MockMvcRequestBuilders.post("/user").content(mapper.writeValueAsBytes(map))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserOnlyNamesFailsBadRequest() throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("firstName", "Some");
        map.put("lastName", "One");

        mvc.perform(MockMvcRequestBuilders.post("/user").content(mapper.writeValueAsBytes(map))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserEmptyBodyBadRequest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/{email}", VALID_USER_WITH_REQUIRED.getEmail())
                        .accept(CONTENT_TYPE))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(CONTENT_TYPE),
                        content().json(mapper.writeValueAsString(VALID_USER_WITH_REQUIRED)));
    }

    @Test
    public void getUserBadRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/{email}", "Some")
                        .contentType(CONTENT_TYPE)
                        .content(mapper.writeValueAsString(VALID_USER_WITH_REQUIRED))
                        .accept(CONTENT_TYPE))
                .andExpect(
                        status().isBadRequest());
    }

    @Test
    public void getUserNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/{email}", "test@google.com")
                        .accept(CONTENT_TYPE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(mapper.writeValueAsString(new Error("404", "User not found."))));
    }

    @Test
    public void updateUserSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/user/{email}", VALID_USER_WITH_REQUIRED.getEmail())
                        .content(mapper.writeValueAsString(VALID_USER_WITH_REQUIRED.setFirstName("test@google.com")))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isNoContent());
        ;
    }
}
