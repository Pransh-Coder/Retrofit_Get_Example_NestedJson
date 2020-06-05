package com.example.retrofit_get_example_nestedjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class BookDescription extends AppCompatActivity {

    TextView author,BookTitle;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);

        imageView = findViewById(R.id.imageview);
        author = findViewById(R.id.txt2);
        BookTitle = findViewById(R.id.txt1);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        String authname = intent.getStringExtra("auth");
        String imgLink = intent.getStringExtra("img");

        BookTitle.setText(name);
        author.setText(authname);
        Picasso.get().load(imgLink).into(imageView);

        Toast.makeText(this, ""+name+" "+authname+" "+imgLink, Toast.LENGTH_SHORT).show();
    }
}
