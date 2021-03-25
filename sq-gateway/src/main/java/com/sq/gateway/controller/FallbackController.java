package com.sq.gateway.controller;

import com.sq.dto.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/")
    public ResponseMessage fallback() {
        return new ResponseMessage(500, "response is too long", null);
    }
}
