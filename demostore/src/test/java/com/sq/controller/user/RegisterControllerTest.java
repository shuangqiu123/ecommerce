package com.sq.controller.user;

import com.sq.pojo.Member;
import com.sq.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("register should return jwt and member details")
    public void registerShouldReturnJwtAndMemberGivenValidRegisterRequest() throws Exception {
        // Given
        Member member = new Member();
        Member returnedMember = new Member();
        member.setUsername("newUser");
        returnedMember.setUsername("newUser");
        member.setPassword("123456");
        returnedMember.setPassword("$2a$10$a04BQtyY3vWypNJQBn4ox.eiIy/dg5kFsMT8gC22JXhRcUMq9MkkK");
        member.setEmail("user123@123.com");
        returnedMember.setEmail("user123@123.com");
        member.setRole(0);
        returnedMember.setRole(0);

        given(userService.register(member))
                .willReturn(returnedMember);

        // When
        mockMvc.perform(post("/user/register/normal")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"email\":\"user123@123.com\",\n" +
                        "    \"username\": \"newUser\",\n" +
                        "    \"password\": \"123456\",\n" +
                        "    \"role\": 0\n" +
                        "}")
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.object.username").value("newUser"))
                .andExpect(jsonPath("$.object.email").value("user123@123.com"))
                .andExpect(jsonPath("$.object.role").value(0));
    }
}