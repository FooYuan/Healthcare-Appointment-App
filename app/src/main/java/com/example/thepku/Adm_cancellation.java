package com.example.thepku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Adm_cancellation extends AppCompatActivity {

    FirebaseFirestore fStore;
    RecyclerView recyclerView;
    ArrayList<User2> userArrayList;
    MyAdapter2 myAdapter;
    String id1,name1,pass1;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_cancellation);

        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fStore = FirebaseFirestore.getInstance();
        //setOnClickListener();
        userArrayList = new ArrayList<User2>();
        myAdapter = new MyAdapter2(Adm_cancellation.this,userArrayList);
        id1=id;
        name1=name;
        pass1=pass;
        recyclerView.setAdapter(myAdapter);
        backbtn=findViewById(R.id.imageButton);

        EventChangeListener();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adm_cancellation.this, Adm_app.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                startActivity(intent);
                finish();
            }
        });
    }
    private void EventChangeListener() {
        fStore.collection("appointments").whereEqualTo("status","Cancelled")
                .orderBy("date", Query.Direction.DESCENDING).orderBy("time", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error !=null){
                            Toast.makeText(Adm_cancellation.this, "Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc:value.getDocumentChanges()){
                            if (dc.getType()==DocumentChange.Type.ADDED){
                                userArrayList.add(dc.getDocument().toObject(User2.class));
                            }
                            myAdapter.notifyDataSetChanged();
                            //if (progressDialog.isShowing())
                            //progressDialog.dismiss();
                        }
                    }
                });
    }
}