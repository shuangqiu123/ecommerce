package com.sq.controller.user;

import com.sq.dto.ResponseMessage;
import com.sq.dto.user.UserSignUpDto;
import com.sq.pojo.Member;
import com.sq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/register")
public class RegisterController {
    private final UserService userService;

    @PostMapping("/normal")
    public ResponseEntity<ResponseMessage> register(@RequestBody @Valid UserSignUpDto userSignUpDto) {
        Member signUpMember = new Member();
        BeanUtils.copyProperties(userSignUpDto, signUpMember);

        ResponseMessage responseMessage = userService.register(signUpMember);
        if (responseMessage.getErrors().size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
        return ResponseEntity.ok(responseMessage);
    }
}
