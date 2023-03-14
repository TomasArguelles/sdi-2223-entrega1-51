package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.repositories.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Metodo que devuelve todos las ofertas del sistema
     * @return offers Lisa de ofertas.
     */
    public List<Offer> getAllOffersToBuy(String userEmail) {
        List<Offer> offers = new ArrayList<>();
        offers = offersRepository.findAllToBuy(userEmail);
        return offers;
    }

    public List<Offer> searchOffersByNameAndUser(String searchText,String userEmail) {
        List<Offer> offers = new ArrayList<>();
        searchText = "%"+searchText+"%";
        offers = offersRepository.searchByName(searchText,userEmail);
        return offers;
    }

    public void setOfferSold(Long id) {
        offersRepository.setOfferSold(true,id);
    }
}
