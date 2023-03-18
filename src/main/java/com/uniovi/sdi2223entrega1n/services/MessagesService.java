package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.entities.Conversation;
import com.uniovi.sdi2223entrega1n.entities.Message;
import com.uniovi.sdi2223entrega1n.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    public void add(Message newMsg){messagesRepository.save(newMsg);}
}
