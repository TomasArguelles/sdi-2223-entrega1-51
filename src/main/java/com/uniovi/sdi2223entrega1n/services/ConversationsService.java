package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.entities.Conversation;
import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.repositories.ConversationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationsService {

    @Autowired
    private ConversationsRepository conversationsRepository;

    public  Conversation getConversationByOfferIdAndBuyer(Long offerId, User buyer) {
        return conversationsRepository.findConversationByOfferId(offerId, buyer);
    }

    public void add(Conversation newConv){conversationsRepository.save(newConv);}

    public List<Conversation> findAllConversationsFromUserByEmail(User seller) {



        return conversationsRepository.findAllBySeller(seller);
    }
    public Conversation findByOfferId(Long offerId) {
        return conversationsRepository.findByOfferId(offerId);
    }

    public Conversation save(Conversation conversation) {
        conversationsRepository.save(conversation);
        return conversation;
    }

    public Conversation getConversation(Long id) {
        return conversationsRepository.findConversationById(id);
    }


}
