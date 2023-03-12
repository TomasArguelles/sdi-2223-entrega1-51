package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.repositories.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OffersService {

    @Autowired
    private OffersRepository offersRepository;

    /**
     * Recibe una nueva oferta y la persiste en la base de datos.
     *
     * @param newOffer Datos de la nueva oferta.
     */
    public void add(Offer newOffer) {
        // Fecha a dia de hoy
        newOffer.setDateUpload(Instant.now());
        offersRepository.save(newOffer);
    }

    /**
     * Listado de ofertas propias de un usuario.
     *
     * @param userId Id del usuario.
     * @return
     */
    public List<Offer> findAllOffersFromUserById(final Long userId) {
        List<Offer> offers = new ArrayList<>();
        //offersRepository.findAllBySellerId(userId).forEach(offers::add);
        offersRepository.findAll().forEach(offers::add);
        return offers;
    }
}
