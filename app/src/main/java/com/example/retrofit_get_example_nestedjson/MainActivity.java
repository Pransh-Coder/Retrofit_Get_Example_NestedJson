package com.example.retrofit_get_example_nestedjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    List<Book_Array> book_arrayList = new ArrayList<>();

    int cacheSize = 10*1024*1024;   // 10 MB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Cache cache = new Cache(getCacheDir(),cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if(!IsNetworkAvailable()){
                            int maxStale = 60*60*24*28;     // tolerate 4 weeks stale
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        //  By using this we are not going to use the ApiClient that we have created
        Retrofit.Builder builder =new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/v2/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        //ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        boolean b = IsNetworkAvailable();
        if(!b){
            Toast.makeText(this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

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

                   // It is used for extracting specific keys or elements from API like "book_title" , "author" , "image"

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
    public Boolean IsNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null&&networkInfo.isConnected();

    }
}