package com.example.firebase.models;

import java.util.Date;

public class Showtime {
    public String id;
    public String movieId;
    public String theaterId;
    public Date time;
    public double price;

    public Showtime() {}

    public Showtime(String id, String movieId, String theaterId, Date time, double price) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.time = time;
        this.price = price;
    }
}