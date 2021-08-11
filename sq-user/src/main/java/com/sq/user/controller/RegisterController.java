package com.sq.user.controller;

import com.sq.dto.ResponseMessage;
import com.sq.pojo.Member;
import com.sq.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/normal")
    public ResponseMessage register(@RequestBody Member member) {
        Member m = userService.register(member);
        ResponseMessage responseMessage = new ResponseMessage();
        if (m != null) {
            responseMessage.setCode(200);
            responseMessage.setMessage("register successful");
            responseMessage.setObject(m);
        } else {
            responseMessage.setCode(200);
            responseMessage.setMessage("register unsuccessful");
        }
        return responseMessage;
    }
}
