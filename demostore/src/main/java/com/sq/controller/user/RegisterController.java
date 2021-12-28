package com.sq.controller.user;

import com.sq.dto.ResponseMessage;
import com.sq.dto.user.UserSignUpDto;
import com.sq.pojo.Member;
import com.sq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/register")
public class RegisterController {
    private final UserService userService;

    @PostMapping("/normal")
    public ResponseMessage register(@RequestBody @Valid UserSignUpDto userSignUpDto) {
        Member signUpMember = new Member();
        BeanUtils.copyProperties(userSignUpDto, signUpMember);


        Member m = userService.register(signUpMember);

        ResponseMessage responseMessage = new ResponseMessage();
        if (m != null) {
            responseMessage.setCode(200);
            responseMessage.setMessage("register successful");
            responseMessage.setObject(m);
        } else {
            responseMessage.setCode(404);
            responseMessage.setMessage("register unsuccessful");
        }
        return responseMessage;
    }
}
