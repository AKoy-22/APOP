package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class ReadingTracker extends AppCompatActivity {
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_tracker);
        db = new Database(this);
        EditText inpTitle = findViewById(R.id.editBookTitle);
        EditText inpIsbn = findViewById(R.id.editBookISBN);
        EditText inpAuthor = findViewById(R.id.editBookAuthor);
        EditText inpTime = findViewById(R.id.editTimeSpent);

        ImageButton go_back = findViewById(R.id.btnBack);
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        ImageButton btnMessage = findViewById(R.id.btnMessageIcon);
        Button btnAddTracker = findViewById(R.id.btnAddTrackerBook);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userId = sharedPreferences.getString("userId", "NA");

        //button event for go back main page
        go_back.setOnClickListener(v -> startActivity(new Intent(ReadingTracker.this,MainMenu.class)));
        btnMessage.setOnClickListener(v -> startActivity(new Intent(ReadingTracker.this,Message.class)));
        btnAddTracker.setOnClickListener(v -> startActivity(new Intent(ReadingTracker.this,AddBookTracker.class)));

        //the bottom menu bar to link the pages
        bottom_menu.setSelectedItemId(R.id.menu_reading_tracker);
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.menu_add_book) {
                startActivity(new Intent(ReadingTracker.this,AddBook.class));
            } else if(menuItem.getItemId() == R.id.menu_update_book) {
                startActivity(new Intent(ReadingTracker.this,UpdateBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_borrow_book) {
                startActivity(new Intent(ReadingTracker.this,BorrowBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_reading_tracker) {
                startActivity(new Intent(ReadingTracker.this,ReadingTracker.class));
            } else if(menuItem.getItemId() == R.id.menu_user_account) {
                startActivity(new Intent(ReadingTracker.this,UserAccount.class));
            }
            return true;
        });

        btnAddTracker.setOnClickListener(new View.OnClickListener() {
            boolean isInserted;
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(inpIsbn.getText()) || TextUtils.isEmpty(inpTitle.getText())||
                        TextUtils.isEmpty(inpAuthor.getText()) || TextUtils.isEmpty(inpTime.getText())){
                    if(TextUtils.isEmpty(inpIsbn.getText())){
                        inpIsbn.setError("Please input ISBN");
                    }
                    if(TextUtils.isEmpty(inpTitle.getText())){
                        inpTitle.setError("Please input title");
                    }
                    if(TextUtils.isEmpty(inpAuthor.getText())){
                        inpAuthor.setError("Please input Author");
                    }
                    if(TextUtils.isEmpty(inpTime.getText())){
                        inpTime.setError("Please input time spent");
                    }
                }
                else {
                    isInserted = db.addBookToRh(inpIsbn.getText().toString(),
                            inpTitle.getText().toString(), inpAuthor.getText().toString(), userId, Double.parseDouble(inpTime.getText().toString()));
                    if (isInserted) {
                        Toast.makeText(ReadingTracker.this,
                                Html.fromHtml("<big>Data is added</big>"),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ReadingTracker.this,
                                "Data is not added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}