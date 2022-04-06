package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Message extends AppCompatActivity {

    Database db;
    String userId;
    Cursor c;
   // String msgTitle, msgDate, msgSenderId, msgContent;
    //int msgId;
    ArrayList<String> msgTitlesList, msgDatesList, msgSenderIdList, msgContentList;
    ArrayList<Integer> msgIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        msgTitlesList = new ArrayList<String>();
        msgDatesList = new ArrayList<String>();
        msgSenderIdList = new ArrayList<String>();
        msgIdList = new ArrayList<Integer>();
        msgContentList=new ArrayList<String>();
        db = new Database(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getString("userId", "NA");
        c = db.viewMessages(userId);

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                int msgId = c.getInt(0);
                msgIdList.add(msgId);
                String msgSenderId = c.getString(1);
                msgSenderIdList.add(msgSenderId);
                String msgDate = c.getString(4);
                msgDatesList.add(msgDate);
                String msgContent=c.getString(5);
                msgContentList.add(msgContent);
                String msgTitle = c.getString(6);
                msgTitlesList.add(msgTitle);
            }

            Collections.reverse(msgDatesList);
            Collections.reverse(msgTitlesList);
            Collections.reverse(msgIdList);
            Collections.reverse(msgSenderIdList);
            Collections.reverse(msgContentList);

            ImageButton go_back = findViewById(R.id.btnBack);
            BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);

            //button event for go back main page
            go_back.setOnClickListener(v -> startActivity(new Intent(Message.this, MainMenu.class)));

            //the bottom menu bar to link the pages
            bottom_menu.setOnItemSelectedListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_add_book) {
                    startActivity(new Intent(Message.this, AddBook.class));
                } else if (menuItem.getItemId() == R.id.menu_update_book) {
                    startActivity(new Intent(Message.this, UpdateBook2.class));
                } else if (menuItem.getItemId() == R.id.menu_borrow_book) {
                    startActivity(new Intent(Message.this, BorrowBook2.class));
                } else if (menuItem.getItemId() == R.id.menu_reading_tracker) {
                    startActivity(new Intent(Message.this, ReadingTracker.class));
                } else if (menuItem.getItemId() == R.id.menu_user_account) {
                    startActivity(new Intent(Message.this, UserAccount.class));
                }
                return true;
            });

            //ListView
            List<HashMap<String, String>> messageList = new ArrayList<>();

            for (int i = 0; i < msgTitlesList.size(); i++) {
                HashMap<String, String> hashmap = new HashMap<>();
                hashmap.put("title", msgTitlesList.get(i));
                hashmap.put("date", msgDatesList.get(i));
                messageList.add(hashmap);
            }

            String[] from = {"title", "date"};
            int[] to = {R.id.txtMessage, R.id.txtMessageDate};

            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), messageList,
                    R.layout.message_list_item, from, to);

            ListView listView = findViewById(R.id.CureentLoanListView);
            listView.setAdapter(adapter);
            //on click item
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    int clickedMsgId = msgIdList.get(position);
                    String clickedMsgSenderId = msgSenderIdList.get(position);
                    String clickedMsgTitle = msgTitlesList.get(position);
                    String clickedMsgDate = msgDatesList.get(position);
                    String clickedMsgContent=msgContentList.get(position);

                    Intent i = new Intent(Message.this, MessageDisplay.class);
                    i.putExtra("msgId", clickedMsgId);
                    i.putExtra("senderId", clickedMsgSenderId);
                    i.putExtra("title", clickedMsgTitle);
                    i.putExtra("date", clickedMsgDate);
                    i.putExtra("content", clickedMsgContent);
                    startActivity(i);
                }
            });
        }
        ImageButton go_back = findViewById(R.id.btnBack);
        go_back.setOnClickListener(v -> startActivity(new Intent(Message.this,MainMenu.class)));
    }
}