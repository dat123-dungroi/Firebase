package com.example.firebase.models;

import java.util.Date;

public class Ticket {
    public String id;
    public String userId;
    public String showtimeId;
    public String movieTitle;
    public String theaterName;
    public Date showtime;
    public String seatNumber;
    public double price;

    public Ticket() {}

    public Ticket(String id, String userId, String showtimeId, String movieTitle, String theaterName, Date showtime, String seatNumber, double price) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.movieTitle = movieTitle;
        this.theaterName = theaterName;
        this.showtime = showtime;
        this.seatNumber = seatNumber;
        this.price = price;
    }
}