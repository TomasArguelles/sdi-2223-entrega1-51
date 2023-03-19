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
    private ConversationsService convService;
    @Autowired
    private  MessagesService msgService;

    @PostConstruct
    public void init() {
        User eliminar1 = new User("eliminar1@email.com", "User", "Normal");
        eliminar1.setPassword("123456");
        eliminar1.setRole(rolesService.getRoles()[0]);
        eliminar1.setWallet(154.0);
        usersService.addUser(eliminar1);
        User eliminar2 = new User("eliminar2@email.com", "User", "Normal");
        eliminar2.setPassword("123456");
        eliminar2.setRole(rolesService.getRoles()[0]);
        eliminar2.setWallet(154.0);
        usersService.addUser(eliminar2);
        User eliminar3 = new User("eliminar3@email.com", "User", "Normal");
        eliminar3.setPassword("123456");
        eliminar3.setRole(rolesService.getRoles()[0]);
        eliminar3.setWallet(154.0);
        usersService.addUser(eliminar3);
        User eliminar4 = new User("eliminar4@email.com", "User", "Normal");
        eliminar4.setPassword("123456");
        eliminar4.setRole(rolesService.getRoles()[0]);
        eliminar4.setWallet(154.0);
        usersService.addUser(eliminar4);



        User user1 = new User("user01@email.com", "User", "Normal");
        user1.setPassword("user01");
        user1.setRole(rolesService.getRoles()[0]);
        user1.setWallet(154.0);
        usersService.addUser(user1);


        User user2 = new User("user02@email.com", "User2", "Normal");
        user2.setPassword("user02");
        user2.setRole(rolesService.getRoles()[0]);
        user2.setWallet(154.0);
        usersService.addUser(user2);

        User user3 = new User("user03@email.com", "User3", "Normal");
        user3.setPassword("user03");
        user3.setRole(rolesService.getRoles()[0]);
        user3.setWallet(100.0);
        usersService.addUser(user3);

        User user4 = new User("user04@email.com", "User", "Normal");
        user4.setPassword("user04");
        user4.setRole(rolesService.getRoles()[0]);
        user4.setWallet(154.0);
        usersService.addUser(user4);

        User admin = new User("admin@email.com", "User", "Admin");
        admin.setPassword("admin");
        admin.setRole(rolesService.getRoles()[1]);
        usersService.addUser(admin);

        User user5= new User("user05@email.com", "User", "Normal");
        user5.setPassword("user05");
        user5.setRole(rolesService.getRoles()[0]);
        user5.setWallet(154.0);
        usersService.addUser(user5);

        User user6= new User("user06@email.com", "User", "Normal");
        user6.setPassword("user06");
        user6.setRole(rolesService.getRoles()[0]);
        user6.setWallet(154.0);
        usersService.addUser(user6);

        User user7= new User("user07@email.com", "User", "Normal");
        user7.setPassword("user07");
        user7.setRole(rolesService.getRoles()[0]);
        user7.setWallet(154.0);
        usersService.addUser(user7);


        User user8= new User("user08@email.com", "User", "Normal");
        user8.setPassword("user08");
        user8.setRole(rolesService.getRoles()[0]);
        user8.setWallet(14.0);
        usersService.addUser(user8);

        User user9= new User("user09@email.com", "User", "Normal");
        user9.setPassword("user09");
        user9.setRole(rolesService.getRoles()[0]);
        user9.setWallet(14.0);
        usersService.addUser(user9);

        User user10= new User("user10@email.com", "User", "Normal");
        user10.setPassword("user10");
        user10.setRole(rolesService.getRoles()[0]);
        user10.setWallet(14.0);
        usersService.addUser(user10);

        User user11= new User("user11@email.com", "User", "Normal");
        user11.setPassword("user11");
        user11.setRole(rolesService.getRoles()[0]);
        user11.setWallet(14.0);
        usersService.addUser(user11);
        User user12= new User("user12@email.com", "User", "Normal");
        user12.setPassword("user12");
        user12.setRole(rolesService.getRoles()[0]);
        user12.setWallet(14.0);
        usersService.addUser(user12);
        User user13= new User("user13@email.com", "User", "Normal");
        user13.setPassword("user13");
        user13.setRole(rolesService.getRoles()[0]);
        user13.setWallet(14.0);
        usersService.addUser(user8);
        User user14= new User("user14@email.com", "User", "Normal");
        user14.setPassword("user14");
        user14.setRole(rolesService.getRoles()[0]);
        user14.setWallet(14.0);
        usersService.addUser(user14);
        User user15= new User("user15@email.com", "User", "Normal");
        user15.setPassword("user15");
        user15.setRole(rolesService.getRoles()[0]);
        user15.setWallet(14.0);
        usersService.addUser(user15);

        User eliminar5 = new User("eliminar5@email.com", "eliminar5", "eliminar5");
        eliminar5.setPassword("123456");
        eliminar5.setRole(rolesService.getRoles()[0]);
        eliminar5.setWallet(154.0);
        usersService.addUser(eliminar5);

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

        Offer offer12 = new Offer("Coche2", "Peugeot", 14.0);
        offer12.setSeller(user6);
        offer12.setSold(true);
        offersService.add(offer12);

        Offer offer13 = new Offer("Coche3", "Citroen", 24.0);
        offer13.setSeller(user6);
        offersService.add(offer13);

        Offer offer14 = new Offer("Coche4", "Aston Martin", 1224.0);
        offer14.setSeller(user6);
        offersService.add(offer14);

        //Conversation c1=new Conversation(offer9);

        Offer offer15 = new Offer("Carro", "Carro correro", 2.0);
        offer15.setSeller(user4);
        offersService.add(offer15);
        
        Offer offer16 = new Offer("Coche5", "Seat", 18.0);
        offer16.setSeller(user8);
        offersService.add(offer16);

        Conversation c2=new Conversation();
        c2.setOffer(offer15);
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

