package com.example.letsvoterwithconfidence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Declareresult extends AppCompatActivity {
    Switch aSwitch;
    TextView t1;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declareresult);
        aSwitch=findViewById(R.id.switch1);
        t1=findViewById(R.id.tc2);
        db=FirebaseFirestore.getInstance();

        DocumentReference documentReference=db.collection("switch").document("resultstatus");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String fieldValue = document.getString("status");
                        if(fieldValue.equals("on")){
                            t1.setText("Votings lines are open");
                            aSwitch.setChecked(true);
                        }
                        else{
                            t1.setText("Votings lines are closed");
                            aSwitch.setChecked(false);

                        }
                        Log.d("TAG", "Field Value: " + fieldValue);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "Error getting document: " + task.getException());
                }
            }
        });


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aSwitch.isChecked()){
                    t1.setText("Result is declared");
                    DocumentReference documentReference=db.collection("switch").document("resultstatus");
                    Map<String,Object> map=new HashMap<>();
                    map.put("status","on");
                    documentReference.update(map);
                }
                else{
                    t1.setText("Result not declared");
                    DocumentReference documentReference=db.collection("switch").document("resultstatus");
                    Map<String,Object> map=new HashMap<>();
                    map.put("status","off");
                    documentReference.update(map);
                }
            }
        });
    }
}