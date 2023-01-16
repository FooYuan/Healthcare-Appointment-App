package com.example.thepku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText muserName,matrix,mphone,mpass1,mpass2;
    Button mbtnRegister;
    TextView mloginText;
    FirebaseFirestore fStore;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        muserName = findViewById(R.id.userName);
        matrix = findViewById(R.id.matrixnum);
        mphone=findViewById(R.id.phone);
        mpass1 = findViewById(R.id.pass1);
        mpass2 = findViewById(R.id.pass2);
        mbtnRegister = findViewById(R.id.btnRegister);
        mloginText = findViewById(R.id.loginText);
        fStore = FirebaseFirestore.getInstance();

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String userName = muserName.getText().toString().trim();
                String matrixnum = matrix.getText().toString().trim();
                String phone = mphone.getText().toString().trim();
                String pass1 = mpass1.getText().toString().trim();
                String pass2 = mpass2.getText().toString().trim();

                if(TextUtils.isEmpty(userName)){
                    muserName.setError("Username is Required.");
                    return;
                }

                if (userName.length()>25){
                    muserName.setError("Username must within 25 characters.");
                    return;
                }

                if(TextUtils.isEmpty(matrixnum)){
                    matrix.setError("Matrix number is Required.");
                    return;
                }

                if (matrixnum.length()<8){
                    matrix.setError("Matrix Number must be >=8 characters.");
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    mphone.setError("Phone number is Required.");
                    return;
                }

                if (phone.length()<10){
                    mphone.setError("Invalid phone number.");
                    return;
                }

                if(TextUtils.isEmpty(pass1)){
                    mpass1.setError("Password is Required.");
                    return;
                }

                if(pass1.length()<6){
                    mpass1.setError("Password Must be >=6 Characters.");
                    return;
                }

                if(TextUtils.isEmpty(pass2)){
                    mpass2.setError("Password is Required.");
                    return;
                }

                if(!pass2.equals(pass1)){
                    mpass2.setError("Password mismatch.");
                    return;
                }

                //verify existing user
                DocumentReference doc = fStore.collection("users").document(matrixnum);
                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc1= task.getResult();
                            String a = doc1.getString("matrixnum");
                            if (matrixnum.equals(a)){
                                Toast.makeText(Register.this,"Existing user.",Toast.LENGTH_SHORT).show();
                            }else{
                                //Register user
                                userid = matrixnum;
                                DocumentReference documentReference = fStore.collection("users").document(userid);
                                Map<String, Object> user = new HashMap<>();
                                user.put("name", userName);
                                user.put("matrixnum", matrixnum);
                                user.put("phone", phone);
                                user.put("password", pass1);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Register.this, "Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });

        mloginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}