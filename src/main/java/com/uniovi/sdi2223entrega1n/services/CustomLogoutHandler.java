package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.entities.CustomLog;
import com.uniovi.sdi2223entrega1n.interceptors.AuthUtil;
import com.uniovi.sdi2223entrega1n.repositories.LoggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Manejador de eventos de logout.
 *
 * @version 1.0
 */
@Service
public class CustomLogoutHandler implements LogoutSuccessHandler {

    @Autowired
    private LoggingRepository loggingRepository;

    public CustomLogoutHandler() {

    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String username = authentication.getName();

        // Obtener datos de la peticion
        CustomLog log = AuthUtil.getAuthLogInfo(request, response, authentication, "LOGOUT");

        // Guardar el log en la base de datos
        loggingRepository.save(log);

        // Redireccionar a login
        response.sendRedirect("/login");
    }
}
