package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Timestamp;
import java.util.Date;

public class MessageDisplay extends AppCompatActivity {
    int msgId;
    String senderId, receivedDate, title, receivedContent;

    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_display);
        db=new Database(this);
        TextView received=findViewById(R.id.txtMsgOrignal);
        TextView header=findViewById(R.id.txtMsgHeader);
        Spinner spinner=findViewById(R.id.spnAcceptOrDec);
        EditText reply=findViewById(R.id.txtAdminMsg);
        Button btnReply=findViewById(R.id.btnSubmitReply);
        ImageButton go_back = findViewById(R.id.btnBack);
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);

       //display message
        Intent i=getIntent();
        if(i!=null){
            msgId=getIntent().getIntExtra("msgId",0);
            senderId=getIntent().getStringExtra("senderId");
            title=getIntent().getStringExtra("title");
            receivedDate=getIntent().getStringExtra("date");
            receivedContent=getIntent().getStringExtra("content");

            header.setText("Title: "+title+"\nFrom: "+senderId+"\n"+receivedDate);
            received.setText(receivedContent);
        }

        //Send Reply
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(MessageDisplay.this);
                String userId=sharedPreferences.getString("userId","NA");

                Timestamp t = new Timestamp(System.currentTimeMillis());
                Date time=new Date(t.getTime());

                String date=time.toString();
                //Spinner choices are accept, decline, N/A
                String choice = spinner.getSelectedItem().toString();
                String content="";
                int bookId=db.searchBookIdByMessageId(msgId);
                int pay=db.findPriceByBookId(bookId);
                if(spinner.getSelectedItemPosition()==1){
                    content="Your request has been accepted. Please pay $"+pay;
                    choice=choice+"ed";
                }
                else if(spinner.getSelectedItemPosition()==2){
                    content="Your request has been declined.";
                    choice=choice+"d";
                }
                String personalizedMsg=reply.getText().toString();
                content=content+""+personalizedMsg;

                boolean success=db.sendMessage(senderId, userId, date,  bookId,content, choice);
                if(success){
                    Toast.makeText(MessageDisplay.this,"Reply sent.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MessageDisplay.this,"Message not sent.Please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
      //button event for go back main page
        go_back.setOnClickListener(v -> startActivity(new Intent(MessageDisplay.this, MainMenu.class)));
/*
        //the bottom menu bar to link the pages
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.menu_add_book) {
                startActivity(new Intent(MessageDisplay.this, AddBook.class));
            } else if (menuItem.getItemId() == R.id.menu_update_book) {
                startActivity(new Intent(MessageDisplay.this, UpdateBook2.class));
            } else if (menuItem.getItemId() == R.id.menu_borrow_book) {
                startActivity(new Intent(MessageDisplay.this, BorrowBook2.class));
            } else if (menuItem.getItemId() == R.id.menu_reading_tracker) {
                startActivity(new Intent(MessageDisplay.this, ReadingTracker.class));
            } else if (menuItem.getItemId() == R.id.menu_user_account) {
                startActivity(new Intent(MessageDisplay.this, UserAccount.class));
            }
            return true;
        });*/
    }


}