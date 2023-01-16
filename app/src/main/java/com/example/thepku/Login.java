package com.example.thepku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText mid, mpassword;
    Button mbtnlogin;
    TextView mregistertext;
    RadioGroup rgRole;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mid = findViewById(R.id.id);
        mpassword = findViewById(R.id.password);
        mbtnlogin = findViewById(R.id.btnLogin);
        mregistertext = findViewById(R.id.registerText);
        rgRole = findViewById(R.id.rgRole);
        fStore = FirebaseFirestore.getInstance();

        mbtnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String id = mid.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                int checkedId = rgRole.getCheckedRadioButtonId();

                if (TextUtils.isEmpty(id)) {
                    mid.setError("This field is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mpassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mpassword.setError("Password Must be >=6 Characters.");
                    return;
                }

                if (checkedId == -1){
                    // no radio button are checked
                    Toast.makeText(Login.this,"Select Role.",Toast.LENGTH_SHORT).show();
                    return;
                }

                // student login
                if(checkedId==R.id.radioBtnstu) {
                    DocumentReference documentReference = fStore.collection("users").document(id);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot doc= task.getResult();
                                String a = doc.getString("matrixnum");
                                String b = doc.getString("password");
                                String c = doc.getString("name");
                                String d = doc.getString("phone");
                                if (id.equals(a) && password.equals(b)){
                                Toast.makeText(Login.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, Stu_homepage.class);
                                intent.putExtra("matrix",a);
                                intent.putExtra("password",b);
                                intent.putExtra("name",c);
                                intent.putExtra("phone",d);
                                startActivity(intent);
                                finish();
                            }else {
                                    Toast.makeText(Login.this, "Invalid credentials.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }

                //admin login
                if(checkedId==R.id.radioBtnadm) {
                    DocumentReference documentReference = fStore.collection("admin").document(id);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot doc= task.getResult();
                                String a = doc.getString("id");
                                String b = doc.getString("password");
                                String c = doc.getString("name");
                                if (id.equals(a) && password.equals(b)){
                                    Toast.makeText(Login.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Adm_homepage.class);
                                    intent.putExtra("id",a);
                                    intent.putExtra("password",b);
                                    intent.putExtra("name",c);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(Login.this, "Invalid credentials.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        mregistertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}