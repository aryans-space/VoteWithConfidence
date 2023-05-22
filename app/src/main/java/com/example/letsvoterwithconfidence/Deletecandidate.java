package com.example.letsvoterwithconfidence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Deletecandidate extends AppCompatActivity {
    EditText memail;
    Button delete;
    TextView admindash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletecandidate);
        delete=findViewById(R.id.deletebutton);
        admindash=findViewById(R.id.admindashboard);
        memail=findViewById(R.id.email);

        admindash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Adminactivity.class));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=memail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is required");
                    return;
                }
                // Create an AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(Deletecandidate.this);
                builder.setTitle("Delete Candidate")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Handle positive button click

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                DocumentReference documentRef = db.collection("candidates").document(email);
                                documentRef.delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Deletecandidate.this,"Candidate record delete successfully",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(),Adminactivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle any errors that occurred while deleting the document
                                                Toast.makeText(Deletecandidate.this,"Error occured"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(),Adminactivity.class));
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
        });

    }
}