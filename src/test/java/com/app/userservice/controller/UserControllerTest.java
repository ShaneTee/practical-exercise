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

    private User VALID_USER = new User("some.one@email.com", "s0m3s3cr3tp455w0rd");

    @Autowired
    public UserControllerTest(MockMvc mvc) {
        this.mvc = mvc;
        this.mapper = (new ObjectMapper()).setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Test
    public void createUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .content(mapper.writeValueAsBytes(VALID_USER))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isCreated());
        ;
    }
    @Test
    public void createUserWithOptionalNames() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .content(mapper.writeValueAsBytes(VALID_USER.setFirstName("Some")
                                .setLastName("One")))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isCreated());
        ;
    }

    @Test
    public void createUserWithoutPassword() throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("email", VALID_USER.getEmail());

        mvc.perform(MockMvcRequestBuilders.post("/user").content(mapper.writeValueAsBytes(map))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserOnlyNames() throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("firstName", "Some");
        map.put("lastName", "One");

        mvc.perform(MockMvcRequestBuilders.post("/user").content(mapper.writeValueAsBytes(map))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserEmptyBody() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/{email}", VALID_USER.getEmail())
                        .accept(CONTENT_TYPE))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(CONTENT_TYPE),
                        content().json(mapper.writeValueAsString(VALID_USER)));
    }

    @Test
    public void getUserEmailPath() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/{email}", "Some")
                        .contentType(CONTENT_TYPE)
                        .content(mapper.writeValueAsString(VALID_USER))
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
        String updatedUserJson = mapper.writeValueAsString(VALID_USER.setFirstName("Test"));

        mvc.perform(MockMvcRequestBuilders.put("/user/{email}", VALID_USER.getEmail())
                        .content(updatedUserJson)
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isNoContent());

        mvc.perform(MockMvcRequestBuilders.get("/user/{email}", VALID_USER.getEmail())
                            .accept(CONTENT_TYPE))
                    .andExpectAll(status().isOk(),
                            content().contentType(CONTENT_TYPE),
                            content().json(updatedUserJson)
                    );
    }

    @Test
    public void updateUserNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/user/{email}", "test@google.com")
                        .content(mapper.writeValueAsString(VALID_USER.setFirstName("Test")))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isNotFound());
        ;
    }


    @Test
    public void updateUserInvalidEmailPath() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/user/{email}", "someone")
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
        ;
    }

    @Test
    public void updateUserInvalidEmailBody() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/user/{email}", VALID_USER.getEmail())
                        .content(mapper.writeValueAsString(VALID_USER.setEmail("someone")))
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
        ;
    }

    @Test
    public void updateUserEmptyBody() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/user/{email}", VALID_USER.getEmail())
                        .contentType(CONTENT_TYPE)
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
        ;
    }

    @Test
    public void deleteUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/user/{email}", VALID_USER.getEmail())
                        .accept(CONTENT_TYPE))
                .andExpect(status().isNoContent());

        getUser();
    }

    @Test
    public void deleteUserInvalidEmailPath() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/user/{email}", "some")
                        .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }
}
