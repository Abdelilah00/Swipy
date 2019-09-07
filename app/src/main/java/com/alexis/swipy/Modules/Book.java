package com.alexis.swipy.Modules;

import android.content.ContentValues;
import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("BookId")
    private int id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Description")
    private String description;

    @SerializedName("ImageUrl")
    private String imageUrl;

    @SerializedName("Image")
    private byte[] image;

    @SerializedName("Prix")
    private int prix;

    @SerializedName("Rating")
    private float rating;

    public Book(int id, String title, String description, String imageUrl, int prix, float rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.prix = prix;
        this.rating = rating;
    }

    public Book(int id, String title, String description, byte[] image, int prix) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ContentValues getContentValue(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("BookId", id);
        contentValues.put("Title", title);
        contentValues.put("Description", description);
        contentValues.put("Image", image);
        contentValues.put("Prix", prix);
        //contentValues.put("Rating", rating);
        return contentValues;
    }
}
