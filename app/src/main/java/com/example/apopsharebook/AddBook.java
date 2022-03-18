package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AddBook extends AppCompatActivity {
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        db = new Database(this);

        ImageButton go_back = findViewById(R.id.btnBack);
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        ImageButton btnMessage = findViewById(R.id.btnMessageIcon);
        Button btnAddBook = findViewById(R.id.btnBookSubmit);
        EditText inpTitle = findViewById(R.id.editBookTitle);
        EditText inpIsbn = findViewById(R.id.editBookISBN);
        EditText inpAuthor = findViewById(R.id.editBookAuthor);
        EditText inpPublisher=findViewById(R.id.editBookPublisher);
        EditText inpPubYear=findViewById(R.id.editBookYear);
        Spinner genre = findViewById(R.id.editBookCategory);
        RadioGroup rGroup = findViewById(R.id.rd_group1);
        //button event for go back main page
        go_back.setOnClickListener(v -> startActivity(new Intent(AddBook.this,MainMenu.class)));
        btnMessage.setOnClickListener(v -> startActivity(new Intent(AddBook.this,Message.class)));

        //the bottom menu bar to link the pages
        bottom_menu.setSelectedItemId(R.id.menu_add_book);
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.menu_add_book) {
                startActivity(new Intent(AddBook.this,AddBook.class));
            } else if(menuItem.getItemId() == R.id.menu_update_book) {
                startActivity(new Intent(AddBook.this,UpdateBook.class));
            } else if(menuItem.getItemId() == R.id.menu_borrow_book) {
                startActivity(new Intent(AddBook.this,BorrowBook.class));
            } else if(menuItem.getItemId() == R.id.menu_reading_tracker) {
                startActivity(new Intent(AddBook.this,ReadingTracker.class));
            } else if(menuItem.getItemId() == R.id.menu_user_account) {
                startActivity(new Intent(AddBook.this,UserAccount.class));
            }
            return true;
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            boolean isInserted;
            @Override
            public void onClick(View view) {
                isInserted = db.addBook(Integer.parseInt(inpIsbn.getText().toString()),
                        inpTitle.getText().toString(),genre.getSelectedItem().toString(),
                        inpAuthor.getText().toString(),inpPublisher.getText().toString(),Integer.parseInt(inpPubYear.getText().toString()),"prabh@xzy.com","For rent");
                if(isInserted){
                    Toast.makeText(AddBook.this,
                            Html.fromHtml("<big>Data is added</big>"),
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddBook.this,
                            "Data is not added", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}