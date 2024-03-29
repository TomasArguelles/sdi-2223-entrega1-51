package com.uniovi.sdi2223entrega1n.repositories;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OffersRepository extends CrudRepository<Offer, Long> {

    @Query("SELECT o FROM Offer o WHERE o.seller.email = ?1")
    List<Offer> findAllBySeller(final String sellerEmail);

    @Query("SELECT o FROM Offer o WHERE (LOWER(o.title) LIKE LOWER(?1)) AND o.seller.email NOT LIKE ?2")
    List<Offer> searchByName(String searchText, String userEmail);

    @Query("SELECT o FROM Offer o WHERE o.seller.email NOT LIKE ?1")
    List<Offer> findAllToBuy(String userEmail);

    @Query("SELECT o FROM Offer o WHERE o.buyer.email = ?1 and o.sold = true")
    List<Offer> findAllByBuyer(final String buyerEmail);

    @Query("SELECT o FROM Offer o WHERE o.featured = true")
    List<Offer> getAllFeatured();

    @Modifying
    @Transactional
    @Query("UPDATE Offer SET sold =?1 WHERE id = ?2")
    void setOfferSold(boolean value, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Offer SET buyer =?1 WHERE id = ?2")
    void setBuyer(User user, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Offer SET featured = true WHERE id = ?1")
    void featuredOfferById(Long id);

}
