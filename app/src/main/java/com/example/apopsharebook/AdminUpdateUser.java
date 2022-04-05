package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminUpdateUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_user);

        EditText userId = findViewById(R.id.editUserId);
        Button selectUser = findViewById(R.id.btnAdminUpdateUser);

        selectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = selectUser.getText().toString();
                Intent intent = new Intent (AdminUpdateUser.this, AdminUpdateUser2.class);
                intent.putExtra("user",userId.getText().toString());
                startActivity(intent);
            }
        });
    }
}