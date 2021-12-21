package com.sq.service.impl;

import com.sq.pojo.Member;
import com.sq.mapper.UserMapper;
import com.sq.service.UserService;
import com.sq.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

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
    public Member register(Member member) {
        try {
            String password = member.getPassword();
            String encode = passwordEncoder.encode(password);
            member.setPassword(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // check if username is already in the database
        Member m = userMapper.selectByUserName(member.getUsername());
        if (m != null)
            return null;

        //set create and update time
        member.setCreated(new Date(System.currentTimeMillis()));
        member.setUpdated(new Date(System.currentTimeMillis()));

        userMapper.insertSelective(member);
        Member createdMember = userMapper.selectByUserName(member.getUsername());

        // set the JWTToken
        String s = jwtUtil.generateToken(createdMember);
        createdMember.setAuthToken(s);

        return createdMember;
    }

    @Override
    public Member selectUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
