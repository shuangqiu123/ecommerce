package com.sq.controller.user;
import com.sq.dto.ResponseMessage;
import com.sq.dto.user.UserDto;
import com.sq.dto.user.UserLoginDto;
import com.sq.pojo.Member;
import com.sq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/login")
public class LoginController {

    private final UserService userService;

    @PostMapping("/normal")
    public ResponseMessage normalLogin(@RequestBody @Valid UserLoginDto userLoginDto) {

        Member m = userService.login(userLoginDto.getUsername(), userLoginDto.getPassword());
        UserDto userDto = new UserDto();
        ResponseMessage responseMessage = new ResponseMessage();
        if (m != null) {
            BeanUtils.copyProperties(m, userDto);
            responseMessage.setCode(200);
            responseMessage.setMessage("login successful");
            responseMessage.setObject(userDto);
        } else {
            responseMessage.setCode(401);
            responseMessage.setMessage("login unsuccessful");
        }
        return responseMessage;
    }
}
