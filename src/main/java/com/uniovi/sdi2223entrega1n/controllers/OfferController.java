package com.uniovi.sdi2223entrega1n.controllers;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.services.OffersService;
import com.uniovi.sdi2223entrega1n.services.UsersService;
import com.uniovi.sdi2223entrega1n.validators.OfferFormValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@Controller
public class OfferController {

    @Autowired
    private OffersService offersService;

    @Autowired
    private OfferFormValidation offerValidationForm;

    @Autowired
    private UsersService usersService;


    private boolean invalidBuy = false;

    private Logger logger = LoggerFactory.getLogger(OfferController.class);


    /**
     * Añade una nueva oferta.
     *
     * @return
     */
    @RequestMapping(value = "/offer/add", method = RequestMethod.POST)
    public String addNewOffer(@ModelAttribute Offer offerToAdd, BindingResult result, Model model, Principal principal) {
        // Validacion de campos
        offerValidationForm.validate(offerToAdd, result);

        if (result.hasErrors()) {
            model.addAttribute("fields", offerToAdd);
            return "offer/add";
        }

        // Añadir la nueva oferta
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User seller = usersService.getUserByEmail(email);
        // El vendedor es el usuario en sesión
        offerToAdd.setSeller(seller);
        // Fecha a dia de hoy
        offerToAdd.setDateUpload(Instant.now());

        // Compruebo el valor del checkbox por si el usuario quiere destacar la nueva oferta
        if (offerToAdd.getFeatured()) {
            // Obtengo el dinero que tiene la cartera del usuario
            String userEmail = principal.getName();
            User user = usersService.getUserByEmail(userEmail);
            Double wallet = user.getWallet();

            // Compruebo si el dinero del wallet es superior al precio de destacar una oferta
            if (wallet >= 20.0) {
                invalidBuy = false;
                offerToAdd.setFeatured(true); // destaco la oferta
                usersService.decrementMoney(user, 20.0); // actualizo el dinero del usuario
            }
            else {
                invalidBuy = true;
                model.addAttribute("buyError", invalidBuy);
            }
        }

        offersService.add(offerToAdd);

        logger.info("Oferta añadida al sistema correctamente");

        return "redirect:/offer/list";
    }

    /**
     * Redirecciona a la vista de añadir una nueva oferta.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/offer/add", method = RequestMethod.GET)
    public String getAddNewOffer(Model model) {
        model.addAttribute("offer", new Offer());
        return "offer/add";
    }

    /**
     * Redirecciona a la vista de listado de oferta disponibles.
     * Además, obtiene todas las ofertas destacadas (de todos los usuarios)
     * y las redirecciona a la vista de listado de ofertas disponibles
     */
    @RequestMapping(value = "/offer/list", method = RequestMethod.GET)
    public String getAllOffersList(Model model, Principal principal) {
        String userEmail = principal.getName();

        if (userEmail == null || userEmail == "" || userEmail == "anonymousUser") {
            return "home";
        }

        // Si el usuario está autenticado, obtener sus datos
        List<Offer> offers = offersService.findAllOffersFromUserByEmail(userEmail);

        // Enviar el listado de ofertas a la vista
        model.addAttribute("offerList", offers);

        // Obtenemos la lista de ofertas destacadas
        List<Offer> featuredOffers = offersService.getAllFeatured();

        // Enviar el listado de ofertas destacadas a la vista
        model.addAttribute("offerFeaturedList", featuredOffers);

        return "offer/list";
    }

    /**
     * Dar de baja una oferta por su identificador.
     *
     * @param id Identificador de la oferta.
     * @return
     */
    @RequestMapping(value = "/offer/delete/{id}", method = RequestMethod.GET)
    public String deleteOfferById(@PathVariable final Long id) {
        offersService.deleteOfferById(id);
        return "redirect:/offer/list";
    }

    /**
     * Metodo que redirecciona a la vista de TODAS las ofertas en el sistema
     * @param model
     * @param principal usuario
     * @param searchText texto a buscar
     * @return la vista
     */
    @RequestMapping (value="/offer/allList")
    public String getOffersToBuy(Model model, Principal principal,@RequestParam(value = "", required = false)String searchText){
        String userEmail = principal.getName();
        List<Offer>offers;
        //Si esta vacio el buscador devolvemos todas, sino no
        if(searchText==null || searchText.isEmpty()){
            offers = offersService.getAllOffersToBuy(userEmail);
        }else{
            offers = offersService.searchOffersByNameAndUser(searchText,userEmail);
        }
        User user = usersService.getUserByEmail(userEmail);
        model.addAttribute("buyer",user);
        model.addAttribute("offersList",offers);
        model.addAttribute("buyError",invalidBuy);
        return "offer/allList";
    }

    /**
     * Metodo para comprar una oferta
     * @param id id de la oferta
     * @param principal usuario
     * @return la vista
     */
    @RequestMapping(value = "/offer/{id}/buyOffer")
    public String buyOffer(@PathVariable Long id,Principal principal){
        //Obtengo el dinero que tiene la cartera del usuario
        String userEmail = principal.getName();
        User user = usersService.getUserByEmail(userEmail);
        Double wallet = user.getWallet();
        //Obtengo el precio de la oferta
        Offer offer = offersService.getOffer(id);
        Double price = offer.getPrice();
        invalidBuy=true;
        //Comprobar si el dinero del wallet es superior al precio
        if(wallet>=price) {
            offersService.setOfferSold(id,user);
            usersService.decrementMoney(user,price);
            invalidBuy=false;
        }
        return "redirect:/offer/allList";
    }

    /**
     * Metodo que actualiza la vista
     * @param model
     * @param principal usuario registrado
     * @param searchText texto a buscar
     * @return la vista
     */
    @RequestMapping(value = "/offer/list/update")
    public String updateList(Model model, Principal principal,@RequestParam(value = "", required = false)String searchText){
        String userEmail = principal.getName();
        List<Offer>offers;
        //Si esta vacio el buscador devolvemos todas, sino no
        if(searchText==null || searchText.isEmpty()){
            offers = offersService.getAllOffersToBuy(userEmail);

        }else{
            offers = offersService.searchOffersByNameAndUser(searchText,userEmail);
        }
        User user = usersService.getUserByEmail(userEmail);
        model.addAttribute("buyer",user);
        model.addAttribute("offersList",offers);
        model.addAttribute("buyError",invalidBuy);
        return "offer/allList :: tableBuy";
    }

    /**
     * Obtiene todas las ofertas compradas por el usuario autenticado
     *
     * @param model, modelo al que pasamos la lista de ofertas
     * @param principal, nos permite obtener al usuario autenticado
     * @return la lista de ofertas
     */
    @RequestMapping("/offer/boughtList")
    public String getAllByBuyer(Model model, Principal principal) {
        // Obtenemos el email del usuario autenticado
        String email = principal.getName(); // email es el name de la autenticación
        // Obtenemos la lista de ofertas compradas por el usuario
        List<Offer> offers = offersService.getAllByBuyer(email);
        // Se la pasamos al modelo
        model.addAttribute("offersList", offers);
        return "offer/boughtList";
    }

    /**
     * Destacar una oferta por su identificador
     * Cuesta 20€, se debe comprobar por tanto que el usuario tenga ese dinero al menos
     *
     * @param id, identificador de la oferta a destacar
     * @param principal, nos permite obtener al usuario autenticado
     * @param model, permite enviar a la vista un atributo
     * @return la redirección a la vista de listado de ofertas
     */
    @RequestMapping("/offer/featured/{id}")
    public String featuredOfferById(@PathVariable final Long id, Principal principal, Model model) {
        // Obtengo el dinero que tiene la cartera del usuario
        String userEmail = principal.getName();
        User user = usersService.getUserByEmail(userEmail);
        Double wallet = user.getWallet();

        // Compruebo si el dinero del wallet es superior al precio de destacar una oferta (20€)
        if (wallet >= 20.0) {
            invalidBuy = false;
            offersService.featuredOfferById(id); // destaco la oferta
            usersService.decrementMoney(user, 20.0); // actualizo el dinero del usuario

            // ACTUALIZACIÓN:
            // Enviar el listado de ofertas (propias del usuario) actualizado a la vista
            List<Offer> offers = offersService.findAllOffersFromUserByEmail(userEmail);
            model.addAttribute("offerList", offers);
            // Enviar el listado de ofertas destacadas actualizado a la vista
            List<Offer> featuredOffers = offersService.getAllFeatured();
            model.addAttribute("offerFeaturedList", featuredOffers);
        }
        else {
            invalidBuy = true;
            model.addAttribute("buyError", invalidBuy);
        }

        return "redirect:/offer/list";
    }

}
