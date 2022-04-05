package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminMsgUser extends AppCompatActivity {

    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_msg_user);

        EditText userID = findViewById(R.id.editUserId);
        EditText messageText = findViewById(R.id.txtAdminMsg);
        Button messageUser = findViewById(R.id.btnAdminUpdateUser);

//        String uId = ""; ; String msg = "";

//        String user = userID.getText().toString();
//        String msgTxt = messageText.getText().toString();

        String user = userID.getText().toString();
        String msgTxt = messageText.getText().toString();
        String sID = "Admin";

        messageUser.setOnClickListener(new View.OnClickListener() {
            Boolean messageSent;
            @Override
            public void onClick(View view) {

                messageSent = db.adminMessage(user, sID, msgTxt);
            }
        });
    }
}