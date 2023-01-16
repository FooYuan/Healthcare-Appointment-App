package com.example.thepku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder3>{

    Context context;
    ArrayList<Notification> userArrayList;


    public MyAdapter3(Context context, ArrayList<Notification> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;

    }

    @NonNull
    @Override
    public MyAdapter3.MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.notification,parent,false);
        return new MyAdapter3.MyViewHolder3(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter3.MyViewHolder3 holder, int position) {

        Notification user = userArrayList.get(position);
        holder.date_time.setText(user.date_time);
        holder.content.setText(user.content);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }



    public static class MyViewHolder3 extends RecyclerView.ViewHolder {
        TextView date_time,content;


        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            date_time=itemView.findViewById(R.id.date_time);
            content=itemView.findViewById(R.id.content);

        }

    }
}
