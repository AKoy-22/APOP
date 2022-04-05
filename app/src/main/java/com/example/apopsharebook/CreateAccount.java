package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {

    Database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        //-------Create account------

        EditText username = findViewById(R.id.editUserId);
        EditText password = findViewById(R.id.editUserPw);
        EditText repassword = findViewById(R.id.editUserRePw);
        EditText fName = findViewById(R.id.editUserFname);
        EditText lName = findViewById(R.id.editUserLname);
        EditText add = findViewById(R.id.editUserAdr);
        EditText age = findViewById(R.id.editUserAge);
        TextView txtWel = findViewById(R.id.txtWelcome);
//        Button btnPef = findViewById(R.id.btnPref);

        Button register = findViewById(R.id.btnRegister);

        DB = new Database(this);


//        btnPef.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(CreateAccount.this,PrefPopup.class));
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String firstName = fName.getText().toString();
                String lastName = lName.getText().toString();
                String address = add.getText().toString();
                int userAge = 0;

                try {
                     userAge = Integer.parseInt(age.getText().toString());
                }
                catch (Exception e){
                    e.getMessage();
                }
                if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())  || TextUtils.isEmpty(repassword.getText().toString()) || TextUtils.isEmpty(fName.getText().toString()) || TextUtils.isEmpty(lName.getText().toString()) || TextUtils.isEmpty(add.getText().toString()) || TextUtils.isEmpty(age.getText().toString())) {
                    Toast.makeText(CreateAccount.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    if(TextUtils.isEmpty(username.getText().toString())){
                        username.setError("Please enter user name");
                    }
                    if(TextUtils.isEmpty(password.getText().toString())){
                        password.setError("Please enter password");
                    }
                    if(TextUtils.isEmpty(fName.getText().toString())){
                        fName.setError("Please enter First Name");
                    }
                    if(TextUtils.isEmpty(lName.getText().toString())){
                        lName.setError("Please enter Last Name");
                    }
                    if(TextUtils.isEmpty(add.getText().toString())){
                        add.setError("Please enter the age");
                    }
                    if(TextUtils.isEmpty(age.getText().toString())){
                        age.setError("Please enter age");
                    }
                }
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.checkUsername(user);
                        if (!checkuser){
                            Boolean insert = DB.insertData(user, pass, firstName, lastName, address, userAge);
                            if(insert) {
                                Toast.makeText(CreateAccount.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),PrefPopup.class);
                                intent.putExtra("id",user);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(CreateAccount.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(CreateAccount.this, "User already exists! Please sign in!   ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(CreateAccount.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}