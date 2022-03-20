package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BorrowBook2 extends AppCompatActivity implements RecyclerAdapter.ItemClickListener{

    int img=R.drawable.book_cover_1;
    Button btnSearch;
    ImageButton btnBack, btnMsgIcon;
    EditText editTxtSearch;
    RecyclerAdapter adapter;
    Database database;
    Cursor c;
    String inpLoc;
    ArrayList<String> bTitles, bAuthors, bGenres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book2);

        btnSearch=findViewById(R.id.btnSearch);
        editTxtSearch=findViewById(R.id.editTxtSearch);

        database=new Database(this);

        //Entering location and clicking Search button will display books in that location
        // --> !!!may be better to change to spinner rather than having user type in location to reduce input error (spelling/not existing)!!!
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bTitles=new ArrayList<String>();
                bAuthors=new ArrayList<String >();
                bGenres=new ArrayList<String>();
                inpLoc=editTxtSearch.getText().toString();
                c=database.searchBookByLocation(inpLoc);

                if(c.getCount()>0){
                    while(c.moveToNext()){
                        bTitles.add(c.getString(0));
                        bAuthors.add(c.getString(1));
                        bGenres.add(c.getString(2));
                    }
                }

                RecyclerView recyclerView=findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(BorrowBook2.this));
                adapter=new RecyclerAdapter(BorrowBook2.this, img,bTitles, bAuthors, bGenres);
                adapter.setClickListener(BorrowBook2.this);
                recyclerView.setAdapter(adapter);
            }
        });
        //Note - works with just this, but when inside a method, requires classname prior to this (AK)
        /*RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecyclerAdapter(this, img,titles,authors,genres);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);*/

        //Clicking back button will return to Main Menu activity
        btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BorrowBook2.this,MainMenu.class));
            }
        });
        //Clicking message image icon will open Message activity
        btnMsgIcon=findViewById(R.id.btnMessageIcon);
        btnMsgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BorrowBook2.this,Message.class));
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}