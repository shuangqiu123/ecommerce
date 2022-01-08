package com.sq.controller.user;

import com.sq.pojo.Member;
import com.sq.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("log in should return jwt and member details")
    public void loginInShouldReturnJwtAndMemberGivenValidLogInRequest() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("abcde123456");
        member.setPassword("$2a$10$a04BQtyY3vWypNJQBn4ox.eiIy/dg5kFsMT8gC22JXhRcUMq9MkkK");
        member.setEmail("user@123.com");
        member.setAuthToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjUsImNyZWF0ZWQiOjE2MjgzMzI5MzYzNDksImV4cCI6MTY0MDQyODkzNn0.t3f7cEWImGhM-MpV2bsd1JZKG_EfIMn4A7Xm7g5Y_Z5IQa-mtvKwfco_3T_e7uMp4GVf9WGiJU0Ve82MUHEYGQ");
        member.setRole(0);

        given(userService.login("abcde123456", "abcde123456", true))
                .willReturn(member);

        // When
        mockMvc.perform(post("/user/login/normal")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" + "\"username\":\"abcde123456\",\n" + "\"password\":\"abcde123456\"\n" + "}")
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.object.username").value("abcde123456"))
                .andExpect(jsonPath("$.object.email").value("user@123.com"))
                .andExpect(jsonPath("$.object.role").value(0));
    }


    @Test
    @DisplayName("log in with wrong user credentials should return log in unsuccessful message")
    public void loginInShouldReturnUnsuccessfulGivenInValidLogInRequest() throws Exception {
        Member member = new Member();
        member.setUsername("abcde123456");
        member.setPassword("$2a$10$a04BQtyY3vWypNJQBn4ox.eiIy/dg5kFsMT8gC22JXhRcUMq9MkkK");
        member.setEmail("user@123.com");
        member.setAuthToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjUsImNyZWF0ZWQiOjE2MjgzMzI5MzYzNDksImV4cCI6MTY0MDQyODkzNn0.t3f7cEWImGhM-MpV2bsd1JZKG_EfIMn4A7Xm7g5Y_Z5IQa-mtvKwfco_3T_e7uMp4GVf9WGiJU0Ve82MUHEYGQ");
        member.setRole(0);

        given(userService.login("abcde123456", "abcde123456", true))
                .willReturn(null);

        mockMvc.perform(post("/user/login/normal")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" + "\"username\":\"abcde123456\",\n" + "\"password\":\"abcde123456\"\n" + "}")
                .characterEncoding("utf-8"))
                .andExpect(jsonPath("$.errors.username").value("Username or password unmatched."));
    }
}
