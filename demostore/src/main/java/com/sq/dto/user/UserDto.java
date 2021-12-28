package com.sq.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Date created;

    private Date updated;

    private String sex;

    private String address;

    private Integer state;

    private String file;

    private String description;

    private Integer points;

    private BigDecimal balance;

    private String isverified;

    private String authToken;

    private Integer role;
}
