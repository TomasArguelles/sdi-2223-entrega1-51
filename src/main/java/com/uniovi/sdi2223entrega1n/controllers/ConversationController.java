package com.uniovi.sdi2223entrega1n.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.uniovi.sdi2223entrega1n.entities.Conversation;
import com.uniovi.sdi2223entrega1n.entities.Message;
import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.services.ConversationsService;
import com.uniovi.sdi2223entrega1n.services.OffersService;
import com.uniovi.sdi2223entrega1n.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ConversationController {

    @Autowired
    private ConversationsService conversationsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private OffersService offersService;

    @RequestMapping(value = "/conversation/list")
    public String getAllConversationsList(Model model, Principal principal) {
        String userEmail = principal.getName();
        User u=usersService.getUserByEmail(userEmail);
        // Enviar el listado de conversaciones a la vista
        model.addAttribute("conversationsList", conversationsService.findAllConversationsFromUserByEmail(u));
        return "conversation/list";
    }


    @RequestMapping("/conversation/redirectToConversation/{offerId}")
    public String getRedirectToConversation(@PathVariable Long offerId, Principal principal, Model model) {
        String userEmail = principal.getName();
        User u = usersService.getUserByEmail(userEmail);

        Offer o=offersService.findById(offerId);
        //System.out.println("O  "+o.getId());
        Conversation conv = conversationsService.findByOfferId(offerId);
        Conversation conversation;
        if (conv == null) {

            conversation = new Conversation();
            conversation.setOffer(o);
            conversation.setBuyer(u);
            conversationsService.save(conversation);
            System.out.println("No había conver, se creó "+conversation);
            return "redirect:/conversation/conversation/"+conversation.getId();
        }else{
            System.out.println("La conver es "+conv);
            return "redirect:/conversation/conversation/"+conv.getId();
        }


    }
    @RequestMapping("conversation/conversation/{conversationId}")
    public String getConversation(@PathVariable Long conversationId, Model model) {

        model.addAttribute("conversation", conversationsService.getConversation(conversationId));
        System.out.println("Id: "+conversationId);
        System.out.println("Conv: "+conversationsService.getConversation(conversationId));
        System.out.println("Msgs size: "+conversationsService.getConversation(conversationId).getMsgs().size());
        return "conversation/conversation";
    }

    @PostMapping("conversation/conversation/{conversationId}/addMessage")
    public String addMessage(@PathVariable Long conversationId,
                             @RequestParam String text,
                             Principal principal) {
        User sender = usersService.getUserByEmail(principal.getName());
        Conversation conversation = conversationsService.getConversation(conversationId);
        if (conversation == null) {
            // handle conversation not found error
        }
        Message message = new Message();
        message.setText(text);
        message.setDate(Instant.now());
        message.setSender(sender);
        message.setConversation(conversation);
        conversation.addMessage(message);
        conversationsService.save(conversation);
        return "redirect:/conversation/conversation/" + conversationId;
    }



}







