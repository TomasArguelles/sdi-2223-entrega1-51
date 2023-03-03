package com.uniovi.sdi2223entrega1n.repositories;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface OffersRepository extends CrudRepository<Offer, Long> {
}
