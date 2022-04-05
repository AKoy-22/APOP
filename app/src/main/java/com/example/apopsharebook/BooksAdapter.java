package com.example.apopsharebook;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

//    Context context;
//    RecyclerView recyclerView;
//    final View.OnClickListener onClickListener = new MyOnClickListener();
    List<Books> booksList;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;
    int[] images = {R.drawable.book_cover_1,R.drawable.book_cover_2,R.drawable.book_cover_3,
                    R.drawable.cover02,R.drawable.cover01,R.drawable.cover03,
                    R.drawable.cover04};

    Random r = new Random();
    int low = 0;
    int high = 6;
    int img = r.nextInt(high-low) + low;

    public BooksAdapter(Context context,List<Books> booksList){
        inflater = LayoutInflater.from(context);
        this.booksList = booksList;

    }

    @NonNull
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.ViewHolder holder, int position) {
        Books book = booksList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookGenre.setText(book.getGenre()+" | ");
        holder.bookStatus.setText(book.getStatus());
        holder.bookYear.setText(book.getYear());
        holder.bookOwner.setText(book.getOwner());
        holder.bookImage.setImageResource(images[img]);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    void setClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener{
        TextView bookTitle,bookAuthor,bookGenre,bookStatus,bookYear,bookOwner;
        ImageView bookImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookGenre = itemView.findViewById(R.id.book_genre);
            bookStatus = itemView.findViewById(R.id.book_status);
            bookYear = itemView.findViewById(R.id.book_year);
            bookOwner = itemView.findViewById(R.id.book_Owner);
            bookImage = itemView.findViewById(R.id.book_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemClickListener!=null)
                itemClickListener.onItemClick(view,getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view,int position);
    }


//    private class MyOnClickListener implements View.OnClickListener {
//        String bookT,bookA,bookG,bookS,bookY,bookO;
//        @Override
//        public void onClick(View view) {
//            int itemPos = recyclerView.getChildAdapterPosition(view);
//             bookT = booksList.get(itemPos).getTitle();
//             bookA = booksList.get(itemPos).getAuthor();
//             bookG = booksList.get(itemPos).getGenre();
//            bookS = booksList.get(itemPos).getStatus();
//            bookY = booksList.get(itemPos).getYear();
//            bookO = booksList.get(itemPos).getOwner();
//
//            Intent i = new Intent(context,UpdatePage.class);
//            i.putExtra("title",bookT);
//            i.putExtra("author",bookA);
//            i.putExtra("year",bookY);
//
//
//
//            Toast.makeText(context,bookT,Toast.LENGTH_LONG).show();
//        }
//    }

}
