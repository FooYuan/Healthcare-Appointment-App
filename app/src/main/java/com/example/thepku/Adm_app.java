package com.example.thepku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class Adm_app extends AppCompatActivity {

    FirebaseFirestore fStore;
    String formattedDate;
    RecyclerView recyclerView;
    ArrayList<User1> userArrayList;
    MyAdapter1 myAdapter;
    String id1,name1,pass1;
    MyAdapter1.RecyclerViewClickListener listener;
    TextView text,cancel;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_app);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        text=findViewById(R.id.text);
        cancel=findViewById(R.id.cancel);
        backbtn=findViewById(R.id.imageButton);

        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");
        id1=id;
        name1=name;
        pass1=pass;

        fStore = FirebaseFirestore.getInstance();
        setOnClickListener();
        userArrayList = new ArrayList<User1>();
        myAdapter = new MyAdapter1(Adm_app.this,userArrayList,listener);
        recyclerView.setAdapter(myAdapter);

        /* starts DATE */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        /* ends DATE */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, +1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime()).endDate(endDate.getTime()).build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = formatter.format(date);
                text.setVisibility(View.GONE);
                userArrayList.clear();
                myAdapter.notifyDataSetChanged();
                EventChangeListener();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adm_app.this, Adm_cancellation.class);
                intent.putExtra("id",id1);
                intent.putExtra("password",pass1);
                intent.putExtra("name",name1);
                startActivity(intent);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adm_app.this, Adm_homepage.class);
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
                Intent intent=new Intent(Adm_app.this,Adm_appdetail.class);
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
        fStore.collection("appointments").whereEqualTo("date",formattedDate).whereEqualTo("status","Approved")
                .orderBy("time", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error !=null){
                            //if (progressDialog.isShowing())
                            //progressDialog.dismiss();
                            Toast.makeText(Adm_app.this, "Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc:value.getDocumentChanges()){
                            if (dc.getType()==DocumentChange.Type.ADDED){
                                userArrayList.add(dc.getDocument().toObject(User1.class));
                            }
                            myAdapter.notifyDataSetChanged();
                            if (userArrayList.isEmpty()){
                                text.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

}