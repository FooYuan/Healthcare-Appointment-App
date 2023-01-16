package com.example.thepku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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

public class Adm_req extends AppCompatActivity {

    FirebaseFirestore fStore;
    RecyclerView recyclerView;
    ArrayList<User1> userArrayList;
    MyAdapter1 myAdapter;
    //ProgressDialog progressDialog;
    String id1,name1,pass1;
    MyAdapter1.RecyclerViewClickListener listener;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_req);

        //progressDialog=new ProgressDialog(this);
        //progressDialog.setCancelable(false);
        //progressDialog.setMessage("Fetching Data...");
        //progressDialog.show();

        backbtn=findViewById(R.id.imageButton);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");
        fStore = FirebaseFirestore.getInstance();
        setOnClickListener();
        userArrayList = new ArrayList<User1>();
        myAdapter = new MyAdapter1(Adm_req.this,userArrayList,listener);
        id1=id;
        name1=name;
        pass1=pass;

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adm_req.this, Adm_homepage.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setOnClickListener() {
        listener=new MyAdapter1.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(Adm_req.this,Adm_reqdetail.class);
                intent.putExtra("adminid",id1);
                intent.putExtra("adminname",name1);
                intent.putExtra("adminpassword",pass1);

                intent.putExtra("stuname",userArrayList.get(position).getName());
                intent.putExtra("matrix",userArrayList.get(position).getMatrixnum());
                intent.putExtra("date",userArrayList.get(position).getDate());
                intent.putExtra("time",userArrayList.get(position).getTime());
                intent.putExtra("reason",userArrayList.get(position).getReason());
                intent.putExtra("phone",userArrayList.get(position).getPhone());
                startActivity(intent);
            }
        };
    }
    private void EventChangeListener() {
        fStore.collection("appointments").whereEqualTo("status","Pending")
                .orderBy("date", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error !=null){
                            //if (progressDialog.isShowing())
                                //progressDialog.dismiss();
                            Toast.makeText(Adm_req.this, "Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc:value.getDocumentChanges()){
                            if (dc.getType()==DocumentChange.Type.ADDED){
                                userArrayList.add(dc.getDocument().toObject(User1.class));
                            }
                            myAdapter.notifyDataSetChanged();
                            //if (progressDialog.isShowing())
                                //progressDialog.dismiss();
                        }
                    }
                });
    }
}