package com.example.letsvoterwithconfidence;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapterview extends RecyclerView.Adapter<myadapterview.myviewholder> {
    ArrayList<ModalView> datalist;
    String a1,a2,a3;
    public myadapterview(ArrayList<ModalView> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowview,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(a1+" : "+datalist.get(position).getName());
        holder.t2.setText(a2+" : "+datalist.get(position).getParty());
        holder.t3.setText(a3+" : "+datalist.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            a1=t1.getText().toString();
            a2=t2.getText().toString();
            a3=t3.getText().toString();
        }
    }

}
