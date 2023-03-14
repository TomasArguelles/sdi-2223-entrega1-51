package com.uniovi.sdi2223entrega1n.pageobjects;

import com.uniovi.sdi2223entrega1n.util.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

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
     * Eliminar una oferta de la lista.
     *
     * @param driver
     * @param position Número de fila de la tabla donde se encuentra el botón borrar oferta específico.
     */
    static public void clickDeleteButton(WebDriver driver, int position) {
        List<WebElement> markList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());

        // Boton de eliminar de la primera fila
        WebElement btnDeleteOffer = markList.get(position).findElement(By.className("btnBorrarOferta"));

        btnDeleteOffer.click();


    }

    /**
     * Añadir una oferta para probar.
     *
     * @param driver
     * @param offerTitle
     */
    static public void addSampleOffer(WebDriver driver, String offerTitle) {
        // Acceder a la vista de añadir una nueva oferta
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");

        // Rellenar campos del formulario con valores inválidos.
        PO_OfferView.fillForm(driver, offerTitle, "Coche de los años 90", 100.0);
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

    /**
     * Comprueba que la oferta no está en el listado de ofertas.
     *
     * @param driver
     * @param textToSearch
     */
    static public void checkOfferNotExistsOnPage(WebDriver driver, String textToSearch) {
        SeleniumUtils.textIsNotPresentOnPage(driver, textToSearch);
    }
}
