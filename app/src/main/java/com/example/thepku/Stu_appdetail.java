package com.example.thepku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Stu_appdetail extends AppCompatActivity {

    FirebaseFirestore fStore;
    TextView textname,textmatrix,textdate,texttime,textreason;
    EditText reason;
    Button button1,button2,button3;
    String id;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_appdetail);

        backbtn=findViewById(R.id.imageButton);
        textname = findViewById(R.id.textname);
        textmatrix = findViewById(R.id.textmatrix);
        textdate = findViewById(R.id.textdate);
        texttime = findViewById(R.id.texttime);
        textreason = findViewById(R.id.textreason);
        reason = findViewById(R.id.reason);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        fStore = FirebaseFirestore.getInstance();

        String matrixnumber = getIntent().getStringExtra("matrix");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String reason1 =getIntent().getStringExtra("reason");
        String phone = getIntent().getStringExtra("phone");

        textname.setText(name);
        textmatrix.setText(matrixnumber);
        textdate.setText(date);
        texttime.setText(time);
        textreason.setText(reason1);

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
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
                reason.setVisibility(View.VISIBLE);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                reason.setVisibility(View.GONE);
            }
        });

        //read document id
        fStore.collection("appointments").whereEqualTo("name",name).whereEqualTo("date",date)
                .whereEqualTo("time",time).whereEqualTo("reason",reason1).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                id=document.getId();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Stu_appdetail.this,"Error!" + e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        //Toast.makeText(Stu_appdetail.this,"done!" + id,Toast.LENGTH_SHORT).show();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String appreason=reason.getText().toString().trim();

                if (TextUtils.isEmpty(appreason)) {
                    reason.setError("This field is Required.");
                    return;
                }

                Map<String, Object> change = new HashMap<>();
                change.put("cancel_reason", appreason);
                change.put("status", "Cancelled");
                DocumentReference documentReference = fStore.collection("appointments").document(id);
                documentReference.update(change).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Stu_appdetail.this,"Appointment cancelled.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Stu_appdetail.this, Stu_homepage.class);
                        intent.putExtra("matrix",matrixnumber);
                        intent.putExtra("name",name);
                        intent.putExtra("password",pass);
                        intent.putExtra("phone",phone);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Stu_appdetail.this,"Error!" + e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stu_appdetail.this, Stu_app.class);
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