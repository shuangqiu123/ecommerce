package com.sq.service.impl;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.sq.dto.ResponseMessage;
import com.sq.dto.user.UserDto;
import com.sq.dto.user.UserResetPasswordDto;
import com.sq.pojo.Member;
import com.sq.mapper.UserMapper;
import com.sq.service.UserService;
import com.sq.util.JWTUtil;
import com.sq.util.SendGridUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final SendGridUtil sendGridUtil;
    private final GoogleAuthorizationCodeFlow oauthClient;

    @Value("${server.frontendURL}")
    private String frontendURL;

    @Override
    public Member login(String username, String password, Boolean rememberMe) {
        Member member = userMapper.selectByUserName(username);
        if (member == null) {
            return null;
        }
        // get the user input password
        String realPassword = member.getPassword();

        try {
            // verify the password
            if (passwordEncoder.matches(password, realPassword)) {

                // set the JWTToken
                String s = "";
                if (rememberMe != null && rememberMe) {
                    s = jwtUtil.generateToken(member, JWTUtil.Time.MONTH);
                }
                else {
                    s = jwtUtil.generateToken(member, JWTUtil.Time.DAY);
                }
                member.setAuthToken(s);

                return member;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseMessage register(Member member) {
        ResponseMessage responseMessage = new ResponseMessage();
        UserDto userDto = new UserDto();
        responseMessage.setErrors(new HashMap<>());

        try {
            String password = member.getPassword();
            String encode = passwordEncoder.encode(password);
            member.setPassword(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // check if username is already in the database
        if (userMapper.selectByUserName(member.getUsername()) != null) {
            responseMessage.getErrors().put("username", "Username is already taken.");
        }
        // check if email is already in the database
        if (userMapper.selectByEmail(member.getEmail()) != null) {
            responseMessage.getErrors().put("email", "Email is already taken.");
        }
        if (responseMessage.getErrors().size() > 0) {
            return responseMessage;
        }

        //set create and update time
        member.setCreated(new Date(System.currentTimeMillis()));
        member.setUpdated(new Date(System.currentTimeMillis()));

        userMapper.insertSelective(member);
        Member createdMember = userMapper.selectByUserName(member.getUsername());

        // set the JWTToken
        String s = jwtUtil.generateToken(createdMember, JWTUtil.Time.DAY);
        createdMember.setAuthToken(s);
        try {
            sendGridUtil.sendEmail(createdMember.getEmail(), "Verify Your Email",
                    Map.of("emailVerifyLink", frontendURL + "/user/verifyEmail?token=" + s), "d-3698d92ccb234881a877c24011465068");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        responseMessage.setCode(200);
        BeanUtils.copyProperties(createdMember, userDto);
        responseMessage.setObject(userDto);
        return responseMessage;
    }

    @Override
    public Member selectUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void forgotPassword(String email) {
        Member member = userMapper.selectByEmail(email);
        if (member == null) {
            return;
        }
        String token = jwtUtil.generateToken(member, JWTUtil.Time.HOUR);
        System.out.println(token);
        Map<String, String> customization = Map.of("resetLink", frontendURL + "/user/resetPassword?token=" + token);
        try {
            sendGridUtil.sendEmail(email, "Reset Your Password", customization, "d-e8fa2691c4c44a4dbcf6396971e1a63f");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void resetPassword(UserResetPasswordDto dto, Long uid) {
        String password = dto.getPassword();
        Member member = userMapper.selectByPrimaryKey(uid);
        member.setPassword(passwordEncoder.encode(password));
        userMapper.updateByPrimaryKeySelective(member);
    }

    @Override
    public void verifyEmail(Long uid) {
        Member member = userMapper.selectByPrimaryKey(uid);
        member.setIsverified("Y");
        userMapper.updateByPrimaryKeySelective(member);
    }

    @Override
    public String getGoogleLoginUrl() {
        GoogleAuthorizationCodeRequestUrl url = oauthClient.newAuthorizationUrl();
        url.setRedirectUri(frontendURL + "/user/oauth/google");
        return url.toString();
    }

    @Override
    public Member signInByGoogleToken(String code) {
        GoogleAuthorizationCodeTokenRequest tokenRequest = oauthClient.newTokenRequest(code);
        tokenRequest.setRedirectUri(frontendURL + "/user/oauth/google");

        GoogleTokenResponse response;
        GoogleIdToken googleIdToken = null;
        try {
            response = tokenRequest.execute();
            googleIdToken = response.parseIdToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        return oauthSignIn(payload.getEmail(), payload.getEmail().substring(0, payload.getEmail().indexOf('@')));
    }

    private Member oauthSignIn(String email, String username) {
        Member userSelectedByEmail = userMapper.selectByEmail(email);
        if (userSelectedByEmail != null) {
            return userSelectedByEmail;
        }
        if (username.length() < 4) {
            username += UUID.randomUUID().toString().substring(0, 5);
        }
        Member userSelectedByUserName = userMapper.selectByUserName(username);
        if (userSelectedByUserName != null) {
            username += UUID.randomUUID().toString().substring(0, 5);
        }
        Member savedMember = new Member();
        savedMember.setUsername(username);
        savedMember.setEmail(email);
        savedMember.setPassword("unset");
        savedMember.setIsverified("Y");
        savedMember.setCreated(new Date(System.currentTimeMillis()));
        savedMember.setUpdated(new Date(System.currentTimeMillis()));
        userMapper.insertSelective(savedMember);
        return userMapper.selectByEmail(email);
    }
}