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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Adm_pass extends AppCompatActivity {

    EditText mpass,mpass1,mpass2;
    Button btn;
    FirebaseFirestore fStore;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_pass);

        mpass=findViewById(R.id.pass);
        mpass1=findViewById(R.id.pass1);
        mpass2=findViewById(R.id.pass2);
        btn=findViewById(R.id.btnup);
        fStore = FirebaseFirestore.getInstance();
        backbtn=findViewById(R.id.imageButton);

        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String dbpass =getIntent().getStringExtra("password");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=mpass.getText().toString().trim();
                String pass1=mpass1.getText().toString().trim();
                String pass2=mpass2.getText().toString().trim();

                if(TextUtils.isEmpty(pass)){
                    mpass.setError("This field is Required.");
                    return;
                }

                if(TextUtils.isEmpty(pass1)){
                    mpass1.setError("This field is Required.");
                    return;
                }

                if(TextUtils.isEmpty(pass2)){
                    mpass2.setError("This field is Required.");
                    return;
                }

                if(pass1.length()<6){
                    mpass1.setError("Password Must be >=6 Characters.");
                    return;
                }

                if(!pass.equals(dbpass)){
                    mpass.setError("Wrong password.");
                    return;
                }

                if(!pass2.equals(pass1)){
                    mpass2.setError("Password mismatch.");
                    return;
                }

                DocumentReference documentReference = fStore.collection("admin").document(id);
                documentReference.update("password",pass1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Adm_pass.this,"New password updated.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Adm_pass.this, Adm_homepage.class);
                        intent.putExtra("id",id);
                        intent.putExtra("name",name);
                        intent.putExtra("password",pass1);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Adm_pass.this,"Error!" + e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adm_pass.this, Adm_profile.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("password",dbpass);
                startActivity(intent);
                finish();
            }
        });
    }
}