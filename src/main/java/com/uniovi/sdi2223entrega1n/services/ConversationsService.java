package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.entities.Conversation;
import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.repositories.ConversationsRepository;
import com.uniovi.sdi2223entrega1n.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationsService {

    @Autowired
    private ConversationsRepository conversationsRepository;

    public void add(Conversation newConv){conversationsRepository.save(newConv);}

    public List<Conversation> findAllConversationsFromUserByEmail(final String sellerEmail) {
        List<Conversation> convs = new ArrayList<>();
        for(Conversation c : conversationsRepository.findAllBySeller(sellerEmail)){
            convs.add(c);
        }

        return convs;
    }
}
