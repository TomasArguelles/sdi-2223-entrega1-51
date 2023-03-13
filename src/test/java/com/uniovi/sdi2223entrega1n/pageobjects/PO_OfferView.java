package com.uniovi.sdi2223entrega1n.pageobjects;

import com.uniovi.sdi2223entrega1n.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_OfferView extends PO_NavView {
    static public void fillForm(WebDriver driver, String titleP, String descriptionP, Double priceP) {

        // Campo titulo de la oferta
        WebElement offerTitle = driver.findElement(By.name("title"));
        offerTitle.click();
        offerTitle.clear();
        offerTitle.sendKeys(titleP);

        // Campo descripcion de la oferta
        WebElement description = driver.findElement(By.name("description"));
        description.click();
        description.clear();
        description.sendKeys(descriptionP);

        // Campo precio (En euros) de la oferta
        WebElement price = driver.findElement(By.name("price"));
        price.click();
        price.clear();
        price.sendKeys(priceP.toString());

        // Pulsar el boton de Añadir.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    /**
     * Comprueba que se muestra el mensaje de error con clave <code>resourceKey</code>.
     *
     * @param driver
     * @param language
     * @param resourceKey Clave en el fichero de idiomas.
     */
    static public void checkErrorMessage(WebDriver driver, int language, String resourceKey) {
        // Esperamos a que se cargue el saludo de bienvenida en Español
        SeleniumUtils.waitLoadElementsBy(driver, "text", p.getString(resourceKey, language),
                getTimeout());
    }
}
