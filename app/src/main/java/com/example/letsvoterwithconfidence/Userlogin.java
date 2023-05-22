package com.example.letsvoterwithconfidence;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Userlogin extends AppCompatActivity {
    EditText memail,mpassword;
    ProgressBar pbr;
    Button mloginbtn;
    TextView mregister,forgots,madmin;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        memail=findViewById(R.id.email);
        mpassword=findViewById(R.id.password);
        pbr=findViewById(R.id.progressBar);
        mregister=findViewById(R.id.registernow);
        mloginbtn=findViewById(R.id.btn_login);
        madmin=findViewById(R.id.admin);
        fauth=FirebaseAuth.getInstance();
        forgots=findViewById(R.id.forgot);
        fstore=FirebaseFirestore.getInstance();

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            int correct=0;
            @Override
            public void onClick(View v) {
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is required");
                    correct=1;
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is required");
                    correct=1;
                    return;
                }

                if(password.length()<6){
                    mpassword.setError("Password must be atleast 6 characters long");
                    correct=1;
                    return;
                }
                pbr.setVisibility(View.VISIBLE);
                if(correct==0) {
                    Log.d(TAG, "Document exists!");
                    fauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String  userId=fauth.getCurrentUser().getUid();
                                DocumentReference docRef = fstore.collection("users").document(userId);


                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Toast.makeText(Userlogin.this,"User Logged in successfully", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(Userlogin.this, Useractivity.class);
                                                startActivity(intent);


                                                // Document exists in the collection
                                                Log.d("TAG", "Document exists");
                                            } else {
                                                // Document does not exist in the collection
                                                Toast.makeText(Userlogin.this,"Admin Logged in successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(),Adminactivity.class));
                                                Log.d("TAG", "Document does not exist");
                                            }
                                        } else {
                                            Log.d("TAG", "Failed with: ", task.getException());
                                        }
                                    }
                                });

                            }
                            else{
                                Toast.makeText(Userlogin.this,"Error occured"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                pbr.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else{

                }

            }
        });

        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Userregistration.class));
            }
        });
        madmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Adminlogin.class));
            }
        });

        forgots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetmail=new EditText(v.getContext());
                AlertDialog.Builder passwordresetdialog=new AlertDialog.Builder(v.getContext());
                passwordresetdialog.setTitle("Reset Password?");
                passwordresetdialog.setMessage("Enter your email to recieve reset link ");
                passwordresetdialog.setView(resetmail);

                passwordresetdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail=resetmail.getText().toString();
                        fauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Userlogin.this,"Reset link sent to your email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Userlogin.this,"Error Reset Link not sent"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                passwordresetdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordresetdialog.create().show();
            }
        });
    }


}