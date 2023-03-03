package com.uniovi.sdi2223entrega1n.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String email;
    private String name;
    private String lastName;
    private String role;
    private String password;

    @Transient //propiedad que no se almacena en la tabla.
    private String passwordConfirm;
    // Ofertas que el usuario pone en venta
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Offer> onSale;
    // Ofertas que el usuario ha comprado
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private Set<Offer> bought;
    public User(String email, String name, String lastName) {
        super();
        this.email = email;
        this.name = name;
        this.lastName = lastName;
    }
    public User() { }
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getEmail() {return email; }
    public void setEmail(String dni) { this.email = dni; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFullName() {
        return this.name + " " + this.lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Offer> getOnSale() {
        return onSale;
    }

    public Set<Offer> getBought() {
        return bought;
    }
}