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

public class Stu_app extends AppCompatActivity {

    FirebaseFirestore fStore;
    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    MyAdapter myAdapter;
    //ProgressDialog progressDialog;
    String matrixnumber1,name1,pass1,phone1;
    MyAdapter.RecyclerViewClickListener listener;
    ImageButton backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_app);

        /*progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();*/

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backbtn=findViewById(R.id.imageButton);

        fStore = FirebaseFirestore.getInstance();
        setOnClickListener();
        userArrayList = new ArrayList<User>();
        myAdapter = new MyAdapter(Stu_app.this,userArrayList,listener);
        String matrixnumber = getIntent().getStringExtra("matrix");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");
        String phone = getIntent().getStringExtra("phone");
        matrixnumber1=matrixnumber;
        name1=name;
        pass1=pass;
        phone1=phone;

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stu_app.this, Stu_homepage.class);
                intent.putExtra("matrix",matrixnumber);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setOnClickListener() {
        listener=new MyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(Stu_app.this,Stu_appdetail.class);
                intent.putExtra("matrix",matrixnumber1);
                intent.putExtra("name",name1);
                intent.putExtra("password",pass1);
                intent.putExtra("phone",phone1);
                intent.putExtra("date",userArrayList.get(position).getDate());
                intent.putExtra("time",userArrayList.get(position).getTime());
                intent.putExtra("reason",userArrayList.get(position).getReason());
                startActivity(intent);

            }
        };
    }

    private void EventChangeListener() {
        fStore.collection("appointments").whereEqualTo("name",name1).whereEqualTo("status","Approved")
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error !=null){
                            //if (progressDialog.isShowing())
                                //progressDialog.dismiss();
                            Toast.makeText(Stu_app.this, "Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc:value.getDocumentChanges()){
                            if (dc.getType()==DocumentChange.Type.ADDED){
                                userArrayList.add(dc.getDocument().toObject(User.class));
                            }
                            myAdapter.notifyDataSetChanged();
                            //if (progressDialog.isShowing())
                                //progressDialog.dismiss();
                        }
                    }
                });
    }
}