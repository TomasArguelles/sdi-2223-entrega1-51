package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.entities.CustomLog;
import com.uniovi.sdi2223entrega1n.repositories.LoggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoggingService {

    @Autowired
    private LoggingRepository loggingRepository;

    /**
     * Añadir un nuevo log al sistema.
     *
     * @param newLog
     */
    public void addNewLog(final CustomLog newLog) {
        loggingRepository.save(newLog);
    }

    /**
     * Listado de todos los logs de la aplicación.
     *
     * @return
     */
    public List<CustomLog> findAll() {
        // TODO: Paginacion.
        List<CustomLog> logs = new ArrayList<>();
        loggingRepository.findAll().forEach(logs::add);
        return logs;
    }

    /**
     * Listado de todos los logs de un tipo concreto.
     *
     * @param logTypeToFilter Tipo de log a filtrar.
     * @return
     */
    public List<CustomLog> findAllByLogType(final String logTypeToFilter) {
        return loggingRepository.findAllByLogType(logTypeToFilter);
    }

    /**
     * Elimina todos los logs del sistema.
     */
    public void deleteAllLogs() {
        loggingRepository.deleteAll();
    }
}
