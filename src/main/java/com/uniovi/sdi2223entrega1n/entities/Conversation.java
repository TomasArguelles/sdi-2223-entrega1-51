package com.uniovi.sdi2223entrega1n.entities;

import javax.persistence.*;
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

    public Conversation() {
    }

    public Conversation(Long id, Offer offer) {
        this.id = id;
        this.offer = offer;
    }

    public Conversation(Offer offer) {
        super();
        this.offer = offer;
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
}
