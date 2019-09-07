package com.alexis.swipy.Modules;

import com.google.gson.annotations.SerializedName;

public class Categorie {
    @SerializedName("CategorieId")
    private int id;

    @SerializedName("CategorieName")
    private String name;

    @SerializedName("ImageUrl")
    private String image;

    public Categorie(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
