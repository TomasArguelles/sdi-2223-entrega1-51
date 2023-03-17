package com.uniovi.sdi2223entrega1n.interceptors;

import com.uniovi.sdi2223entrega1n.entities.CustomLog;
import com.uniovi.sdi2223entrega1n.repositories.LoggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Clase que se encarga de gestionar el evento de login correcto.
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoggingRepository loggingRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        CustomLog log = AuthUtil.getAuthLogInfo(request, response, authentication, "LOGIN-EX");

        // Guardar el log en la base de datos
        loggingRepository.save(log);

        response.sendRedirect("/home");
    }
}
