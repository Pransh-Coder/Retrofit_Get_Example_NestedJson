package com.example.retrofit_get_example_nestedjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book_Array {

    @SerializedName("book_title")
    @Expose
    private String title;

    @SerializedName("image")
    @Expose
    private String imglink;

    @SerializedName("author")
    @Expose
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
