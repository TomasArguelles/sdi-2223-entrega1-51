package com.uniovi.sdi2223entrega1n.services;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
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
     * Dar de baja una oferta por su identificador.
     *
     * @param id Identificador de la oferta.
     * @return
     */
    public void deleteOfferById(final Long id) {
        offersRepository.deleteById(id);
    }

    /**
     * Metodo que devuelve todos las ofertas del sistema
     *
     * @return offers Lisa de ofertas.
     */
    public List<Offer> getAllOffersToBuy(String userEmail) {
        List<Offer> offers = new ArrayList<>();
        offers = offersRepository.findAllToBuy(userEmail);
        return offers;
    }

    /**
     * Buscar ofertas por nombre y que no sean del usuario
     * @param searchText nombre a buscar
     * @param userEmail email del usuario que no debe ser el vendedor
     * @return lista de ofertas
     */
    public List<Offer> searchOffersByNameAndUser(String searchText,String userEmail) {
        List<Offer> offers = new ArrayList<>();
        searchText = "%"+searchText+"%";
        offers = offersRepository.searchByName(searchText,userEmail);
        return offers;
    }

    /**
     * Metodo que actualiza una oferta a vendida
     * @param id de la oferta
     */
    public void setOfferSold(Long id,User user) {
        offersRepository.setOfferSold(true,id);
        offersRepository.setBuyer(user,id);
    }

    /**
     * Metodo que buscar una oferta por id
     * @param id de la oferta
     * @return la oferta
     */
    public Offer getOffer(Long id) {
        return offersRepository.findById(id).get();
    }

    /**
     * Obtiene todas las ofertas compradas por un usuario
     * @param email, email del usuario
     * @return offers, la lista de ofertas compradas
     */
    public List<Offer> getAllByBuyer(String email) {
        List<Offer> offers = new ArrayList<>();
        offersRepository.findAllByBuyer(email).forEach(offers::add);
        return offers;
    }

    /**
     * Destacar una oferta por su identificador
     * @param id, identificador de la oferta a destacar
     */
    public void featuredOfferById(Long id) {
        offersRepository.featuredOfferById(id);
    }

    /**
     * Obtiene todas las ofertas destacadas (de todos los usuarios)
     */
    public List<Offer> getAllFeatured() {
        return offersRepository.getAllFeatured();
    }

}
