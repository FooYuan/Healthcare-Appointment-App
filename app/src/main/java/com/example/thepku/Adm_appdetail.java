package com.example.thepku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Adm_appdetail extends AppCompatActivity {

    FirebaseFirestore fStore;
    TextView textname,textmatrix,textdate,texttime,textreason,phone;
    Button button1;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_appdetail);

        textname = findViewById(R.id.textname);
        textmatrix = findViewById(R.id.textmatrix);
        textdate = findViewById(R.id.textdate);
        texttime = findViewById(R.id.texttime);
        textreason = findViewById(R.id.textreason);
        button1 = findViewById(R.id.button1);
        phone = findViewById(R.id.phone);
        backbtn=findViewById(R.id.imageButton);

        fStore = FirebaseFirestore.getInstance();

        String matrixnumber = getIntent().getStringExtra("matrix");
        String stuname = getIntent().getStringExtra("stuname");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String reason1 =getIntent().getStringExtra("reason");
        String phone1 =getIntent().getStringExtra("phone");
        String pass =getIntent().getStringExtra("adminpassword");
        String adminname =getIntent().getStringExtra("adminname");
        String id =getIntent().getStringExtra("adminid");

        textname.setText(stuname);
        textmatrix.setText(matrixnumber);
        textdate.setText(date);
        texttime.setText(time);
        textreason.setText(reason1);
        phone.setText(phone1);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(new Date());
        int a=Integer.parseInt(date.substring(8));
        int b=Integer.parseInt(currentDate.substring(8));
        int c=Integer.parseInt(date.substring(5,7));
        int d=Integer.parseInt(currentDate.substring(5,7));
        if (c<d){
            button1.setVisibility(View.GONE);
        }else if(c==d){
            if(a < b) {
                button1.setVisibility(View.GONE);
            }}

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Adm_appdetail.this,Adm_reschedule.class);
                intent.putExtra("adminid",id);
                intent.putExtra("adminname",adminname);
                intent.putExtra("adminpassword",pass);

                intent.putExtra("stuname",stuname);
                intent.putExtra("matrix",matrixnumber);
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                intent.putExtra("reason",reason1);
                intent.putExtra("phone",phone1);
                startActivity(intent);

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adm_appdetail.this, Adm_app.class);
                intent.putExtra("id",id);
                intent.putExtra("name",adminname);
                intent.putExtra("password",pass);
                startActivity(intent);
                finish();
            }
        });
    }
}