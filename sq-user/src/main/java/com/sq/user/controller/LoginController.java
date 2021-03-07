package com.sq.user.controller;
import com.sq.dto.ResponseMessage;
import com.sq.pojo.Member;
import com.sq.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user/login")
public class LoginController {
    @Autowired
    private UserService userService;


    @PostMapping("/normal")
    public ResponseMessage normalLogin(@RequestBody Member member) {

        Member m = userService.login(member.getUsername(), member.getPassword());
        ResponseMessage responseMessage = new ResponseMessage();

        if (m != null) {
            responseMessage.setCode(200);
            responseMessage.setMessage("login successful");
            responseMessage.setObject(m);
        } else {
            responseMessage.setCode(200);
            responseMessage.setMessage("login unsuccessful");
        }
        return responseMessage;
    }
}
