package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AdminCreateUser extends AppCompatActivity {

	Database DB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_create_user);

		ImageButton btnMessage = findViewById(R.id.btnMessageIcon);
		ImageButton go_back = findViewById(R.id.btnBack);

		EditText username = findViewById(R.id.editUserId);
		EditText password = findViewById(R.id.editUserPw);
		EditText fName = findViewById(R.id.editUserFname);
		EditText lName = findViewById(R.id.editUserLname);
		EditText add = findViewById(R.id.editUserAdr);
		EditText age = findViewById(R.id.editUserAge);

		Button createUser = findViewById(R.id.btnAdminUpdateUser);

		DB = new Database(this);

		createUser.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				String user = username.getText().toString();
				String pass = password.getText().toString();
				String firstName = fName.getText().toString();
				String lastName = lName.getText().toString();
				String address = add.getText().toString();
				int userAge = Integer.parseInt(age.getText().toString());

				if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())  || TextUtils.isEmpty(fName.getText().toString()) || TextUtils.isEmpty(lName.getText().toString()) || TextUtils.isEmpty(add.getText().toString()) || TextUtils.isEmpty(age.getText().toString())) {
					Toast.makeText(AdminCreateUser.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
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
				}else{
					Boolean checkuser = DB.checkUsername(user);
					if (checkuser==false){
						Boolean insert = DB.insertData(user, pass, firstName, lastName, address, userAge);
						if(insert) {
							Toast.makeText(AdminCreateUser.this, "User created successfully", Toast.LENGTH_SHORT).show();
						}
						else{
							Toast.makeText(AdminCreateUser.this, "Registration failed", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(AdminCreateUser.this, "User already exists!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
}