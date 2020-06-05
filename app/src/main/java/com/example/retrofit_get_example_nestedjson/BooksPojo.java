package com.example.retrofit_get_example_nestedjson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BooksPojo {

    @SerializedName("book_array")
    @Expose
    private List<Book_Array> book_arrayList;

    public List<Book_Array> getBook_arrayList() {
        return book_arrayList;
    }

    public void setBook_arrayList(List<Book_Array> book_arrayList) {
        this.book_arrayList = book_arrayList;
    }
}
