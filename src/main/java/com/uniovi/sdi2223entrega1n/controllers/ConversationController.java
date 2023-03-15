package com.uniovi.sdi2223entrega1n.controllers;

import com.uniovi.sdi2223entrega1n.entities.Conversation;
import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.services.ConversationsService;
import com.uniovi.sdi2223entrega1n.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@Controller
public class ConversationController {

    @Autowired
    private ConversationsService conversationsService;

    @RequestMapping(value = "/conversation/list", method = RequestMethod.GET)
    public String getAllConversationsList(Model model, Principal principal) {
        String userEmail = principal.getName();

        if (userEmail == null || userEmail == "" || userEmail == "anonymousUser") {
            return "home";
        }

        // Si el usuario está autenticado, obtener sus datos
        List<Conversation> convs = conversationsService.findAllConversationsFromUserByEmail(userEmail);

        // Enviar el listado de conversaciones a la vista
        model.addAttribute("conversationsList", convs);
        return "/conversation/list";
    }

}







