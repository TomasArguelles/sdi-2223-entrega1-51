package com.uniovi.sdi2223entrega1n.controllers;

import com.uniovi.sdi2223entrega1n.entities.CustomLog;
import com.uniovi.sdi2223entrega1n.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoggingController {

    @Autowired
    private LoggingService loggingService;

    /**
     * Listado de todos los logs registrados por la aplicación.
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
     */
    @RequestMapping("/admin/logs/filter")
    public String getAllLogsByLogType(@RequestParam(value = "logType") String logType, Model model) {
        if (logType.equalsIgnoreCase("TODO")) {
            List<CustomLog> logs = loggingService.findAll();
            model.addAttribute("logs", logs);
            return "admin/dashboard";
        }

        List<CustomLog> logs = loggingService.findAllByLogType(logType.toUpperCase());
        model.addAttribute("logs", logs);
        return "admin/dashboard";
    }

    /**
     * Eliminar todos los logs del sistema.
     */
    @RequestMapping(value = "/admin/logs/deleteAll", method = RequestMethod.GET)
    public String deleteAllLogs() {
        loggingService.deleteAllLogs();
        return "redirect:/admin/logs";
    }
}
