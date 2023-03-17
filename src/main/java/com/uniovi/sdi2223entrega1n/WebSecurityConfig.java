package com.uniovi.sdi2223entrega1n;

import com.uniovi.sdi2223entrega1n.interceptors.CustomAccessDeniedHandler;
import com.uniovi.sdi2223entrega1n.interceptors.LoginFailureHandler;
import com.uniovi.sdi2223entrega1n.interceptors.LoginSuccessHandler;
import com.uniovi.sdi2223entrega1n.services.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomLogoutHandler logoutHandler;

    @Autowired
    private LoginSuccessHandler loginHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Redireccion para usuarios no autorizados
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/script/**", "/", "/signup", "/login/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user/list").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user/deleteSelected").hasAuthority("ROLE_ADMIN")
                .antMatchers("/offer/add", "/offer/list").hasAuthority("ROLE_STANDARD")
                .antMatchers("/conversation/**").hasAuthority("ROLE_STANDARD")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(loginHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(logoutHandler)
                .permitAll();
    }

}
