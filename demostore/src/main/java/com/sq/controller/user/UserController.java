package com.sq.controller.user;

import com.sq.dto.ResponseMessage;
import com.sq.dto.user.UserResetPasswordDto;
import com.sq.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/forgotPassword")
    public ResponseEntity<ResponseMessage> forgotPassword(@RequestParam @Email String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok(new ResponseMessage(200, "success", null));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseMessage> forgotPassword(@RequestBody @Valid UserResetPasswordDto dto, HttpServletRequest httpServletRequest) {
        userService.resetPassword(dto, getUid(httpServletRequest));
        return ResponseEntity.ok(new ResponseMessage(200, "success", null));
    }

    private Long getUid(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return Long.parseLong(userId);
    }
}
