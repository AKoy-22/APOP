package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class UserAccount extends AppCompatActivity {
    Database db;
    Cursor c;
    String password,FName, LName, Address, fullName, stringAge, preferences;
    int Age;
    ArrayList prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        ImageButton go_back = findViewById(R.id.btnBack);
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        ImageButton btnMessage = findViewById(R.id.btnMessageIcon);
        ImageButton btnUserEdit = findViewById(R.id.btnUserEdit);
        Button btnReadingHistory = findViewById(R.id.btnReadingHistory);
        Button btnRequestHistory = findViewById(R.id.btnRequestHistory);
        Button btnCurrentLoan = findViewById(R.id.btnCurrentLoan);
        Button btnSignOut=findViewById(R.id.btnSignOut);
        TextView userName=findViewById(R.id.txtUserName);
        TextView user_Name=findViewById(R.id.txt_user_name);
        TextView userEmail=findViewById(R.id.txtUserEmail);
        TextView userInterest=findViewById(R.id.txtUserInterested);
        TextView userAge=findViewById(R.id.txtUserAge);
        TextView userAddress=findViewById(R.id.txtUserAddress);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userId = sharedPreferences.getString("userId", "NA");
        user_Name.setText(userId);

        //filling user information
        userEmail.setText(userId);
        db=new Database(this);

        c=db.getUserInfo(userId);
        if(c.getCount()>0) {
            while (c.moveToNext()) {
                FName = c.getString(2);
                LName = c.getString(3);
                Address = c.getString(4);
                Age=c.getInt(5);
            }
        }

        fullName=FName+" "+LName;
        userName.setText(fullName);
        userAge.setText(Age+"");
        userAddress.setText(Address);

        prefs=new ArrayList<String>();
        c=db.getPreferences(userId);
        if(c.getCount()>0) {
            while (c.moveToNext()) {
             prefs.add(c.getString(1));
            }
        }
         preferences="";
         preferences=prefs.get(0).toString();
         for(int i=1;i<prefs.size();i++){
           preferences=preferences+", "+prefs.get(i).toString();
         }
         userInterest.setText(preferences);

        // button to Log Out
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().remove("userId").commit();
                startActivity(new Intent(UserAccount.this,Welcome.class));
            }
        });

        //button event for go back main page
        go_back.setOnClickListener(v -> startActivity(new Intent(UserAccount.this,MainMenu.class)));
        btnMessage.setOnClickListener(v -> startActivity(new Intent(UserAccount.this,Message.class)));
        btnUserEdit.setOnClickListener(v -> startActivity(new Intent(UserAccount.this,EditAccount.class)));
        btnReadingHistory.setOnClickListener(v -> startActivity(new Intent(UserAccount.this,ReadingHistory.class)));
        btnRequestHistory.setOnClickListener(v -> startActivity(new Intent(UserAccount.this,RequestHistory.class)));
        btnCurrentLoan.setOnClickListener(v -> startActivity(new Intent(UserAccount.this,CurrentLoan.class)));

        //the bottom menu bar to link the pages
        bottom_menu.setSelectedItemId(R.id.menu_user_account);
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.menu_add_book) {
                startActivity(new Intent(UserAccount.this,AddBook.class));
            } else if(menuItem.getItemId() == R.id.menu_update_book) {
                startActivity(new Intent(UserAccount.this,UpdateBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_borrow_book) {
                startActivity(new Intent(UserAccount.this,BorrowBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_reading_tracker) {
                startActivity(new Intent(UserAccount.this,ReadingTracker.class));
            } else if(menuItem.getItemId() == R.id.menu_user_account) {
                startActivity(new Intent(UserAccount.this,UserAccount.class));
            }
            return true;
        });



    }
}