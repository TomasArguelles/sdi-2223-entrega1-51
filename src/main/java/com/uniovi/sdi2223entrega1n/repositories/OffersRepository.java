package com.uniovi.sdi2223entrega1n.repositories;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OffersRepository extends CrudRepository<Offer, Long> {

    @Query("SELECT o FROM Offer o WHERE o.seller.email = ?1")
    List<Offer> findAllBySeller(final String sellerEmail);
}
