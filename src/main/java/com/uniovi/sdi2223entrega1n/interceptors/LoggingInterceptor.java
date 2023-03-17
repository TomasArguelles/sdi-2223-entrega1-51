package com.uniovi.sdi2223entrega1n.interceptors;

import com.uniovi.sdi2223entrega1n.entities.CustomLog;
import com.uniovi.sdi2223entrega1n.entities.LogType;
import com.uniovi.sdi2223entrega1n.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * --- Logger de la aplicación ---
 * <p>
 * Se encarga de interceptar todas las peticiones HTTP y guardar en el log
 * un registro de todas las acciones del usuario. Cada linea de log contiene:
 * <p>
 * <code>
 *     <ul>
 *         <li>Tipo de log</li>
 *         <li>Método HTTP</li>
 *         <li>Endpoint consultado</li>
 *         <li>Texto descriptivo de la accion realizada</li>
 *     </ul>
 * </code>
 *
 * @version 1.0.0
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    // Nombre del endpoint de login
    private static final String LOGIN_ENDPOINT = "login";

    // Nombre del endpoint para el registro de usuarios.

    // Nombre del endpoint para cerrar la sesión en curso.

    // Utilizado para marcar aquellos endpoints no deseados
    private static final String UNKNOWN_ENDPOINT = "none";

    // Endpoints de los que se registra log. No registrar ficheros estáticos.
    private final String[] endpointsTargetArr = new String[]{
            "offer", "user", "login", "signup", "home", "admin", "conversation"
    };

    @Autowired
    private LoggingService loggingService;

    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * En función del endpoint recibido y del código de estado HTTP,
     * devuelve un código y otro.
     * <p>
     * <code>
     * Listado de posibles códigos.
     *     <ul>
     *         <li>PET</li>
     *         <li>ALTA</li>
     *         <li>LOGIN-EX</li>
     *         <li>LOGIN-ERR</li>
     *         <li>LOGOUT</li>
     *     </ul>
     * </code>
     *
     * @param httpMethod Método HTTP de la petición entrante.
     * @param httpStatus Código de estado HTTP.
     * @param requestUrl URL del endpoint recibido.
     */
    private String getLogTypeByRequestUrl(final HttpServletRequest request, final String httpMethod,
                                          final int httpStatus, final String requestUrl) {
        boolean isSuccessResponse = httpStatus >= 200 && httpStatus < 400;

        if (!requestUrl.contains(LOGIN_ENDPOINT)) {
            // Peticiones del resto de controladores
            return LogType.PET.name();
        }

        return UNKNOWN_ENDPOINT;
    }

    /**
     * Formatea el mensaje de log.
     * <br />
     * Formato de salida:
     * <code>
     * [ TIPO_MENSAJE ] :: REMOTE_ADDR :: METODO_HTTP :: CODIGO_HTTP :: ENDPOINT :: LOCALE :: TIMESTAMP
     * </code>
     *
     * @param endPointName   Nombre de la URL del endpoint.
     * @param httpMethod     Método HTTP recibido.
     * @param logType        Tipo de log {@link LogType}
     * @param statusCode     Código de estado de respuesta HTTP.
     * @param responseLocale Localización de la respuesta HTTP.
     * @param remoteAddr     Ip del cliente que se conecta.
     * @param currentTime    Fecha y hora actual.
     * @return Cadena con el mensaje de log formateado.
     * @see #getLogTypeByRequestUrl(HttpServletRequest, String, int, String)
     * @see #formatLogMessage(String, String, String, int, Locale, String, Timestamp)
     */
    private String formatLogMessage(final String endPointName, final String httpMethod, final String logType,
                                    final int statusCode, final Locale responseLocale, final String remoteAddr,
                                    final Timestamp currentTime) {
        StringBuilder sB = new StringBuilder();

        sB.append("[ ").append(logType).append(" ]");
        // Separador de los elementos del mensaje, por defecto
        String LOG_MSG_DELIMITER = " :: ";
        sB.append(LOG_MSG_DELIMITER);
        sB.append(remoteAddr);
        sB.append(LOG_MSG_DELIMITER);
        sB.append(httpMethod);
        sB.append(LOG_MSG_DELIMITER);
        sB.append(statusCode);
        sB.append(LOG_MSG_DELIMITER);
        sB.append(endPointName);
        sB.append(LOG_MSG_DELIMITER);
        sB.append(responseLocale.getCountry()).append("_").append(responseLocale.getLanguage());
        sB.append(LOG_MSG_DELIMITER);
        sB.append(currentTime);

        return sB.toString();
    }

    /**
     * Extrae todos los parámetros de una peticion y devuelve una cadena con
     * sus pares clave-valor con un formato determinado.
     *
     * @param params Parametros recibidos de la request.
     */
    private String extractRequestParamsAsString(Map<String, String[]> params) {
        StringBuilder sB = new StringBuilder();

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            sB.append(param.getKey()).append(" : ")
                    .append(Arrays.stream(param.getValue()).reduce((res, p) -> {
                        return res += " ; " + p;
                    }));
            sB.append("\n");
        }

        return sB.toString();
    }

    @Override
    public void afterCompletion
            (HttpServletRequest request, HttpServletResponse response, Object
                    handler, Exception exception) throws Exception {

        // Nombre del usuario en sesion
        String usernameInSession = SecurityContextHolder.getContext().getAuthentication().getName();

        // URI del endpoint consultado
        String endPointName = request.getRequestURI();

        // Parametros de la peticion, si los hay
        String requestParams = extractRequestParamsAsString(request.getParameterMap());

        // Método HTTP recibido.
        String httpMethod = request.getMethod();

        // Código de estado de la respuesta HTTP
        int responseStatus = response.getStatus();

        // Cabecera HTTP indicando el tipo de log.
        String logType = getLogTypeByRequestUrl(request, httpMethod, responseStatus, endPointName);

        // Idioma de la respuesta HTTP
        Locale responseLocale = response.getLocale();

        // IP del equipo que realiza la peticion
        String remoteAddress = request.getRemoteAddr();

        // Fecha y hora actual
        Timestamp currentTime = Timestamp.from(Instant.now());

        // Registrar log de determinados endpoints. No registrar accesos a ficheros estáticos.
        String endpointNameExtracted = endPointName.split("/").length > 1
                ? endPointName.split("/")[1]
                : "/";

        // Filtrar solamente los endpoint definidos en endpointsTargetArr
        Optional<String> isValidEndpoint = Arrays.stream(endpointsTargetArr).filter(ep ->
                ep.contains(endpointNameExtracted)).findAny();

        if (isValidEndpoint.isPresent() && !logType.equalsIgnoreCase(UNKNOWN_ENDPOINT)) {
            CustomLog newLog = usernameInSession == null
                    ? new CustomLog(logType, httpMethod, responseStatus, remoteAddress, responseLocale, currentTime, endPointName, requestParams)
                    : new CustomLog(logType, httpMethod, responseStatus, remoteAddress, responseLocale, currentTime, endPointName, requestParams, usernameInSession);

            // Persistir log en base de datos
            loggingService.addNewLog(newLog);
        }
    }
}
