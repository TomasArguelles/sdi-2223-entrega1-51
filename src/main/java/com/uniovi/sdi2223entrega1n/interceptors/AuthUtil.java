package com.uniovi.sdi2223entrega1n.interceptors;

import com.uniovi.sdi2223entrega1n.entities.CustomLog;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;

/**
 * Clase de utilidad para obtener información de autenticación.
 * <p>
 * Esta clase contiene métodos estáticos que permiten obtener información de
 * autenticación.
 * <p>
 *
 * @version 1.0
 */
public class AuthUtil {

    /**
     * Obtiene la información del log de autenticación.
     *
     * @param request        Petición HTTP recibida.
     * @param response       Respuesta HTTP a enviar.
     * @param authentication Información de la autenticación spring.
     * @param logTypeCode    Tipo de log.
     * @return Información del log de autenticación.
     */
    public static CustomLog getAuthLogInfo(HttpServletRequest request, HttpServletResponse response,
                                           Authentication authentication, String logTypeCode) {
        // Nombre del usuario en sesion
        String username = "anonymous";
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        // URI
        String endPointName = request.getRequestURI();

        // Método HTTP recibido.
        String httpMethod = request.getMethod();

        // Código de estado de la respuesta HTTP
        int responseStatus = response.getStatus();

        // Cabecera HTTP indicando el tipo de log - [LOGOUT].

        // Idioma de la respuesta HTTP
        Locale responseLocale = response.getLocale();

        // IP del equipo que realiza la peticion
        String remoteAddress = request.getRemoteAddr();

        // Fecha y hora actual
        Timestamp currentTime = Timestamp.from(Instant.now());

        // Guardar el log en la base de datos
        return new CustomLog(logTypeCode, httpMethod, responseStatus, remoteAddress, responseLocale, currentTime, endPointName, null, username);
    }
}
