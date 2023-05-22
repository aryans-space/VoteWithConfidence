package com.example.letsvoterwithconfidence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Resultsfinal extends AppCompatActivity {
    RecyclerView recview;
    ArrayList<Modalresult> datalist;
    FirebaseFirestore db;
    myadapterresult adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultsfinal);
        recview=(RecyclerView)findViewById(R.id.recview3);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        adapter=new myadapterresult(datalist);
        recview.setAdapter(adapter);
        db.collection("candidates").orderBy("votes", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                int count=1;
                for(DocumentSnapshot d:list){
                    Modalresult obj=new Modalresult(d.getString("fullname"),d.getString("partyname"),d.getString("votes"),String.valueOf(count));
                    count++;
                    datalist.add(obj);
                }
                adapter.notifyDataSetChanged();

            }
        });
    }
}