package com.uniovi.sdi2223entrega1n.repositories;

import com.uniovi.sdi2223entrega1n.entities.Conversation;
import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConversationsRepository extends CrudRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c WHERE c.offer.seller = ?1")
    List<Conversation> findAllBySeller(User user);
    @Query("SELECT c FROM Conversation c WHERE c.buyer = ?1")
    List<Conversation> findAllByBuyer(User user);


    @Query("SELECT c FROM Conversation c WHERE c.offer.id= ?1 and c.buyer= ?2")
    Conversation findConversationByOfferIdAndBuyer(Long offerId, User buyer);

    @Query("SELECT c FROM Conversation c WHERE c.offer.id= ?1 and c.offer.seller= ?2")
    Conversation findConversationByOfferIdAndSeller(Long offerId, User seller);

    @Query("SELECT c FROM Conversation c WHERE c.offer.id= ?1 ")
    List<Conversation> findByOfferId(Long offerId);

    @Query("SELECT c FROM Conversation c WHERE c.id= ?1")
    Conversation findConversationById(Long id);
}
