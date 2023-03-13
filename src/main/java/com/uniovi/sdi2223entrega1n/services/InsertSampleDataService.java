package com.uniovi.sdi2223entrega1n.services;



import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class InsertSampleDataService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;
    @Autowired
    private  OffersService offersService;

    @PostConstruct
    public void init() {
        User user1 = new User("usuario1@email.com", "User", "Normal");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user1);

        User user2 = new User("admin1@email.com", "User", "Admin");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[1]);
        usersService.addUser(user2);


        User user3 = new User("usuario2@email.com", "User", "Normal");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user3);

        User user4 = new User("usuario3@email.com", "User", "Normal");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user4);

        User user5 = new User("usuario4@email.com", "User", "Normal");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[1]);
        usersService.addUser(user5);

        Offer offer1 = new Offer("Coche1","Opel",124.0);
        offer1.setSeller(user1);
        offersService.add(offer1);

        Offer offer2 = new Offer("Coche2","Peugeot",14.0);
        offer2.setSeller(user1);
        offer2.setSold(true);
        offersService.add(offer2);

        Offer offer3 = new Offer("Coche3","Citroen",24.0);
        offer3.setSeller(user1);
        offersService.add(offer3);

        Offer offer4 = new Offer("Coche4","Aston Martin",1224.0);
        offer4.setSeller(user1);
        offersService.add(offer4);

        Offer offer5 = new Offer("Moto1","KTM",234.0);
        offer5.setSeller(user3);
        offersService.add(offer5);

        Offer offer6 = new Offer("Moto2","Honda",12.0);
        offer6.setSeller(user3);
        offersService.add(offer6);

        Offer offer7 = new Offer("Consola1","PS4",244.0);
        offer7.setSeller(user4);
        offersService.add(offer7);

        Offer offer8 = new Offer("Consola2","XBOX",128.0);
        offer8.setSeller(user4);
        offersService.add(offer8);

        Offer offer9 = new Offer("Movil1","iPhone",700.0);
        offer9.setSeller(user5);
        offersService.add(offer9);

        Offer offer10 = new Offer("Movil2","Samsung",228.0);
        offer10.setSeller(user5);
        offersService.add(offer1);

        Offer offer11 = new Offer("Movil3","LG",28.0);
        offer11.setSeller(user5);
        offersService.add(offer11);


    }
}

