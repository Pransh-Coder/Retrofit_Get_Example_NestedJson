package com.example.retrofit_get_example_nestedjson;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterBooks extends RecyclerView.Adapter<RecyclerAdapterBooks.ViewHolder>{

    Context context;
    List<Book_Array> bookDetails_pojoList = new ArrayList<>();

    public RecyclerAdapterBooks(Context context, List<Book_Array> bookDetails_pojoList) {
        this.context = context;
        this.bookDetails_pojoList = bookDetails_pojoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bookName.setText("Title : " + bookDetails_pojoList.get(position).getTitle());
        holder.bookAuthor.setText("Author :" + bookDetails_pojoList.get(position).getAuthor());
        Picasso.get().load(bookDetails_pojoList.get(position).getImglink()).into(holder.imageView);

        final String name = bookDetails_pojoList.get(position).getTitle();
        final String auth = bookDetails_pojoList.get(position).getAuthor();
        final String imgLink = bookDetails_pojoList.get(position).getImglink();

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,BookDescription.class);
                intent.putExtra("name",name);
                intent.putExtra("auth",auth);
                intent.putExtra("img",imgLink);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookDetails_pojoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView bookAuthor,bookName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            bookAuthor = itemView.findViewById(R.id.author);
            bookName = itemView.findViewById(R.id.bookTitle);
        }
    }
}

