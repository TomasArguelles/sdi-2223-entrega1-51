package com.uniovi.sdi2223entrega1n;

import com.uniovi.sdi2223entrega1n.pageobjects.PO_NavView;
import com.uniovi.sdi2223entrega1n.pageobjects.PO_OfferView;
import com.uniovi.sdi2223entrega1n.pageobjects.PO_Properties;
import com.uniovi.sdi2223entrega1n.pageobjects.PO_View;
import com.uniovi.sdi2223entrega1n.repositories.OffersRepository;
import com.uniovi.sdi2223entrega1n.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2223Entrega1NApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "C:\\Users\\Tomás\\Downloads\\OneDrive_1_7-3-2023\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "C:\\Users\\UO253628\\Downloads\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "C:\\Users\\kikoc\\Dev\\sellenium\\geckodriver-v0.30.0-win64.exe";
    //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";

    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";

    @Autowired
    private OffersRepository offersRepository;

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp() {
        driver.navigate().to(URL);
    }

    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }

    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {
    }

    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        // Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    //    [Prueba1] Registro de Usuario con datos válidos.
//    @Test
//    @Order(1)
//    void PR01() {
//        //Nos movemos al formulario de registro
//        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos VALIDOS
//        PO_SignUpView.fillForm(driver, "JoseFo@gmail.com", "Josefo", "Perez", "77777", "77777");
//        //Comprobamos que hemos ido a la pagina de home, confirmando que el registro se ha completado con exito
//        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
//    }
//
//    //    [Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío, apellidos vacíos).
//    @Test
//    @Order(2)
//    void PR02() {
//        //Nos movemos al formulario de registro
//        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos INVALIDOS
//        PO_SignUpView.fillForm(driver, "", "", "", "77777", "77777");
//        //Comprobamos que seguimos en la pantalla de registro
//        PO_SignUpView.checkSignUpPage(driver, PO_Properties.getSPANISH());
//    }
//
//    //    [Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña inválida).
//    @Test
//    @Order(3)
//    void PR03() {
//        //Nos movemos al formulario de registro
//        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos INVALIDOS
//        PO_SignUpView.fillForm(driver, "JoseFo@gmail.com", "Josefo", "Perez", "77777", "773777");
//        //Comprobamos que seguimos en la pantalla de registro
//        PO_SignUpView.checkSignUpPage(driver, PO_Properties.getSPANISH());
//    }
//
//    //    [Prueba4] Registro de Usuario con datos inválidos (email existente).
//    @Test
//    @Order(4)
//    void PR04() {
//        //Nos movemos al formulario de registro
//        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos VALIDOS
//        PO_SignUpView.fillForm(driver, "JoseFo1@gmail.com", "Josefo", "Perez", "77777", "77777");
//        //Comprobamos que seguimos en la pantalla de registro
//        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
//
//        //Nos movemos al formulario de registro
//        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//        //Cumplimentamos el registro con datos INVALIDOS
//        PO_SignUpView.fillForm(driver, "JoseFo1@gmail.com", "Josefo", "Perez", "77777", "77777");
//        //Comprobamos que seguimos en la pantalla de registro
//        PO_SignUpView.checkSignUpPage(driver, PO_Properties.getSPANISH());
//    }
//
    // [Prueba 15]. Añadir nueva oferta con datos válidos.
    @Test
    @Order(5)
    public void PR05() {
        // Iniciar sesión como usuario standard
        SeleniumUtils.signInIntoAccount(driver, "STANDARD");

        String newOfferText = "Coche marca Renault";

        // Acceder a la vista de añadir una nueva oferta
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");

        // Rellenar campos del formulario con valores válidos.
        PO_OfferView.fillForm(driver, newOfferText, "Coche de los años 90", 2000.50);

        // Comprobar que se muestra en el listado de ofertas
        PO_View.checkElementBy(driver, "text", newOfferText);
    }

    // [Prueba 16]. Añadir una nueva oferta con datos inválidos -> precio negativo.
    @Test
    @Order(6)
    public void PR06() {
        // Iniciar sesión como usuario standard
        SeleniumUtils.signInIntoAccount(driver, "STANDARD");

        // Acceder a la vista de añadir una nueva oferta
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "addOfferMenu");

        // Rellenar campos del formulario con valores inválidos.
        PO_OfferView.fillForm(driver, "Coche marca Renault", "Coche de los años 90", -1.0);

        // Comprobar que se muestra el error en el formulario.
        PO_OfferView.checkErrorMessage(driver, PO_Properties.getSPANISH(), "error.offer.price.range");
    }

    // [Prueba 17]. Listado de ofertas propias de un usuario. Comprobar que se muestran todas las ofertas
    // publicadas por dicho usuario.
    @Test
    @Order(7)
    public void PR07() {
        // Iniciar sesión como usuario standard
        SeleniumUtils.signInIntoAccount(driver, "STANDARD");

        // Acceder a la vista de listado de ofertas
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listOfferMenu");

        // Comprobar número elementos de tabla con número de elementos BBDD
        int offerCountFromUserOnDatabase = offersRepository.findAllBySellerEmail("usuario1@email.com").size();

        // Obtener número de filas de la tabla de la vista del listado de ofertas
        int rowCount = SeleniumUtils.countTableRows(driver, "//table[@class='table table-hover']/tbody/tr");

        // Verificar que el número de registros mostrados es correcto
        Assertions.assertEquals(offerCountFromUserOnDatabase, rowCount);
    }
}
