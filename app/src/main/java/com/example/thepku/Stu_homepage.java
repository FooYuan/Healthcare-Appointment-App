package com.example.thepku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Stu_homepage extends AppCompatActivity {
    Button mbtnApp,mbtnBook,mbtnNoti,mbtnPro,mbtnLogout;
    TextView mname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_homepage);

        mbtnApp = findViewById(R.id.btnApp);
        mbtnBook = findViewById(R.id.btnBook);
        mbtnNoti = findViewById(R.id.btnNoti);
        mbtnPro = findViewById(R.id.btnPro);
        mbtnLogout = findViewById(R.id.btnLogout);
        mname = findViewById(R.id.header2);

        String matrixnumber = getIntent().getStringExtra("matrix");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");
        String phone = getIntent().getStringExtra("phone");

        mname.setText(name);

        mbtnApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stu_homepage.this, Stu_app.class);
                intent.putExtra("matrix",matrixnumber);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });

        mbtnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stu_homepage.this, Stu_booking.class);
                intent.putExtra("matrix",matrixnumber);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });

        mbtnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stu_homepage.this, Stu_notification.class);
                intent.putExtra("matrix",matrixnumber);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });

        mbtnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stu_homepage.this, Stu_profile.class);
                intent.putExtra("matrix",matrixnumber);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });

        mbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
}