package com.example.retrofit_get_example_nestedjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    List<Book_Array> book_arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        apiInterface.getBooksfromURL().enqueue(new Callback<BooksPojo>() {
            @Override
            public void onResponse(Call<BooksPojo> call, Response<BooksPojo> response) {

                if(response.isSuccessful()){
                    int statusCode  = response.code();

                    Toast.makeText(MainActivity.this, ""+statusCode, Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "posts loaded from API");

                    book_arrayList = response.body().getBook_arrayList();
                    Toast.makeText(MainActivity.this, ""+book_arrayList.size(), Toast.LENGTH_SHORT).show();

                    adapter = new RecyclerAdapterBooks(MainActivity.this,book_arrayList);
                    recyclerView.setAdapter(adapter);
                    /*for (int i=0;i<book_arrayList.size();i++){

                        String title = book_arrayList.get(i).getTitle();
                        String imgLink = book_arrayList.get(i).getImglink();
                        String author = book_arrayList.get(i).getAuthor();

                        Toast.makeText(MainActivity.this, "\n "+title+"\n "+imgLink+"\n "+author, Toast.LENGTH_SHORT).show();


                    }*/

                }
                else{
                    int statusCode  = response.code();
                    Toast.makeText(MainActivity.this, ""+statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BooksPojo> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
        });

    }
}