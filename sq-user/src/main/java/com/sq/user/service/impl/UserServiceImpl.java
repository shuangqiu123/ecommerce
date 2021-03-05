package com.sq.user.service.impl;

import com.sq.pojo.Member;
import com.sq.user.mapper.UserMapper;
import com.sq.user.service.UserService;
import com.sq.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean login(String username, String password) {
        Member member = userMapper.selectByUserName(username);
        if (member == null) {
            return false;
        }
        String realPassword = member.getPassword();

        try {
            return PasswordUtil.verify(realPassword, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void register(Member member) {
        try {
            member.setPassword(PasswordUtil.encode(member.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userMapper.insertSelective(member);
    }

    @Override
    public Member selectUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
