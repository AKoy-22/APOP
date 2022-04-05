package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        db = new Database(this);

        //
        Button btnLogin = findViewById(R.id.btnLogin);
        Button signUp = findViewById(R.id.btnSignUp);
        try {
//            db.addUser();
//            db.manuallyAddPref();
//            db.addAdmin();
        }
        catch (Exception e){
            e.getStackTrace();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,Login.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,CreateAccount.class));
            }
        });
    }
}

//----------------TO DOs---------------------//
//1. Change search location to spinner (Akiko, Prabhjit)
//2. Format Date for Message (Akiko)
//3. Implement try catch in several places (All)
//4. Message content display --> create xml (Akiko/Oprah)
//5. Improve appearance of BorrowBookDetails xml (Oprah)
//6. Put back the bottom menu bar for BorrowBook xml (Akiko)
//7. Reading Tracker (Prabhjit)
//8. Current Loans (Akiko)
//9. Request history
//10.Reading history
//11. Admin login (Puru)
//12. Search bar
//13. User Account Edit
//14. Sign out
//