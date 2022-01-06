package com.sq.service.impl;

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

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final SendGridUtil sendGridUtil;

    @Value("${server.frontendURL}")
    private String frontendURL;

    @Override
    public Member login(String username, String password) {
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
                String s = jwtUtil.generateToken(member);
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
        String s = jwtUtil.generateToken(createdMember);
        createdMember.setAuthToken(s);

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
        String token = jwtUtil.generateToken(member);
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
}
