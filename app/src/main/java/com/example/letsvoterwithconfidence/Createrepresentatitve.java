package com.example.letsvoterwithconfidence;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Createrepresentatitve extends AppCompatActivity {
    TextView admindash;
    EditText mname,mparty,memail;
    ProgressBar pbr;
    Button submitbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createrepresentatitve);
        admindash=findViewById(R.id.admindashboard);
        submitbutton=findViewById(R.id.btncandidate);
        mname=findViewById(R.id.name);
        mparty=findViewById(R.id.party);
        memail=findViewById(R.id.email);
        pbr=findViewById(R.id.progressBar);

        admindash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Adminactivity.class));
            }
        });



        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mname.getText().toString().trim();
                String email=memail.getText().toString().trim();
                String party=mparty.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    mname.setError("Name is required");
                    return;
                }
                if(TextUtils.isEmpty(party)){
                    mname.setError("Party name is required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is required");
                    return;
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String,Object> user=new HashMap<>();
                user.put("fullname",name);
                user.put("partyname",party);
                user.put("email",email);
                user.put("votes","0");
                db.collection("candidates").document(email)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
                startActivity(new Intent(getApplicationContext(),Adminactivity.class));
            }
        });








    }
}