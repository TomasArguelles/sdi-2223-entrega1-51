package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.repositories.ConversationsRepository;
import com.uniovi.sdi2223entrega1n.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationsService {

    @Autowired
    private ConversationsRepository conversationsRepository;
}
