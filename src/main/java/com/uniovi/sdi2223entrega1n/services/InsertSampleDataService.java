package com.uniovi.sdi2223entrega1n.services;



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

    @PostConstruct
    public void init() {
        User user1 = new User("usuario1@email.com", "User", "Normal");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);
        User user3 = new User("usuario2@email.com", "User", "Normal");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);
        User user4 = new User("usuario3@email.com", "User", "Normal");
        user4.setPassword("123456");
        User user5 = new User("usuario4@email.com", "User", "Normal");
        user5.setPassword("123456");


        User user2 = new User("admin1@email.com", "User", "Admin");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[1]);

        user4.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user1);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);
        //
        usersService.addUser(user2);


    }
}

