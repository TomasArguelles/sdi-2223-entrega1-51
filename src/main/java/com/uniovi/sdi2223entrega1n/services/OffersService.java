package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.repositories.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OffersService {

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private UsersService usersService;

    /**
     * Recibe una nueva oferta y la persiste en la base de datos.
     *
     * @param newOffer Datos de la nueva oferta.
     */
    public void add(Offer newOffer) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User seller = usersService.getUserByEmail(email);

        // El vendedor es el usuario en sesión
        newOffer.setSeller(seller);

        // Fecha a dia de hoy
        newOffer.setDateUpload(Instant.now());
        offersRepository.save(newOffer);
    }

    /**
     * Listado de ofertas propias de un usuario.
     *
     * @param userEmail Email del usuario.
     * @return
     */
    public List<Offer> findAllOffersFromUserByEmail(final String userEmail) {
        List<Offer> offers = new ArrayList<>();
        for (Offer o : offersRepository.findAll()) {
            System.out.println("Offer: " + o + " " + o.getSeller().getEmail());
        }
        offersRepository.findAllBySeller(userEmail).forEach(offers::add);
        return offers;
    }

    /**
     * Dar de baja una oferta por su identificador.
     *
     * @param id Identificador de la oferta.
     * @return
     */
    public void deleteOfferById(final Long id) {
        offersRepository.deleteById(id);
    }
}
