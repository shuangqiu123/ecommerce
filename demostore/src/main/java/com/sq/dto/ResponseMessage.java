package com.sq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage {

    // http code
    private int code;

    // message to front end
    private String message;

    // json object to front end
    private Object object;


    public ResponseMessage() {

    }
}
