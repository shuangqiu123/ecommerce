package com.sq.service;

import com.sq.dto.ResponseMessage;
import com.sq.dto.user.UserResetPasswordDto;
import com.sq.pojo.Member;

public interface UserService {

    Member login(String username, String password);

    ResponseMessage register(Member member);

    Member selectUserById(Long id);

    void forgotPassword(String email);

    void resetPassword(UserResetPasswordDto dto, Long uid);
}
