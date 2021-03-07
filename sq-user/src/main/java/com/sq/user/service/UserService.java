package com.sq.user.service;

import com.sq.pojo.Member;

public interface UserService {

    public Member login(String username, String password);

    public Member register(Member member);

    public Member selectUserById(Long id);

}
