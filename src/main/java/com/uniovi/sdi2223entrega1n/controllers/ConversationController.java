package com.uniovi.sdi2223entrega1n.controllers;

import com.uniovi.sdi2223entrega1n.entities.Conversation;
import com.uniovi.sdi2223entrega1n.entities.Message;
import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.services.ConversationsService;
import com.uniovi.sdi2223entrega1n.services.OffersService;
import com.uniovi.sdi2223entrega1n.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;

@Controller
public class ConversationController {

    @Autowired
    private ConversationsService conversationsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private OffersService offersService;

    @RequestMapping(value = "/conversation/mylist")
    public String getMyConversationsList(Model model, Principal principal) {
        String userEmail = principal.getName();
        User u=usersService.getUserByEmail(userEmail);
        model.addAttribute("conversationsList", conversationsService.findAllConversationsFromUserByEmail(u));
        return "/conversation/mylist";
    }
    @RequestMapping(value = "/conversation/list")
    public String getConversationsList(Model model, Principal principal) {
        String userEmail = principal.getName();
        User u=usersService.getUserByEmail(userEmail);
        model.addAttribute("conversationsList", conversationsService.findAllConversationsByBuyer(u));
        return "/conversation/list";
    }
    @RequestMapping(value = "/conversation/allList")
    public String getAllConversationsList(Model model, Principal principal) {
        String userEmail = principal.getName();
        User u=usersService.getUserByEmail(userEmail);
        model.addAttribute("conversationsList", conversationsService.findAllConversationsByBuyer(u));
        model.addAttribute("myconversationsList", conversationsService.findAllConversationsFromUserByEmail(u));
        return "/conversation/allList";
    }


    @RequestMapping("/conversation/redirectToConversation/{offerId}")
    public String getRedirectToConversation(@PathVariable Long offerId, Principal principal, Model model) {
        String userEmail = principal.getName();
        User u = usersService.getUserByEmail(userEmail);

        Offer o=offersService.findById(offerId);
        //System.out.println("O  "+o.getId());
        Conversation conv = conversationsService.getConversationByOfferIdAndBuyer(offerId,u);

        Conversation conversation;
        if (conv == null) {

            conversation = new Conversation();
            conversation.setOffer(o);
            conversation.setBuyer(u);
            conversationsService.save(conversation);
            System.out.println("No había conver, se creó entre "+o.getSeller().getName()+" y "+conversation.getBuyer().getName());
            return "redirect:/conversation/conversation/"+conversation.getId();
        }else{
            System.out.println("La conver es "+conv);
            return "redirect:/conversation/conversation/"+conv.getId();
        }


    }


    @RequestMapping("/conversation/redirectToConversationMyOffers/{offerId}")
    public String getRedirectToConversationMyOffers(@PathVariable Long offerId, Principal principal, Model model) {
        String userEmail = principal.getName();
        User u = usersService.getUserByEmail(userEmail);

        Offer o=offersService.findById(offerId);
        //System.out.println("O  "+o.getId());
        Conversation conv = conversationsService.getConversationByOfferIdAndSeller(offerId,u);
        Conversation conversation;
        if (conv == null) {

           System.out.println("No hay conversación");
           return "/offer/list";
        }else{
            System.out.println("La conver es "+conv);
            return "redirect:/conversation/myconversation/"+conv.getId();
        }


    }
    @RequestMapping("conversation/conversation/{conversationId}")
    public String getConversation(@PathVariable Long conversationId, Model model) {

        model.addAttribute("conversation", conversationsService.getConversation(conversationId));

        return "conversation/conversation";
    }

    @RequestMapping("conversation/myconversation/{conversationId}")
    public String getMyConversation(@PathVariable Long conversationId, Model model) {

        model.addAttribute("conversation", conversationsService.getConversation(conversationId));


        return "conversation/myconversation";
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

    @RequestMapping(value = "/conversation/mydelete/{id}", method = RequestMethod.GET)
    public String deleteMyConversationById(@PathVariable final Long id, Principal principal) {
        User user = usersService.getUserByEmail(principal.getName());
        Conversation conversation = conversationsService.getConversation(id);

        if (conversation.getBuyer().equals(user) || conversation.getOffer().getSeller().equals(user)) {
            conversationsService.deleteConversationById(id);
            return "redirect:/conversation/mylist";

        }else{
            System.out.println("Error");
            return null;
        }
    }
    @RequestMapping(value = "/conversation/delete/{id}", method = RequestMethod.GET)
    public String deleteConversationById(@PathVariable final Long id, Principal principal) {
        User user = usersService.getUserByEmail(principal.getName());
        Conversation conversation = conversationsService.getConversation(id);

        if (conversation.getBuyer().equals(user) || conversation.getOffer().getSeller().equals(user)) {
            conversationsService.deleteConversationById(id);
            return "redirect:/conversation/list";

        }else{
            System.out.println("Error");
            return null;
        }
    }

}







