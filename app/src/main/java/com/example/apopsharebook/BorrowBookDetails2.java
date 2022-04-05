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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class BorrowBookDetails2 extends AppCompatActivity {
    String title, author, genre, pub, pubYear, owner, status, senderId;
    int bookId, price;
    Database db;
    boolean success,success2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_book_details2);

        ImageButton go_back = findViewById(R.id.btnBack);
        ImageButton btnMessageIcon = findViewById(R.id.btnMessageIcon);
        TextView txtTitle=findViewById(R.id.txtBDetailTitle);
        TextView txtAuthor=findViewById(R.id.txtBDetailAuthor);
        TextView txtGenre=findViewById(R.id.txtBDetailGenre);
        TextView txtPub=findViewById(R.id.txtBDetailPublisher);
        TextView txtPubYear=findViewById(R.id.txtBDetailPubYear);
        TextView txtOwner=findViewById(R.id.txtBDetailOwner);
        TextView txtStatus=findViewById(R.id.txtBDetailStatus);
        TextView txtPrice=findViewById(R.id.txtBPrice);
        Button btnBorrow=findViewById(R.id.btnBorrow);
        Button btnGiveAway=findViewById(R.id.btnBDetailGiveAway);
        Button btnMessage=findViewById(R.id.btnMessage);
        Button btnSend=findViewById(R.id.btnSubmitMsg);
        EditText writeMsg=findViewById(R.id.edTxtWriteMsg);
        BottomNavigationView bottom_menu;


        Intent i= getIntent();
        if (i != null) {
            title=getIntent().getStringExtra("title");
            txtTitle.setText(title);
            author=getIntent().getStringExtra("author");
            txtAuthor.setText(author);
            genre=getIntent().getStringExtra("genre");
            txtGenre.setText(genre);
            pub=getIntent().getStringExtra("pub");
            txtPub.setText(pub);
            pubYear=getIntent().getStringExtra("pubYear");
            txtPubYear.setText(pubYear);
            owner=getIntent().getStringExtra("owner");
            txtOwner.setText(owner);
            status=getIntent().getStringExtra("status");
            txtStatus.setText(status);
            price=getIntent().getIntExtra("price",0);
            txtPrice.setText("$"+price);
            bookId=getIntent().getIntExtra("bookId",0);

            if(status.equals("Share") || status.equals("on loan")){
                btnBorrow.setVisibility(View.VISIBLE);
            }
            else if(status.equals("Give-away")){
                btnGiveAway.setVisibility(View.VISIBLE);
            }


        }
        //Borrow option is chosen, borrow request message sent automatically to the owner
        btnBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE,30);
                Date retDate = cal.getTime();

                Random r = new Random();
                int low = 1;
                int high = 2;
                int price = r.nextInt(high-low) + low;

                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(BorrowBookDetails2.this);
                senderId=sharedPreferences.getString("userId","NA");
                String borrowerId = sharedPreferences.getString("userId","NA");

                Timestamp t = new Timestamp(System.currentTimeMillis());
                Date d=new Date(t.getTime());
                String StartDate=d.toString();
                String returnDate = retDate.toString();

                String request="Borrow request has been sent from";
                String type="Borrow Request";

                db=new Database(BorrowBookDetails2.this);
                success=db.sendMessage(owner, senderId,StartDate, bookId ,request,type);

                if(success){
                    Toast.makeText(BorrowBookDetails2.this,"Borrow Request has been sent.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(BorrowBookDetails2.this,"Message not sent.Please try again.", Toast.LENGTH_LONG).show();
                }
                success2 = db.addToLoanTable(bookId,borrowerId,StartDate,returnDate,Integer.toString(price));
            }
        });

        //Give-Away option is chosen, message sent automatically to the owner
        btnGiveAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(BorrowBookDetails2.this);
                senderId=sharedPreferences.getString("userId","NA");



                Timestamp t = new Timestamp(System.currentTimeMillis());
                Date d=new Date(t.getTime());
                String date=d.toString();
                String content=senderId+" wants to have your book titled :"+title;
                String type="Give-Away Request";
                db=new Database(BorrowBookDetails2.this);
                success=db.sendMessage(owner, senderId, date, bookId ,content, type);

                if(success){
                    Toast.makeText(BorrowBookDetails2.this,"Give-Away Request has been sent.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(BorrowBookDetails2.this,"Message not sent.Please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });

     //Send Personalized Message option is chosen. Clicking  button enables user to send personalized message
      btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeMsg.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.VISIBLE);
              //  btnBorrow.setVisibility(View.GONE);
               // btnGiveAway.setVisibility(View.GONE);
            }
        });
      //method to submit personalized message
      btnSend.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(BorrowBookDetails2.this);
              senderId=sharedPreferences.getString("userId","NA");

              String msgContent=writeMsg.getText().toString();
              Timestamp t = new Timestamp(System.currentTimeMillis());
              Date d=new Date(t.getTime());
              String date=d.toString();
              String type="Personalized Message";
              db=new Database(BorrowBookDetails2.this);
              success=db.sendMessage(owner, senderId, date, bookId ,msgContent,type);
              if(success){
                  Toast.makeText(BorrowBookDetails2.this,"Personalized message has been sent.", Toast.LENGTH_LONG).show();
              }
              else{
                  Toast.makeText(BorrowBookDetails2.this,"Message not sent.Please try again.", Toast.LENGTH_LONG).show();
              }
          }
      });
        //button event for go back main page
        go_back.setOnClickListener(v -> startActivity(new Intent(BorrowBookDetails2.this,MainMenu.class)));
        btnMessageIcon.setOnClickListener(v -> startActivity(new Intent(BorrowBookDetails2.this,Message.class)));

        //the bottom menu bar to link the pages
        bottom_menu=findViewById(R.id.bottom_menu);
        bottom_menu.setOnItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.menu_add_book) {
                startActivity(new Intent(BorrowBookDetails2.this,AddBook.class));
            } else if(menuItem.getItemId() == R.id.menu_update_book) {
                startActivity(new Intent(BorrowBookDetails2.this,UpdateBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_borrow_book) {
                startActivity(new Intent(BorrowBookDetails2.this,BorrowBook2.class));
            } else if(menuItem.getItemId() == R.id.menu_reading_tracker) {
                startActivity(new Intent(BorrowBookDetails2.this,ReadingTracker.class));
            } else if(menuItem.getItemId() == R.id.menu_user_account) {
                startActivity(new Intent(BorrowBookDetails2.this,UserAccount.class));
            }
            return true;
        });
    }
}