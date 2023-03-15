package com.uniovi.sdi2223entrega1n.controllers;

import com.uniovi.sdi2223entrega1n.entities.CustomLog;
import com.uniovi.sdi2223entrega1n.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class LoggingController {

    @Autowired
    private LoggingService loggingService;

    /**
     * Listado de todos los logs registrados por la aplicación.
     *
     * @return
     */
    @RequestMapping("/admin/logs")
    public String getAllLogs(Model model) {
        List<CustomLog> logs = loggingService.findAll();
        model.addAttribute("logs", logs);
        return "admin/dashboard";
    }

    /**
     * Listado de todos los logs de un tipo determinado.
     *
     * @param logType Tipo de log a filtrar.
     * @return
     */
    @RequestMapping("/admin/logs/{logType}")
    public String getAllLogsByLogType(@PathVariable String logType, Model model) {
        List<CustomLog> logs = loggingService.findAllByLogType(logType);
        model.addAttribute("logs", logs);
        return "admin/dashboard :: tableLogs";
    }

    /**
     * Eliminar todos los logs del sistema.
     *
     * @return
     */
    @RequestMapping(value = "/admin/logs/deleteAll", method = RequestMethod.GET)
    public String deleteAllLogs() {
        loggingService.deleteAllLogs();
        return "redirect:/admin/logs";
    }
}
