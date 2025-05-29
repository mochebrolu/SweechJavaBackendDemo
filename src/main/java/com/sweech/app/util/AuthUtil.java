package com.sweech.app.util;



import org.springframework.security.core.Authentication;

import com.sweech.app.security.UserPrincipal;

public class AuthUtil {
    public static Long getUserId(Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        return userPrincipal.getId();
    }
}
