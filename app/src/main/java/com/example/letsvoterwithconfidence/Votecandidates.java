package com.example.letsvoterwithconfidence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Votecandidates extends AppCompatActivity implements SelectInterface{
    RecyclerView recview2;
    ArrayList<ModalVote> datalist;
    FirebaseFirestore db,dbvote,dbreduce;
    myadaptervoting adapter;
    FirebaseAuth fauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votecandidates);
        recview2=(RecyclerView)findViewById(R.id.recview2);
        recview2.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        adapter=new myadaptervoting(datalist,this);
        recview2.setAdapter(adapter);
        fauth=FirebaseAuth.getInstance();
        dbvote=FirebaseFirestore.getInstance();
        dbreduce=FirebaseFirestore.getInstance();

        db.collection("candidates").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){

                    ModalVote obj=new ModalVote(d.getString("fullname"),d.getString("partyname"),d.getString("email"),d.getString("votes"));
                    datalist.add(obj);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onItemClicked(int postion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Votecandidates.this);
        builder.setTitle("Vote Candidate")
                .setMessage("Are you sure with your decision to vote for candidate Name : "+datalist.get(postion).getName()+" from Party : "+datalist.get(postion).getPartyname())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle positive button click

                        DocumentReference documentReference=dbvote.collection("candidates").document(datalist.get(postion).getEmail());
                        int votecount=Integer.parseInt(datalist.get(postion).getVotes());
                        votecount++;
                        Map<String,Object> map=new HashMap<>();
                        map.put("votes",String.valueOf(votecount));
                        documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                String userid=fauth.getUid();
                                DocumentReference docref=dbreduce.collection("users").document(userid);
                                Map<String,Object> map2=new HashMap<>();
                                map2.put("vote","0");

                                docref.update(map2);
                                Toast.makeText(Votecandidates.this,"Voting successfull",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Useractivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Votecandidates.this,"Voting failed",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Useractivity.class));
                            }
                        });
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle negative button click
                        dialog.dismiss();
                    }
                });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();





    }
}