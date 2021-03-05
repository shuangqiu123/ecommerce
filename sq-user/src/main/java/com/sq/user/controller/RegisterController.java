package com.sq.user.controller;

import com.sq.dto.ResponseMessage;
import com.sq.pojo.Member;
import com.sq.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/login")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/normal")
    public ResponseMessage register(@RequestBody Member member) {
        userService.register(member);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(200);
        responseMessage.setMessage("register successful");
        return responseMessage;
    }
}
