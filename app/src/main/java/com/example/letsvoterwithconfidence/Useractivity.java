package com.example.letsvoterwithconfidence;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Useractivity extends AppCompatActivity {

    FirebaseAuth fauth;
    Button button,vote,results;
    TextView email,ph,username;
    FirebaseUser user;
    FirebaseFirestore fstore,fstore2;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useractivity);

        fauth=FirebaseAuth.getInstance();
        button=findViewById(R.id.logout);
        email=findViewById(R.id.user_details);
        fstore=FirebaseFirestore.getInstance();
        fstore2=FirebaseFirestore.getInstance();
        user=fauth.getCurrentUser();
        ph=findViewById(R.id.phonenumber);
        username=findViewById(R.id.username);
        if(user==null){
            Intent intent=new Intent(getApplicationContext(),Userlogin.class);
            startActivity(intent);
            finish();
        }
        else{
            userid=user.getUid();
            DocumentReference documentReference2 = fstore2.collection("users").document(userid);
            documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot valueSnapshot, @Nullable FirebaseFirestoreException error) {
                    ph.setText("Ph. No. : "+valueSnapshot.getString("phone"));
                    username.setText("Name : "+valueSnapshot.getString("fullname"));
                }
            });

            email.setText("Email : "+user.getEmail());
        }
        ;

        vote=findViewById(R.id.vote);
        results=findViewById(R.id.results);
        userid=user.getUid();

        DocumentReference documentReference=fstore.collection("users").document(userid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String fieldValue2 = document.getString("votingstatus");
                        String fieldValue = document.getString("vote");
                        if(fieldValue2.equalsIgnoreCase("off") && fieldValue.equals("1")){
                            vote.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(Useractivity.this,"Your voting status is disabled by admin", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else if(fieldValue.equals("1") && fieldValue2.equalsIgnoreCase("on")){
                            vote.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getApplicationContext(),Votecandidates.class));
                                }
                            });

                        }
                        else{
                            vote.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(Useractivity.this,"You have already voted", Toast.LENGTH_SHORT).show();
                                }
                            });

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



        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference=fstore.collection("switch").document("resultstatus");
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String fieldValue = document.getString("status");
                                if(fieldValue.equals("on")){
                                    startActivity(new Intent(getApplicationContext(),Resultsfinal.class));
                                }
                                else{
                                    Toast.makeText(Useractivity.this,"Result not declared yet", Toast.LENGTH_SHORT).show();
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

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fauth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),Chooserole.class);
                finish();
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed () {
        Toast.makeText(Useractivity.this,"Please logout first", Toast.LENGTH_SHORT).show();
    }
}