package com.uniovi.sdi2223entrega1n.pageobjects;

import com.uniovi.sdi2223entrega1n.util.SeleniumUtils;
import org.openqa.selenium.WebDriver;

public class PO_AdminView extends PO_NavView {

    public static final String ADMIN_EMAIL = "admin@email.com";

    public static final String ADMIN_DASHBOARD_ENDPOINT = "/admin/logs";
    public static final String ADMIN_DELETE_ALL_LOGS_ENDPOINT = "/admin/logs/deleteAll";

    /**
     * Eliminación de todos los logs del sistema.
     *
     * @param driver WebDriver
     */
    public static void deleteAllLogs(WebDriver driver) {
        // Iniciar sesión como administrador
        SeleniumUtils.signInIntoAccount(driver, "ADMIN", ADMIN_EMAIL);

        // Ir a la vista de logs
        PO_NavView.clickOption(driver, ADMIN_DASHBOARD_ENDPOINT, "id", "viewLogsMenuItem");

        // Hacer click en el boton de eliminar logs
        PO_NavView.clickOption(driver, ADMIN_DELETE_ALL_LOGS_ENDPOINT, "id", "deleteAllLogsButton");

        PO_NavView.clickLogout(driver);
    }
}
