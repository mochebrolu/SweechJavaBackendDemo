package com.sweech.app.interceptor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sweech.app.security.TokenService;
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.contains("/signup") || uri.contains("/login")) return true;

        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            setError(response, 401, "Missing or invalid token");
            return false;
        }

        String token = auth.substring(7);
        if (!tokenService.isValid(token)) {
            setError(response, 401, "Token expired or invalid");
            return false;
        }

        return true;
    }

    private void setError(HttpServletResponse res, int status, String msg) throws IOException {
        res.setStatus(status);
        res.setContentType("application/json");
        res.getWriter().write("{\"error\":\"unauthorized\",\"message\":\"" + msg + "\"}");
    }
}


