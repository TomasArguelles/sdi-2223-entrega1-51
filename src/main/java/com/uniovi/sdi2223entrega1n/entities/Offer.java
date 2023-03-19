package com.uniovi.sdi2223entrega1n.entities;
import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Entity
public class Offer {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Instant dateUpload;
    private Double price;
    private Boolean sold = false;
    private Boolean featured = false;
    private String image;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name="user_id_buyer")
    private User buyer = null;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private Set<Conversation> conversations;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double score) {
        this.price = score;
    }

    public Offer() {
    }

    public Offer(Long id, String description, Double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public Offer(String description, Double price, User seller) {
        super();
        this.description = description;
        this.price = price;
        this.seller = seller;
    }

    public Offer(String title, String description, Double price) {
        this(title, description, price, "");

    }

    public Offer(String title, String description, Double price, String image) {
        super();
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
    }


    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateUpload=" + dateUpload +
                ", price=" + price +
                ", urlImage=" + image +
                '}';
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User user) {
        this.seller = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(id, offer.id);
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean resend) {
        this.sold = resend;
    }

    public Instant getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Instant dateUpload) {
        this.dateUpload = dateUpload;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
