package com.example.recipekeeper;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

public class Recipe implements Serializable {
    private long id;
    private String title;
    private String description;
    private String imageUrl;

    public Recipe(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

