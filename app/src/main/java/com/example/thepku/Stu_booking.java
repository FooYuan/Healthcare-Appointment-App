package com.example.thepku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class Stu_booking extends AppCompatActivity {
    TextView t1;
    Button btnbook;
    EditText txtreason;
    RadioGroup rgtime;
    RadioButton r1,r2,r3,r4,r5,r6,r7,r8;
    FirebaseFirestore fStore;
    String time;
    String appdate;
    CheckBox checkBox;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_booking);
        t1=(TextView) findViewById(R.id.textView4);
        rgtime=findViewById(R.id.rgTime);
        txtreason=findViewById(R.id.reason);
        btnbook=findViewById(R.id.button);
        checkBox=findViewById(R.id.checkbox);
        fStore = FirebaseFirestore.getInstance();
        r1=findViewById(R.id.radio1);
        r2=findViewById(R.id.radio2);
        r3=findViewById(R.id.radio3);
        r4=findViewById(R.id.radio4);
        r5=findViewById(R.id.radio5);
        r6=findViewById(R.id.radio6);
        r7=findViewById(R.id.radio7);
        r8=findViewById(R.id.radio8);
        backbtn=findViewById(R.id.imageButton);

        String matrixnumber = getIntent().getStringExtra("matrix");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");
        String phone = getIntent().getStringExtra("phone");

        /* starts DATE */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);
        /* ends DATE */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, +6);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime()).endDate(endDate.getTime()).build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                //Instantiating the SimpleDateFormat class
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatter1 = new SimpleDateFormat("EEEEE MMMMM yyyy HH:mm:ss.SSSZ",new Locale("en","UK"));
                //Formatting the obtained date
                String formattedDate = formatter.format(date);
                String weekday = formatter1.format(date);
                t1.setText("Date:"+formattedDate);
                appdate=formattedDate;
                r1.setVisibility(View.VISIBLE);
                r2.setVisibility(View.VISIBLE);
                r3.setVisibility(View.VISIBLE);
                r4.setVisibility(View.VISIBLE);
                r5.setVisibility(View.VISIBLE);
                r6.setVisibility(View.VISIBLE);
                r7.setVisibility(View.VISIBLE);
                r8.setVisibility(View.VISIBLE);

                fStore.collection("appointments").whereEqualTo("date", formattedDate).whereNotEqualTo("status","Cancelled").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        String a=document.getString("time");
                                        if (a.equals("09:00 AM- 09:30 AM")) {
                                            r1.setVisibility(View.GONE);
                                        };
                                        if (a.equals("09:30 AM- 10:00 AM")) {
                                            r2.setVisibility(View.GONE);
                                        };
                                        if (a.equals("10:00 AM- 10:30 AM")) {
                                            r3.setVisibility(View.GONE);
                                        };
                                        if (a.equals("10:30 AM- 11:00 AM")) {
                                            r4.setVisibility(View.GONE);
                                        };
                                        if (a.equals("11:00 AM- 11:30 AM")) {
                                            r5.setVisibility(View.GONE);
                                        };
                                        if (a.equals("11:30 AM- 12:00 PM")) {
                                            r6.setVisibility(View.GONE);
                                        };
                                        if (a.equals("12:00 PM- 12:30 PM")) {
                                            r7.setVisibility(View.GONE);
                                        };
                                        if (a.equals("12:30 PM- 01:00 PM")) {
                                            r8.setVisibility(View.GONE);
                                        };
                                    }
                                }else{
                                    Toast.makeText(Stu_booking.this, "Error!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appreason=txtreason.getText().toString().trim();
                int checkedId = rgtime.getCheckedRadioButtonId();

                if (checkedId == -1){
                    // no radio button are checked
                    Toast.makeText(Stu_booking.this,"Select Time.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(appreason)) {
                    txtreason.setError("This field is Required.");
                    return;
                }

                if(checkBox.isChecked()){
                }else{
                    checkBox.setError("This field is Required.");
                    return;
                }

                if (checkedId==R.id.radio1){
                    time="09:00 AM- 09:30 AM";
                }else if (checkedId==R.id.radio2){
                    time="09:30 AM- 10:00 AM";
                }else if (checkedId==R.id.radio3){
                    time="10:00 AM- 10:30 AM";
                }else if (checkedId==R.id.radio4){
                    time="10:30 AM- 11:00 AM";
                }else if (checkedId==R.id.radio5){
                    time="11:00 AM- 11:30 AM";
                }else if (checkedId==R.id.radio6){
                    time="11:30 AM- 12:00 PM";
                }else if (checkedId==R.id.radio7){
                    time="12:00 PM- 12:30 PM";
                }else if (checkedId==R.id.radio8){
                    time="12:30 PM- 01:00 PM";
                }

                Map<String, Object> appointment= new HashMap<>();
                appointment.put("name",name);
                appointment.put("matrixnum",matrixnumber);
                appointment.put("phone",phone);
                appointment.put("date",appdate);
                appointment.put("time",time);
                appointment.put("reason",appreason);
                appointment.put("status","Pending");

                fStore.collection("appointments").add(appointment).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Stu_booking.this, "Booked Successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Stu_booking.this, Stu_homepage.class);
                        intent.putExtra("matrix",matrixnumber);
                        intent.putExtra("password",pass);
                        intent.putExtra("name",name);
                        intent.putExtra("phone",phone);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Stu_booking.this,"Error!" + e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stu_booking.this, Stu_homepage.class);
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