package com.STD22073.services;

import com.STD22073.enums.OfferType;
import com.STD22073.models.Offer;

import java.util.Arrays;
import java.util.List;

public class OfferService {
    private final List<Offer> offers = Arrays.asList(
            new Offer("Appel 60min", 2000, OfferType.VOICE),
            new Offer("Internet 1Go", 5000, OfferType.INTERNET),
            new Offer("SMS Illimités", 1500, OfferType.MESSAGING)
    );

    public List<Offer> getAllOffers() {
        return offers;
    }
}
