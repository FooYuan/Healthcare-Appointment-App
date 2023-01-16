package com.example.thepku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Stu_profile extends AppCompatActivity {
    TextView mname,mmatrix,mphone;
    Button btnpass;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_profile);

        btnpass = findViewById(R.id.button);
        mname = findViewById(R.id.textname);
        mmatrix = findViewById(R.id.textmatrix);
        mphone = findViewById(R.id.phone);
        backbtn=findViewById(R.id.imageButton);

        String matrixnumber = getIntent().getStringExtra("matrix");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");
        String phone = getIntent().getStringExtra("phone");

        mname.setText(name);
        mmatrix.setText(matrixnumber);
        mphone.setText(phone);

        btnpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stu_profile.this, Stu_pass.class);
                intent.putExtra("matrix",matrixnumber);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stu_profile.this, Stu_homepage.class);
                intent.putExtra("matrix",matrixnumber);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }
        });
    }
}