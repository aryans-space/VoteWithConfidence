package com.example.letsvoterwithconfidence;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Closevotinglines extends AppCompatActivity {

    Switch aSwitch;
    TextView t12;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closevotinglines);
        aSwitch=findViewById(R.id.switch2);
        t12=findViewById(R.id.tc2);
        db=FirebaseFirestore.getInstance();
        DocumentReference documentReference=db.collection("switch").document("votingstatus");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String fieldValue = document.getString("status");
                        if(fieldValue.equals("on")){
                            t12.setText("Votings lines are open");
                            aSwitch.setChecked(true);
                        }
                        else{
                            t12.setText("Votings lines are closed");
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
                    t12.setText("Votings lines are open");
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference collectionRef = db.collection("users");
                    collectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot querySnapshot) {
                            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                // Update the field in each document
                                String documentId = documentSnapshot.getId();
                                DocumentReference documentRef = collectionRef.document(documentId);
                                documentRef.update("votingstatus", "on");

                            }
                        }
                    }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference documentReference = db.collection("switch").document("votingstatus");
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("status", "on");
                            documentReference.update(updates);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the error
                        }
                    });
                }
                else{
                    t12.setText("Votings lines are closed");
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference collectionRef = db.collection("users");
                    collectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot querySnapshot) {
                            for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                                // Update the field in each document
                                String documentId = documentSnapshot.getId();
                                DocumentReference documentRef = collectionRef.document(documentId);
                                documentRef.update("votingstatus", "off");
                            }
                        }
                    }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference documentReference = db.collection("switch").document("votingstatus");
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("status", "off");
                            documentReference.update(updates);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the error
                        }
                    });
                }
            }
        });
    }
}