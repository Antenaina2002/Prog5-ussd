package com.STD22073.models;

import com.STD22073.enums.OfferType;
import lombok.Data;

@Data
public class Offer {
    private OfferType type;
    private String name;
    private double price;

    public Offer(String name, double price, OfferType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }
}