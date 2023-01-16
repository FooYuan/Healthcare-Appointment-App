package com.example.thepku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Stu_notification extends AppCompatActivity {

    FirebaseFirestore fStore;
    ImageButton backbtn;
    RecyclerView recyclerView;
    ArrayList<Notification> userArrayList;
    MyAdapter3 myAdapter;
    String matrixnumber1,name1,pass1,phone1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_notification);

        String matrixnumber = getIntent().getStringExtra("matrix");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");
        String phone = getIntent().getStringExtra("phone");

        backbtn=findViewById(R.id.imageButton);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fStore = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<Notification>();
        myAdapter = new MyAdapter3(Stu_notification.this,userArrayList);
        matrixnumber1=matrixnumber;
        name1=name;
        pass1=pass;
        phone1=phone;
        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stu_notification.this, Stu_homepage.class);
                intent.putExtra("matrix",matrixnumber);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }
        });
    }



    private void EventChangeListener() {
    fStore.collection("users").document(matrixnumber1).collection("notifications")
            .orderBy("date_time", Query.Direction.DESCENDING)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                    if (error !=null){
                        Toast.makeText(Stu_notification.this, "Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (DocumentChange dc:value.getDocumentChanges()){
                        if (dc.getType()==DocumentChange.Type.ADDED){
                            userArrayList.add(dc.getDocument().toObject(Notification.class));
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                }
            });
}
}