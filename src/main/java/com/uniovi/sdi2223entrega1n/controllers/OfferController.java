package com.uniovi.sdi2223entrega1n.controllers;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.services.OffersService;
import com.uniovi.sdi2223entrega1n.services.UsersService;
import com.uniovi.sdi2223entrega1n.validators.OfferFormValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
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
}
