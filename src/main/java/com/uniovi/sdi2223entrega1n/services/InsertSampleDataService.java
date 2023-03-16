package com.uniovi.sdi2223entrega1n.services;



import com.uniovi.sdi2223entrega1n.entities.Conversation;

import com.uniovi.sdi2223entrega1n.entities.Message;
import com.uniovi.sdi2223entrega1n.entities.Offer;
import com.uniovi.sdi2223entrega1n.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Service
public class InsertSampleDataService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;
    @Autowired
    private OffersService offersService;

    @Autowired
    private MessagesService msgService;

    @Autowired
    private ConversationsService convService;

    @PostConstruct
    public void init() {
        User user1 = new User("usuario1@email.com", "User", "Normal");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);
        user1.setWallet(154.0);
        usersService.addUser(user1);


        User user3 = new User("usuario2@email.com", "User2", "Normal");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);
        user3.setWallet(154.0);
        usersService.addUser(user3);

        User user4 = new User("usuario3@email.com", "User", "Normal");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[0]);
        user4.setWallet(100.0);
        usersService.addUser(user4);

        User user5 = new User("usuario4@email.com", "User", "Normal");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[0]);
        user5.setWallet(154.0);
        usersService.addUser(user5);

        User user2 = new User("admin@email.com", "User", "Admin");
        user2.setPassword("admin");
        user2.setRole(rolesService.getRoles()[1]);
        usersService.addUser(user2);


        Offer offer1 = new Offer("Coche1", "Opel", 124.0);
        offer1.setSeller(user1);
        offersService.add(offer1);

        Offer offer2 = new Offer("Coche2", "Peugeot", 14.0);
        offer2.setSeller(user1);
        offer2.setSold(true);
        offersService.add(offer2);

        Offer offer3 = new Offer("Coche3", "Citroen", 24.0);
        offer3.setSeller(user1);
        offersService.add(offer3);

        Offer offer4 = new Offer("Coche4", "Aston Martin", 1224.0);
        offer4.setSeller(user1);
        offersService.add(offer4);

        Offer offer5 = new Offer("Moto1", "KTM", 234.0);
        offer5.setSeller(user3);
        offersService.add(offer5);

        Offer offer6 = new Offer("Moto2", "Honda", 12.0);
        offer6.setSeller(user3);
        offersService.add(offer6);

        Offer offer7 = new Offer("Consola1", "PS4", 244.0);
        offer7.setSeller(user4);
        offersService.add(offer7);

        Offer offer8 = new Offer("Consola2", "XBOX", 128.0);
        offer8.setSeller(user4);
        offersService.add(offer8);

        Offer offer9 = new Offer("Movil1", "iPhone", 700.0);
        offer9.setSeller(user5);
        offersService.add(offer9);

        Offer offer10 = new Offer("Movil2", "Samsung", 228.0);
        offer10.setSeller(user5);
        offersService.add(offer10);

        Offer offer11 = new Offer("Movil3", "LG", 28.0);
        offer11.setSeller(user5);
        offersService.add(offer11);

        Offer offer12 = new Offer("Coche", "LG", 28.0);
        offer12.setSeller(user1);
        offersService.add(offer12);

        Offer offer13 = new Offer("Carro", "LG", 28.0);
        offer13.setSeller(user3);
        offersService.add(offer13);

        Conversation c1=new Conversation();
        c1.setOffer(offer12);
        c1.setBuyer(user3);
        convService.add(c1);


        Conversation c2=new Conversation();
        c2.setOffer(offer13);
        c2.setBuyer(user1);
        convService.add(c2);
        Conversation c3=new Conversation();
        c3.setOffer(offer10);
        c3.setBuyer(user1);
        convService.add(c3);

        Message m1=new Message();
        m1.setSender(user1);
        m1.setDate(Instant.now());
        m1.setText("Hola, sigue disponible?");
        msgService.add(m1);
        Message m2=new Message();
        m2.setSender(offer13.getSeller());
        m2.setDate(Instant.now());
        m2.setText("Sí, sigue");
        msgService.add(m2);
        Message m3=new Message();
        m3.setSender(user1);
        m3.setDate(Instant.now());
        m3.setText("Genial!");
        msgService.add(m3);
        c2.addMessage(m1);
        c2.addMessage(m2);
        c2.addMessage(m3);
        convService.save(c2);

        Message m4=new Message();
        m4.setSender(user1);
        m4.setDate(Instant.now());
        m4.setText("Hola, sigue disponible?");
        msgService.add(m4);
        c3.addMessage(m4);
        convService.add(c3);




    }
}

