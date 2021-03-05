package com.sq.dto;

import lombok.Data;

@Data
public class ResponseMessage {

    // http code
    private int code;

    // message to front end
    private String message;

    // json object to front end
    private Object object;
}
