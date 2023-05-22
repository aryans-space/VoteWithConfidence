package com.example.letsvoterwithconfidence;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadaptervoting extends RecyclerView.Adapter<myadaptervoting.myviewholder> {
    ArrayList<ModalVote> datalist;
    private final SelectInterface listener;

    public myadaptervoting(ArrayList<ModalVote> datalist,SelectInterface listener) {
        this.datalist = datalist;
        this.listener=listener;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowvoting,parent,false);
        return new myviewholder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        holder.t1c.setText(datalist.get(position).getName());
        holder.t2c.setText(datalist.get(position).getPartyname());



    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView t1c,t2c,t3c;
        public CardView cardView;
        public myviewholder(@NonNull View itemView,SelectInterface listener) {
            super(itemView);
            t1c=itemView.findViewById(R.id.t1c);
            t2c=itemView.findViewById(R.id.t2c);
            t3c=itemView.findViewById(R.id.t3c);
            cardView=itemView.findViewById(R.id.maincotainer2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION)
                            listener.onItemClicked(pos);
                    }
                }
            });
        }
    }
}
