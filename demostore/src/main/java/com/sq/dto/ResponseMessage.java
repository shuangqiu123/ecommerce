package com.sq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    // http code
    private int code;

    // message to front end
    private String message;

    // json object to front end
    private Object object;

    private Map<String, String> errors;

    public ResponseMessage(int code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }
}
