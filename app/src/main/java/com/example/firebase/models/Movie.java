package com.example.firebase.models;

import java.io.Serializable;

public class Movie implements Serializable {
    public String id;
    public String title;
    public String description;
    public String imageUrl;
    public String genre;
    public int durationMinutes;

    public Movie() {}

    public Movie(String id, String title, String description, String imageUrl, String genre, int durationMinutes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
    }
}