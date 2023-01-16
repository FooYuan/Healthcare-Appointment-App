package com.example.thepku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Adm_profile extends AppCompatActivity {
    TextView mname,mmatrix;
    Button btnpass;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_profile);

        btnpass = findViewById(R.id.button);
        mname = findViewById(R.id.textname);
        mmatrix = findViewById(R.id.textmatrix);
        backbtn=findViewById(R.id.imageButton);

        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");

        mname.setText(name);
        mmatrix.setText(id);

        btnpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adm_profile.this, Adm_pass.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                startActivity(intent);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adm_profile.this, Adm_homepage.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                startActivity(intent);
                finish();
            }
        });
    }
}