package com.uniovi.sdi2223entrega1n.repositories;

import com.uniovi.sdi2223entrega1n.entities.Conversation;
import com.uniovi.sdi2223entrega1n.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface ConversationsRepository extends CrudRepository<Conversation, Long> {
}
