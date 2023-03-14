package com.uniovi.sdi2223entrega1n.controllers;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.services.OffersService;
import com.uniovi.sdi2223entrega1n.services.UsersService;
import com.uniovi.sdi2223entrega1n.validators.OfferFormValidation;
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

    /**
     * Añade una nueva oferta.
     *
     * @return
     */
    @RequestMapping(value = "/offer/add", method = RequestMethod.POST)
    public String addNewOffer(@ModelAttribute Offer offerToAdd, BindingResult result, Model model) {
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

        offersService.add(offerToAdd);
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
     *
     * @return
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
        return "offer/list";
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
        model.addAttribute("offersList",offers);
        return "offer/allList";
    }

    /**
     * Metodo para comprar una oferta
     * @param id id de la oferta
     * @param model
     * @param principal usuario
     * @return la vista
     */
    @RequestMapping(value = "/offer/{id}/buyOffer")
    public String buyOffer(@PathVariable Long id,Model model,Principal principal){
        //Obtengo el dinero que tiene la cartera del usuario
        String userEmail = principal.getName();
        User user = usersService.getUserByEmail(userEmail);
        Double wallet = user.getWallet();
        //Obtengo el precio de la oferta
        Offer offer = offersService.getOffer(id);
        Double price = offer.getPrice();
        //Comprobar si el dinero del wallet es superior al precio
        if(wallet>price) {
            offersService.setOfferSold(id);
            user.setWallet((user.getWallet())-price);
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
        model.addAttribute("offersList",offers);
        return "offer/allList :: tableOffer";
    }



}
