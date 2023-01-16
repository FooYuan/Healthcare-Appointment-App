package com.example.thepku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder1>{

    Context context;
    ArrayList<User1> userArrayList;
    private static MyAdapter1.RecyclerViewClickListener listener;

    public MyAdapter1(Context context, ArrayList<User1> userArrayList, MyAdapter1.RecyclerViewClickListener listener) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyAdapter1.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item1,parent,false);
        return new MyAdapter1.MyViewHolder1(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter1.MyViewHolder1 holder, int position) {

        User1 user = userArrayList.get(position);
        holder.reason.setText(user.reason);
        holder.date.setText(user.date);
        holder.time.setText(user.time);
        holder.matrixnum.setText(user.matrixnum);
        holder.name.setText(user.name);
        holder.phone.setText(user.phone);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v,int position);
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView reason,time,date,name,matrixnum,phone;


        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            reason=itemView.findViewById(R.id.reason);
            date=itemView.findViewById(R.id.appdate);
            time=itemView.findViewById(R.id.apptime);
            name=itemView.findViewById(R.id.stuname);
            matrixnum=itemView.findViewById(R.id.stumatrix);
            phone=itemView.findViewById(R.id.phone);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
