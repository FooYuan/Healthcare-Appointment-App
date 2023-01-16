package com.example.thepku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> userArrayList;
    private static RecyclerViewClickListener listener;

    public MyAdapter(Context context, ArrayList<User> userArrayList, RecyclerViewClickListener listener) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        User user = userArrayList.get(position);
        holder.reason.setText(user.reason);
        holder.date.setText(user.date);
        holder.time.setText(user.time);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v,int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView reason,date,time;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reason=itemView.findViewById(R.id.reason);
            date=itemView.findViewById(R.id.appdate);
            time=itemView.findViewById(R.id.apptime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
