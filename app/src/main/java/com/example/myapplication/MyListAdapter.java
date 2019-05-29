package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private String[] listdata;
    Context context;

    public MyListAdapter(String[] listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public MyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyListAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(listdata[i]);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i1 = new Intent(context,Booking.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i1.putExtra("id",listdata[i]);
                context.startActivity(i1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button button;
        public ViewHolder(View itemView) {
            super(itemView);

            this.textView = (TextView) itemView.findViewById(R.id.train);
            button = (Button)itemView.findViewById(R.id.book);

        }
    }
}
