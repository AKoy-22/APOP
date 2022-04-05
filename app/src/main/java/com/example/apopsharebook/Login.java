package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DB=new Database(this);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        EditText username = findViewById(R.id.inpEmail);
        EditText password = findViewById(R.id.inpPass);
        Button btnLogin = findViewById(R.id.btnRegister);
        TextView out = findViewById(R.id.out);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(Login.this, "Enter all credentials", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if (checkuserpass == true) {
                        String type = DB.getType(user);
//                        out.setText(type);
                        if(type.equals("admin")){
                            Toast.makeText(Login.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,AdminMenu.class));
                        }

                        if(type.equals("user")) {
                            Toast.makeText(Login.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userId", user);
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Login.this, " ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });




//        btnAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Login.this, AdminMenu.class));
//            }
//        });
    }
}