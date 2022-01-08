package com.sq.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$",
            message = "Invalid username")
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,20}$",
            message = "Invalid password")
    private String password;

    private Boolean rememberMe;
}
