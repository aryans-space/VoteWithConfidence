package com.example.letsvoterwithconfidence;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapterresult extends RecyclerView.Adapter<myadapterresult.myviewholder> {
    ArrayList<Modalresult> datalist;
    String a1,a2,a3,a4;

    public myadapterresult(ArrayList<Modalresult> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleresult,parent,false);
      return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.t1v.setText(a1+" : "+datalist.get(position).getName());
        holder.t2v.setText(a2+" : "+datalist.get(position).getPartyname());
        holder.t3v.setText(a3+" : "+datalist.get(position).getVotes());
        holder.t4v.setText(a4+" : "+datalist.get(position).getRank());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView t1v,t2v,t3v,t4v;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1v=itemView.findViewById(R.id.t1v);
            t2v=itemView.findViewById(R.id.t2v);
            t3v=itemView.findViewById(R.id.t3v);
            t4v=itemView.findViewById(R.id.t4v);
            a1=t1v.getText().toString();
            a2=t2v.getText().toString();
            a3=t3v.getText().toString();
            a4=t4v.getText().toString();
        }
    }
}
