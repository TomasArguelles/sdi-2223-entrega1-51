package com.uniovi.sdi2223entrega1n.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Conversation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="offer_id")
    private Offer offer;

    @OneToOne
    private User buyer;
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<Message> msgs;


    boolean state;

    public Conversation() {

        this.msgs=new ArrayList<>();
        this.state=false;
    }

    public Conversation(Long id, Offer offer) {
        this.id = id;
        this.offer = offer;
        this.state=false;
        this.msgs=new ArrayList<>();
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

    public List<Message> getMsgs() {
        return msgs;
    }



    public void setState(boolean s){this.state=s;}
    public boolean getState(){return this.state;}
    public void addMessage(Message m){
        msgs.add(m);
        m.setConversation(this);
        //System.out.println("Mensaje "+m.getText()+" añadido a "+getId());
        //System.out.println("Tamaño de conversacion  "+getMsgs().size());
        //System.out.println("Conv  "+this);
        //System.out.println("Msg conv  "+m.getConversation());
    }

    public void setBuyer(User u){this.buyer=u;}
    public User getBuyer(){return this.buyer;}



}
