package com.uniovi.sdi2223entrega1n.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Conversation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="offer_id")
    private Offer offer;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private Set<Message> msgsBuyer;
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private Set<Message> msgsSeller;

    boolean state;

    public Conversation() {
        this.state=false;
    }

    public Conversation(Long id, Offer offer) {
        this.id = id;
        this.offer = offer;
        this.state=false;
    }

    public Conversation(Offer offer) {
        super();
        this.offer = offer;
        this.state=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Set<Message> getMsgsBuyer() {
        return msgsBuyer;
    }

    public Set<Message> getMsgsSeller() {
        return msgsSeller;
    }

    public void setState(boolean s){this.state=s;}
    public boolean getState(){return this.state;}
    public void addBuyerMessage(Message m){
        if(this.msgsBuyer==null)this.msgsBuyer=new HashSet<>();
        else this.msgsBuyer.add(m);
    }

    public void addSellerMessage(Message m){
        if(this.msgsSeller==null)this.msgsSeller=new HashSet<>();
        else this.msgsSeller.add(m);
    }
}
