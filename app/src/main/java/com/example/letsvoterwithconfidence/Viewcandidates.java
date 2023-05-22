package com.example.letsvoterwithconfidence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Viewcandidates extends AppCompatActivity {
    RecyclerView recview;
    ArrayList<ModalView> datalist;
    FirebaseFirestore db;
    myadapterview adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcandidates);
        db=FirebaseFirestore.getInstance();
        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new myadapterview(datalist);
        recview.setAdapter(adapter);

        db.collection("candidates").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //brings all documents in this list
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    ModalView obj=new ModalView(d.getString("fullname"),d.getString("partyname"),d.getString("email"));
                    datalist.add(obj);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}