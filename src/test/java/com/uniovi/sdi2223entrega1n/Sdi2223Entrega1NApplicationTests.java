package com.uniovi.sdi2223entrega1n;

import com.uniovi.sdi2223entrega1n.repositories.OffersRepository;
import antlr.ASTNULLType;
import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.pageobjects.*;
import com.uniovi.sdi2223entrega1n.services.UsersService;
import com.uniovi.sdi2223entrega1n.util.SeleniumUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2223Entrega1NApplicationTests {

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "C:\\Users\\Tomás\\Downloads\\OneDrive_1_7-3-2023\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "C:\\Users\\UO253628\\Downloads\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //static String Geckodriver = "C:\\Users\\kikoc\\Dev\\sellenium\\geckodriver-v0.30.0-win64.exe";
    //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
    //Ruta Manu (cambiar)
    static String Geckodriver = "C:\\Users\\Usuario\\Desktop\\SDI\\sesion5\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private UsersService userService;
    
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
    @Test
    @Order(1)
    void PR01() {
        //Nos movemos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos VALIDOS
        PO_SignUpView.fillForm(driver, "JoseFo@gmail.com", "Josefo", "Perez", "77777", "77777");
        //Comprobamos que hemos ido a la pagina de home, confirmando que el registro se ha completado con exito
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }

    //    [Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío, apellidos vacíos).
    @Test
    @Order(2)
    void PR02() {
        //Nos movemos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos INVALIDOS
        PO_SignUpView.fillForm(driver, "", "", "", "77777", "77777");
        //Comprobamos que seguimos en la pantalla de registro
        PO_SignUpView.checkSignUpPage(driver, PO_Properties.getSPANISH());
    }

    //    [Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña inválida).
    @Test
    @Order(3)
    void PR03() {
        //Nos movemos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos INVALIDOS
        PO_SignUpView.fillForm(driver, "JoseFo@gmail.com", "Josefo", "Perez", "77777", "773777");
        //Comprobamos que seguimos en la pantalla de registro
        PO_SignUpView.checkSignUpPage(driver, PO_Properties.getSPANISH());
    }

    //    [Prueba4] Registro de Usuario con datos inválidos (email existente).
    @Test
    @Order(4)
    void PR04() {
        //Nos movemos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos VALIDOS
        PO_SignUpView.fillForm(driver, "JoseFo1@gmail.com", "Josefo", "Perez", "77777", "77777");
        //Comprobamos que seguimos en la pantalla de registro
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());

        //Nos movemos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos INVALIDOS
        PO_SignUpView.fillForm(driver, "JoseFo1@gmail.com", "Josefo", "Perez", "77777", "77777");
        //Comprobamos que seguimos en la pantalla de registro
        PO_SignUpView.checkSignUpPage(driver, PO_Properties.getSPANISH());
    }
    //[Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el
    //sistema.
    @Test
    @Order(11)
    void PR011() {
        //Nos movemos al formulario de inicio de sesion
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos VALIDOS
        PO_LoginView.fillForm(driver, "admin1@email.com", "123456");
        //Comprobamos que seguimos en la pantalla de registro
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
        //Accedemos a la lista de users
        driver.get("http://localhost:8090/user/list");


        //Sacamos la lista de usuarios q hay
        List<WebElement> usersList = PO_UserListView.getUsersList(driver);
        //Sacamos la lista de usaurios del sistema
        List<User>usersSystem=userService.getUsers();
        Assertions.assertEquals(usersSystem.size(),usersSystem.size());
        //Comprobamos uno a uno
        PO_UserListView.compareOneByOneTwoUsersLists(driver, usersList, usersSystem);



    }

    //[Prueba12] Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar que la lista se actualiza
    //y dicho usuario desaparece.
    @Test
    @Order(12)
    void PR012() {
        //Nos movemos al formulario de inicio de sesion
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos VALIDOS
        PO_LoginView.fillForm(driver, "admin1@email.com", "123456");
        //Comprobamos que seguimos en la pantalla de registro
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
        //Accedemos a la lista de users
        driver.get("http://localhost:8090/user/list");

        //Sacamos la lista de usuarios q hay
        List<WebElement> usersList = PO_UserListView.getUsersList(driver);
        //guardamos tamaño para comporbar
        int s1=usersList.size();
        //Primer usuario y marcaje de su checkbox
        WebElement firstUser= usersList.get(0);

        PO_UserListView.markCheckBoxUser(driver, firstUser);
        //Borramos dandole al boton
        PO_UserListView.clickDeleteButton(driver);
        //Actualizamos la lista
        usersList = PO_UserListView.getUsersList(driver);
        //Guardamos segundo tamaño y vemos q no es el mismo, comprobamos que decrementó en 1
        int s2=usersList.size();
        Assertions.assertNotEquals(s1,s2);
        Assertions.assertEquals(s1,s2+1);





    }

//[Prueba13] Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar que la lista se actualiza
//y dicho usuario desaparece.
    @Test
    @Order(13)
    void PR013() {
        //Nos movemos al formulario de inicio de sesion
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos VALIDOS
        PO_LoginView.fillForm(driver, "admin1@email.com", "123456");
        //Comprobamos que seguimos en la pantalla de registro
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
        //Accedemos a la lista de users
        driver.get("http://localhost:8090/user/list");

        //Sacamos la lista de usuarios q hay
        List<WebElement> usersList = PO_UserListView.getUsersList(driver);
        //guardamos tamaño para comporbar
        int s1=usersList.size();
        //Ultimo usuario y marcaje de su checkbox
        WebElement lastUser= usersList.get(usersList.size()-1);
        PO_UserListView.markCheckBoxUser(driver, lastUser);
        //Borramos dandole al boton
        PO_UserListView.clickDeleteButton(driver);
        //Actualizamos la lista
        usersList = PO_UserListView.getUsersList(driver);
        //Guardamos segundo tamaño y vemos q no es el mismo, comprobamos que decrementó en 1
        int s2=usersList.size();
        Assertions.assertNotEquals(s1,s2);
        Assertions.assertEquals(s1,s2+1);





    }

   //[Prueba14] Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se actualiza y dichos
    //usuarios desaparecen.
    @Test
    @Order(14)
    void PR014() {
        //Nos movemos al formulario de inicio de sesion
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Cumplimentamos el registro con datos VALIDOS
        PO_LoginView.fillForm(driver, "admin1@email.com", "123456");
        //Comprobamos que seguimos en la pantalla de registro
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
        //Accedemos a la lista de users
        driver.get("http://localhost:8090/user/list");

        //Sacamos la lista de usuarios q hay
        List<WebElement> usersList = PO_UserListView.getUsersList(driver);
        //guardamos tamaño para comporbar
        int s1=usersList.size();
        //Sacamos los tres primeros usuarios y marcamos de sus checkboxes
        WebElement u1= usersList.get(0);
        WebElement u2= usersList.get(1);
        WebElement u3= usersList.get(2);
        PO_UserListView.markCheckBoxUser(driver, u1);
        PO_UserListView.markCheckBoxUser(driver, u2);
        PO_UserListView.markCheckBoxUser(driver, u3);
        //Borramos dandole al boton
        PO_UserListView.clickDeleteButton(driver);
        //Actualizamos la lista
        usersList = PO_UserListView.getUsersList(driver);
        //Guardamos segundo tamaño y vemos q no es el mismo, comprobamos que decrementó en 1
        int s2=usersList.size();
        Assertions.assertNotEquals(s1,s2);
        Assertions.assertEquals(s1,s2+3);

    }


    // [Prueba 15]. Añadir nueva oferta con datos válidos.
    @Test
    @Order(15)
    public void PR015() {
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
    @Order(16)
    public void PR016() {
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
    @Order(17)
    public void PR017() {
        // Iniciar sesión como usuario standard
        SeleniumUtils.signInIntoAccount(driver, "STANDARD");

        // Acceder a la vista de listado de ofertas
        PO_NavView.selectDropdownById(driver, "gestionOfertasMenu", "gestionOfertasDropdown", "listOfferMenu");

        // Comprobar número elementos de tabla con número de elementos BBDD
        int offerCountFromUserOnDatabase = offersRepository.findAllBySeller("usuario1@email.com").size();

        // Obtener número de filas de la tabla de la vista del listado de ofertas
        int rowCount = SeleniumUtils.countTableRows(driver, "//table[@class='table table-hover']/tbody/tr");

        // Verificar que el número de registros mostrados es correcto
        Assertions.assertEquals(offerCountFromUserOnDatabase, rowCount);
    }
}
