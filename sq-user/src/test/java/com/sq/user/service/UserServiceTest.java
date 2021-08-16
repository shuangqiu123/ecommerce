package com.sq.user.service;

import com.sq.pojo.Member;
import com.sq.user.mapper.UserMapper;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserMapper userMapper;

    @Before
    public void setup() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @DisplayName("Log in should return JWT and Member given valid username and password")
    public void logInShouldReturnJWTAndMemberGivenValidUsernameAndPassword() {
        // Given
        String username = "user";
        String password = "123456";
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member();
        member.setUsername("user");
        member.setPassword(encodedPassword);
        given(userMapper.selectByUserName("user"))
                .willReturn(member);

        Member result = userService.login(username, password);
        assertNotNull(result.getAuthToken());
        assertEquals("user", result.getUsername());
    }

}
