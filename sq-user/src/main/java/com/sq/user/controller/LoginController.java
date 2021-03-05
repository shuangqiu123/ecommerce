package com.sq.user.controller;
import com.sq.dto.ResponseMessage;
import com.sq.pojo.Member;
import com.sq.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/normal")
    public ResponseMessage normalLogin(@RequestBody Member member) {
        boolean login = userService.login(member.getUsername(), member.getPassword());
        ResponseMessage responseMessage = new ResponseMessage();

        if (login) {
            responseMessage.setCode(200);
            responseMessage.setMessage("login successful");
        } else {
            responseMessage.setCode(400);
            responseMessage.setMessage("login unsuccessful");
        }
        return responseMessage;
    }
}
