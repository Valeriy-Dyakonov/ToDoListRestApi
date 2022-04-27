package com.example.controller;

import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.domain.User;
import com.example.dto.AuthResponseDto;
import com.example.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthControllerTest {
    private static final String LOGIN = "test";
    private static final String PASSWORD = "test";

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAuthController() throws Exception {
        User user = createUser();
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/auth/register")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            String resultContent = mvcResult.getResponse().getContentAsString();
            AuthResponseDto result = objectMapper.readValue(resultContent, AuthResponseDto.class);

            Assert.assertNotNull(result);
            Assert.assertTrue(StringUtils.hasText(result.getToken()));

            mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/auth/sign_in")
                            .content(objectMapper.writeValueAsString(user))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            resultContent = mvcResult.getResponse().getContentAsString();
            result = objectMapper.readValue(resultContent, AuthResponseDto.class);

            Assert.assertNotNull(result);
            Assert.assertTrue(StringUtils.hasText(result.getToken()));

            mvcResult = mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/hasAccess")
                            .header("Authorization", "Bearer " + result.getToken())
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            boolean access = Boolean.parseBoolean(mvcResult.getResponse().getContentAsString());
            Assert.assertTrue(access);
        } catch (Exception e) {
            //do nothing
        } finally {
            User byLogin = userDao.findByLogin(user.getLogin());
            if (byLogin != null) {
                userDao.delete(byLogin);
            }
        }

    }

    private User createUser() {
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        return user;
    }

}
