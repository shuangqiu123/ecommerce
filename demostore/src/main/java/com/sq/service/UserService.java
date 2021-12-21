package com.sq.service;

import com.sq.pojo.Member;

public interface UserService {

    Member login(String username, String password);

    Member register(Member member);

    Member selectUserById(Long id);

}
