package com.fijimf.uberscraper.config;

import com.fijimf.uberscraper.db.user.model.Role;
import com.fijimf.uberscraper.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationSuccessHandler successHandler;

    @Autowired
    private final UserManager userManager;

    public SecurityConfig(PasswordEncoder passwordEncoder, AuthenticationSuccessHandler successHandler, UserManager userManager) {
        this.passwordEncoder = passwordEncoder;
        this.successHandler = successHandler;
        this.userManager = userManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userManager)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requiresChannel().anyRequest().requiresSecure().and()
                .anonymous().principal("anonymous").authorities(List.of(Role.ANONYMOUS))
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user").hasAuthority("USER")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return userManager;
    }

//    @Autowired
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;


}