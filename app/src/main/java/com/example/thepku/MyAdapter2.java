package com.example.thepku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2>{

    Context context;
    ArrayList<User2> userArrayList;

    public MyAdapter2(Context context, ArrayList<User2> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter2.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        return new MyAdapter2.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.MyViewHolder2 holder, int position) {

        User2 user = userArrayList.get(position);
        holder.reason.setText(user.reason);
        holder.date.setText(user.date);
        holder.time.setText(user.time);
        holder.matrixnum.setText(user.matrixnum);
        holder.name.setText(user.name);
        holder.cancel_reason.setText(user.cancel_reason);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


    public static class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView reason,time,date,name,matrixnum,cancel_reason;


        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            reason=itemView.findViewById(R.id.bookreason);
            date=itemView.findViewById(R.id.appdate);
            time=itemView.findViewById(R.id.apptime);
            name=itemView.findViewById(R.id.stuname);
            matrixnum=itemView.findViewById(R.id.stumatrix);
            cancel_reason=itemView.findViewById(R.id.cancelreason);

        }
    }
}
