package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.entities.Conversation;
import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.repositories.ConversationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationsService {

    @Autowired
    private ConversationsRepository conversationsRepository;

    public  Conversation getConversationByOfferIdAndBuyer(Long offerId, User buyer) {
        return conversationsRepository.findConversationByOfferIdAndBuyer(offerId, buyer);
    }

    public  Conversation getConversationByOfferIdAndSeller(Long offerId, User buyer) {
        return conversationsRepository.findConversationByOfferIdAndSeller(offerId, buyer);
    }

    public void add(Conversation newConv){conversationsRepository.save(newConv);}

    public List<Conversation> findAllConversationsFromUserByEmail(User seller) {



        return conversationsRepository.findAllBySeller(seller);
    }

    public List<Conversation> findAllConversationsByBuyer(User buyer) {
        return conversationsRepository.findAllByBuyer(buyer);
    }
    public List<Conversation> findByOfferId(Long offerId) {
        return conversationsRepository.findByOfferId(offerId);
    }

    public Conversation save(Conversation conversation) {
        conversationsRepository.save(conversation);
        return conversation;
    }

    public Conversation getConversation(Long id) {
        return conversationsRepository.findConversationById(id);
    }


    public void deleteConversationById(Long id) {
        conversationsRepository.deleteById(id);
    }

    public void deleteFromBuyer(Long userId) {
        conversationsRepository.deleteByBuyerId(userId);
    }

    public void deleteConversation(Conversation conversation) {
        conversationsRepository.deleteById(conversation.getId());
    }


}
