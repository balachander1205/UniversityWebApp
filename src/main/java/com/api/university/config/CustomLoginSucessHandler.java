package com.api.university.config;

import com.api.university.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@Slf4j
public class CustomLoginSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    HttpSession session;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    throws IOException {
        // TODO Auto-generated method stub
        String userName = "";
        if(authentication.getPrincipal() instanceof Principal) {
            userName = ((Principal)authentication.getPrincipal()).getName();
        }else {
            userName = ((User)authentication.getPrincipal()).getUsername();
        }
        log.info("userName: " + userName);
        session.setAttribute("userId", userName);
        String targetUrl = determineTargetUrl(authentication);
        if(response.isCommitted()) return;
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication){
        String url = "/api/login?error=true";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for(GrantedAuthority a : authorities){
            roles.add(a.getAuthority());
        }
        if(roles.contains("ADMIN")){
            url = "/api/dashboardDetails";
        }else if(roles.contains("USER")) {
            url = "/api/dashboardDetails";
        }
        return url;
    }
}
