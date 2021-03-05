package com.sq.user.service;

import com.sq.pojo.Member;

public interface UserService {

    public boolean login(String username, String password);

    public void register(Member member);

    public Member selectUserById(Long id);

}
