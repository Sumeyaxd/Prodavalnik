package com.example.demo.model.dto;

import com.example.demo.model.enums.ShopEnum;

import java.time.LocalTime;

public class ShopDetailsDTO {
    private ShopEnum city;
    private String description;

    private String email;

    private String phoneNumber;

    private String address;

    private LocalTime open;

    private LocalTime close;

    private String imageUrl;

    public ShopDetailsDTO() {
    }

    public ShopEnum getCity() {
        return city;
    }

    public void setCity(ShopEnum city) {
        this.city = city;
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
}
