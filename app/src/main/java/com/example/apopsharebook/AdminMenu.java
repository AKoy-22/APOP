package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class AdminMenu extends AppCompatActivity {

    ArrayList prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu2);

        Button createUser = findViewById(R.id.btnCreateUser);
        Button btnViewUser = findViewById(R.id.btnDeleteUser);
        Button logout = findViewById(R.id.btnLogOut);
        Button deleteUser = findViewById(R.id.btnDeleteUser);
        Button updateUser = findViewById(R.id.btnUpdateUser);
        Button messageUser = findViewById(R.id.btnMsgUser);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMenu.this, AdminCreateUser.class));
            }
        });

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMenu.this, AdminDeleteUser.class));
            }
        });

        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMenu.this, AdminUpdateUser.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sharedPreferences.edit().remove("userId").commit();
                startActivity(new Intent(AdminMenu.this,Login.class));
            }
        });

        messageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMenu.this,AdminMsgUser.class));
            }
        });
    }
}