package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminDeleteUser extends AppCompatActivity {

    Database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_user);

        EditText userID = findViewById(R.id.editUserId);
        Button deleteUser = findViewById(R.id.btnAdminUpdateUser);
        DB = new Database(this);

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userID.getText().toString();

                if (TextUtils.isEmpty(userID.getText().toString()) ) {
                    Toast.makeText(AdminDeleteUser.this, "Please enter a user ID to delete", Toast.LENGTH_SHORT).show();
                }else{
                    DB.deleteUser(user);
                    userID.getText().clear();
                    Toast.makeText(AdminDeleteUser.this, "User deleted.", Toast.LENGTH_SHORT).show();
                }
                }
        });
    }
}