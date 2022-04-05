package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminUpdateUser2 extends AppCompatActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_user2);

        db = new Database(this);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");

        EditText username = findViewById(R.id.editUserId);
        EditText password = findViewById(R.id.editUserPw);
        EditText fName = findViewById(R.id.editUserFname);
        EditText lName = findViewById(R.id.editUserLname);
        EditText add = findViewById(R.id.editUserAdr);
        EditText age = findViewById(R.id.editUserAge);

        String id = "", pw = "", fn = "", ln = "", uAdd = "";
        int uAge = 0;
        username.setText(user);
        Cursor c  = db.getUserDetails(user);
        while(c.moveToNext()){
            id = c.getString(0);
            pw = c.getString(1);
            fn = c.getString(2);
            ln = c.getString(3);
            uAdd = c.getString(4);
            uAge = c.getInt(5);
        }

        username.setText(id);
        password.setText(pw);
        fName.setText(fn);
        lName.setText(ln);
        add.setText(uAdd);
        age.setText(Integer.toString(uAge));

        Button updateUser = findViewById(R.id.btnAdminUpdateUser);

        updateUser.setOnClickListener(new View.OnClickListener() {
            boolean isUpdated;
            @Override
            public void onClick(View view) {
                  isUpdated = db.updateUser(username.getText().toString(), password.getText().toString(),
                        fName.getText().toString(), lName.getText().toString(),
                        add.getText().toString(), Integer.parseInt(age.getText().toString()));
                if(isUpdated) {
                    Toast.makeText(AdminUpdateUser2.this,
                            Html.fromHtml("User info updated"),
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AdminUpdateUser2.this, "Couldn't update the user info.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

