package com.sq.controller.user;

import com.sq.dto.ResponseMessage;
import com.sq.pojo.Member;
import com.sq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/user/register")
public class RegisterController {
    private final UserService userService;

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
