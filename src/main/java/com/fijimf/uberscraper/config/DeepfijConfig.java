package com.fijimf.uberscraper.config;

import com.fijimf.uberscraper.db.user.model.User;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

@Configuration
public class DeepfijConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccess() {
        return new AuthenticationSuccessHandler() {
            private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                if (authentication.getPrincipal() instanceof User) {
                    User user = (User) authentication.getPrincipal();
                    if (user.getExpireCredentialsAt() != null) {
                        redirectStrategy.sendRedirect(request, response, "/changePassword");

                    } else {
                        redirectStrategy.sendRedirect(request, response, "/");
                    }
                } else {
                    redirectStrategy.sendRedirect(request, response, "/");
                }
            }
        };
    }

    @Bean
    public RandomStringGenerator randomStringGenerator() {
        SecureRandom random = new SecureRandom();
        char[][] pairs = {{'a', 'z'}, {'A', 'Z'}};
        return new RandomStringGenerator.Builder()
                .usingRandom(random::nextInt)
                .withinRange(pairs)
                .build();

    }

}
