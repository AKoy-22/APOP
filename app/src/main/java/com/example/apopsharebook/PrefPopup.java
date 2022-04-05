package com.example.apopsharebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrefPopup extends AppCompatActivity {
    ArrayList<String> prefs;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pref_popup);
        prefs = new ArrayList<>();
        db = new Database(this);
        CheckBox chkBx1 = findViewById(R.id.checkBox);
        CheckBox chkBx2 = findViewById(R.id.checkBox2);
        CheckBox chkBx3 = findViewById(R.id.checkBox3);
        CheckBox chkBx4 = findViewById(R.id.checkBox4);
        CheckBox chkBx5 = findViewById(R.id.checkBox5);
        CheckBox chkBx6 = findViewById(R.id.checkBox6);
        Button btnGo = findViewById(R.id.btnGo);

        Intent i = getIntent();
        String user = i.getStringExtra("id");

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chkBx1.isChecked()){
                    prefs.add(chkBx1.getText().toString());
                }
                if(chkBx2.isChecked()){
                    prefs.add(chkBx2.getText().toString());
                }
                if(chkBx3.isChecked()){
                    prefs.add(chkBx3.getText().toString());
                }
                if(chkBx4.isChecked()){
                    prefs.add(chkBx4.getText().toString());
                }
                if(chkBx5.isChecked()){
                    prefs.add(chkBx5.getText().toString());
                }
                if(chkBx6.isChecked()){
                    prefs.add(chkBx6.getText().toString());
                }
//                Intent intent = new Intent(PrefPopup.this, CreateAccount.class);
//                Bundle args = new Bundle();
//                args.putSerializable("ARRAYLIST",(Serializable)prefs);
//                intent.putExtra("BUNDLE",args);
//                startActivity(intent);
                if(!prefs.isEmpty()) {
                    db.addPrefs(prefs, user);
                    startActivity(new Intent(PrefPopup.this,Login.class));
                } else {
                    Toast.makeText(PrefPopup.this, "Select Preferences", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}