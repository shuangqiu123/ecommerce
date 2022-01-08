package com.sq.controller.user;
import com.sq.dto.ResponseMessage;
import com.sq.dto.user.OAuthCode;
import com.sq.dto.user.UserDto;
import com.sq.dto.user.UserLoginDto;
import com.sq.pojo.Member;
import com.sq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/login")
public class LoginController {

    private final UserService userService;

    @PostMapping("/normal")
    public ResponseEntity<ResponseMessage> normalLogin(@RequestBody @Valid UserLoginDto userLoginDto) {

        Member m = userService.login(userLoginDto.getUsername(), userLoginDto.getPassword(), userLoginDto.getRememberMe());
        UserDto userDto = new UserDto();
        ResponseMessage responseMessage = new ResponseMessage();
        if (m != null) {
            BeanUtils.copyProperties(m, userDto);
            responseMessage.setCode(200);
            responseMessage.setMessage("login successful");
            responseMessage.setObject(userDto);
            return ResponseEntity.ok().body(responseMessage);
        }
        responseMessage.setCode(404);
        responseMessage.setErrors(Map.of("username", "Username or password unmatched."));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
        .body(responseMessage);
    }

    @GetMapping("/google/url")
    public ResponseEntity<ResponseMessage> googleLoginUrl() {
        String url = userService.getGoogleLoginUrl();
        return ResponseEntity.ok().body(new ResponseMessage(200, "success", url));
    }

    @PostMapping("/google/code")
    public ResponseEntity<ResponseMessage> googleLoginCode(@RequestBody OAuthCode code) {
        Member user = userService.signInByGoogleToken(code.getCode());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return ResponseEntity.ok().body(new ResponseMessage(200, "success", userDto));
    }
}
