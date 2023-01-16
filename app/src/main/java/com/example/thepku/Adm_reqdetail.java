package com.example.thepku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Adm_reqdetail extends AppCompatActivity {

    FirebaseFirestore fStore;
    TextView textname,textmatrix,textdate,texttime,textreason,phone;
    Button button2,button3;
    String id;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_reqdetail);

        textname = findViewById(R.id.textname);
        textmatrix = findViewById(R.id.textmatrix);
        textdate = findViewById(R.id.textdate);
        texttime = findViewById(R.id.texttime);
        textreason = findViewById(R.id.textreason);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
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
        String id1 =getIntent().getStringExtra("adminid");

        textname.setText(stuname);
        textmatrix.setText(matrixnumber);
        textdate.setText(date);
        texttime.setText(time);
        textreason.setText(reason1);
        phone.setText(phone1);

        //read document id
        fStore.collection("appointments").whereEqualTo("name",stuname).whereEqualTo("date",date)
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
                Toast.makeText(Adm_reqdetail.this,"Error!" + e.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        //approve button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDate = formatter.format(new Date());
                String content = "Your appointment request on "+date+" "+time+" was approved.";
                Map<String, Object> notification= new HashMap<>();
                notification.put("date_time",currentDate);
                notification.put("content",content);

                DocumentReference documentReference = fStore.collection("appointments").document(id);
                documentReference.update("status","Approved").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        fStore.collection("users").document(matrixnumber).collection("notifications").add(notification)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(Adm_reqdetail.this,"Appointment approved.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Adm_reqdetail.this, Adm_homepage.class);
                                        intent.putExtra("id",id1);
                                        intent.putExtra("name",adminname);
                                        intent.putExtra("password",pass);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Adm_reqdetail.this,"Error!" + e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Adm_reqdetail.this,"Error!" + e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDate = formatter.format(new Date());
                String content = "Your appointment request on "+date+" "+time+" was rejected.";
                Map<String, Object> notification= new HashMap<>();
                notification.put("date_time",currentDate);
                notification.put("content",content);

                fStore.collection("appointments").document(id).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                fStore.collection("users").document(matrixnumber).collection("notifications").add(notification)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(Adm_reqdetail.this,"Appointment rejected.",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Adm_reqdetail.this, Adm_homepage.class);
                                                intent.putExtra("id",id1);
                                                intent.putExtra("name",adminname);
                                                intent.putExtra("password",pass);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Adm_reqdetail.this,"Error!" + e.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Adm_reqdetail.this,"Error!" + e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adm_reqdetail.this, Adm_req.class);
                intent.putExtra("id",id1);
                intent.putExtra("name",adminname);
                intent.putExtra("password",pass);
                startActivity(intent);
                finish();
            }
        });
    }
}