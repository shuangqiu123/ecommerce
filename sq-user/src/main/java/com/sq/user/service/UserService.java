package com.sq.user.service;

import com.sq.pojo.Member;

public interface UserService {

    Member login(String username, String password);

    Member register(Member member);

    Member selectUserById(Long id);

}
