package com.sq.security.service.impl;

import com.sq.pojo.Member;
import com.sq.security.mapper.UserDetailMapper;
import com.sq.security.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Override
    public Member getMemberByUid(String uid) {
        return userDetailMapper.selectByPrimaryKey(Long.parseLong(uid));
    }
}
