package com.uniovi.sdi2223entrega1n.interceptors;

import com.uniovi.sdi2223entrega1n.entities.CustomLog;
import com.uniovi.sdi2223entrega1n.repositories.LoggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Clase que se encarga de manejar los errores de login.
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private LoggingRepository loggingRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        CustomLog log = AuthUtil.getAuthLogInfo(request, response, null, "LOGIN-ERR");

        // Guardar el log en la base de datos
        loggingRepository.save(log);

        String redirectUrl = request.getContextPath() + "/login?error";
        response.sendRedirect(redirectUrl);
    }
}
