package com.mts.work.mvc;

import com.mts.work.controller.UserController;
import com.mts.work.entity.User;
import com.mts.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private static final User USER_MOCK = new User(2, "User name");
    private static final String USER_JSON = "{\"name\":\"Test\"}";

    @Test
    public void createUser() throws Exception {
        mockMvc.perform(post("/user").contentType("application/json").content(USER_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getUser() throws Exception {
        Integer id = 0;
        doReturn(id).when(userService).create(USER_MOCK, "bbcc4621-d88f-4a94-ae2f-b38072bf5087");
        mockMvc.perform(get("/user/1").contentType("application/json"))
                .andExpect(status().isOk());
    }

}