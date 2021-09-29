package com.app.userservice.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.userservice.components.requestBodies.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final MockMvc mvc;
    private User VALID_USER_WITH_REQUIRED = new User("some.one@email.com", "s0m3s3cr3tp455w0rd");
    private User VALID_USER_WITH_REQUIRED_OPTIONALS = new User("some.one@email.com", "s0m3s3cr3tp455w0rd");

    @Autowired
    public UserControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    public void createUser() throws Exception {
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/user").accept(MediaType.APPLICATION_JSON);


        mvc.perform(post)
                .andExpect(status().isCreated())
        ;
    }

    @Test
    public void getUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void updateUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void deleteUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void invalidArgumentPath() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }
}
