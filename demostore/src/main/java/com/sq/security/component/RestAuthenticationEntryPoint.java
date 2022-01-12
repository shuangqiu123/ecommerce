package com.sq.security.component;

import cn.hutool.json.JSONUtil;
import com.sq.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Unauthorized access for user
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), "token error", null);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(JSONUtil.parse(responseMessage));
        response.getWriter().flush();
    }
}
