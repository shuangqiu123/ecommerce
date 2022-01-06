package com.sq.security.component;

import cn.hutool.json.JSONUtil;
import com.sq.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Not logged in or token is invalid
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.FORBIDDEN.value(),"invalid token access", null);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().println(JSONUtil.parse(responseMessage));
        response.getWriter().flush();
    }

}
