package com.prodavalnik.prodavalnik.model.entity;

import com.prodavalnik.prodavalnik.model.enums.ShopEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

@Entity
@Table(name = "shops")
public class Shop extends BaseEntity {
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ShopEnum city;

    @Column(nullable = false)
    @Size(min = 3, max = 200)
    private String description;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    @Size(min = 7, max = 15)
    private String phoneNumber;

    @Column(nullable = false)
    @Size(min = 3, max = 255)
    private String address;

    @Column(name = "open_time", nullable = false)
    private LocalTime open;

    @Column(name = "close_time", nullable = false)
    private LocalTime close;

    @Column(name = "image_url", nullable = false)
    @Size(min = 3, max = 50)
    private String imageUrl;

    public Shop() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalTime getOpen() {
        return open;
    }

    public void setOpen(LocalTime open) {
        this.open = open;
    }

    public LocalTime getClose() {
        return close;
    }

    public void setClose(LocalTime close) {
        this.close = close;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ShopEnum getCity() {
        return city;
    }

    public void setCity(ShopEnum city) {
        this.city = city;
    }

}
