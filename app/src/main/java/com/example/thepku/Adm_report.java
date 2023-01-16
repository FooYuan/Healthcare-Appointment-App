package com.example.thepku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Adm_report extends AppCompatActivity {

    ImageButton backbtn;
    EditText start,end;
    String sdate,edate;
    Button generate,print;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener1;
    FirebaseFirestore fStore;
    TextView stunum,appnum,cannum;
    int count1=0;
    int count2=0;
    int count3=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_report);

        final Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        backbtn=findViewById(R.id.imageButton);
        start=findViewById(R.id.start);
        end=findViewById(R.id.end);
        generate=findViewById(R.id.generate);
        stunum=findViewById(R.id.stunum);
        appnum=findViewById(R.id.appnum);
        cannum=findViewById(R.id.cannum);
        print=findViewById(R.id.print);
        fStore = FirebaseFirestore.getInstance();

        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String pass =getIntent().getStringExtra("password");


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Adm_report.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        onDateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                year=year-1900;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date d =new Date(year, month, day);
                String date= formatter.format(d);
                sdate=date;
                start.setText(date);
            }
        };

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Adm_report.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        onDateSetListener1,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                year=year-1900;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date d =new Date(year, month, day);
                String date= formatter.format(d);
                edate=date;
                end.setText(date);
            }
        };

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String startdate=start.getText().toString().trim();
                String enddate=end.getText().toString().trim();

                if (TextUtils.isEmpty(startdate)) {
                    start.setError("This field is Required.");
                    return;
                }

                if (TextUtils.isEmpty(enddate)) {
                    end.setError("This field is Required.");
                    return;
                }

                start.setEnabled(false);
                end.setEnabled(false);
                generate.setEnabled(false);
                //print.setVisibility(View.VISIBLE);

                fStore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                count1++;
                            }
                            stunum.setText(Integer.toString(count1));
                        }
                    }
                });

                fStore.collection("appointments").whereEqualTo("status","Approved")
                        .orderBy("date").startAt(sdate).endAt(edate).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                count2++;
                            }
                            appnum.setText(Integer.toString(count2));
                        }
                    }
                });

                fStore.collection("appointments").whereEqualTo("status","Cancelled")
                        .orderBy("date").startAt(sdate).endAt(edate).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                count3++;
                            }
                            cannum.setText(Integer.toString(count3));
                        }
                    }
                });


            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                screenshot();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adm_report.this, Adm_homepage.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("password",pass);
                startActivity(intent);
                finish();
            }
        });
    }

    private void screenshot(){
        Date date=new Date();
        CharSequence now=android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss",date);
        String filename= Environment.getExternalStorageDirectory()+"/screenshot/"+now+".jpg";
        View root=getWindow().getDecorView();
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap=Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);

        File file=new File(filename);
        file.getParentFile().mkdirs();

        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            Uri uri= Uri.fromFile(file);
            Intent intent= new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri,"image/*");
            startActivity(intent);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}