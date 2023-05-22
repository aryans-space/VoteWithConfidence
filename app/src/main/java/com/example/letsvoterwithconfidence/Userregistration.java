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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Userregistration extends AppCompatActivity {
    EditText mfullname,mpassword,memail,mphonenumber,mage;
    Button registerbtn;
    ProgressBar pbr;
    FirebaseAuth fauth;
    TextView mlogin,madmin;
    FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregistration);
        mfullname=findViewById(R.id.fullname);
        mpassword=findViewById(R.id.password);
        memail=findViewById(R.id.email);
        mphonenumber=findViewById(R.id.phonenumber);
        registerbtn=findViewById(R.id.btn_register);
        pbr =findViewById(R.id.progressBar);
        mlogin=findViewById(R.id.loginnow);
        madmin=findViewById(R.id.admin);
        mage=findViewById(R.id.age);
        fauth=FirebaseAuth.getInstance();
        final String[] userID = new String[1];
        fstore=FirebaseFirestore.getInstance();
        if(fauth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                String username=mfullname.getText().toString().trim();
                String phonenumber=mphonenumber.getText().toString().trim();
                int age=Integer.parseInt(mage.getText().toString().trim());
                String vote="1";
                if(age<18){
                    mage.setError("You are not eligible to vote");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is required");
                    return;
                }
                else{
                    fstore.collection("admin").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Step 4: Check if the field value exists in the document
                                    String fieldValue = document.getString("email"); // Replace "fieldName" with the name of the field you want to check
                                    if (fieldValue != null && fieldValue.equals(email)) {
                                        memail.setError("Admin account already exist please login using it.");
                                        return;
                                    }
                                }
                            } else {
                                Log.d("TAG", "Error getting documents: " + task.getException());
                            }
                        }
                    });
                }


                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is required");
                    return;
                }


                if(password.length()<6){
                    mpassword.setError("Password must be atleast 6 characters long");
                    return;
                }

                pbr.setVisibility(View.VISIBLE);




                fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Userregistration.this,"User registered successfully", Toast.LENGTH_SHORT).show();
                            userID[0] =fauth.getCurrentUser().getUid();
                            DocumentReference documentReference=fstore.collection("users").document(userID[0]);
                            Map<String,Object> user=new HashMap<>();
                            user.put("fullname",username);
                            user.put("email",email);
                            user.put("phone",phonenumber);
                            user.put("vote",vote);
                            user.put("age",age);
                            user.put("votingstatus","off");
                            user.put("role","user");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"on success: user profile is created for"+userID[0]);
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(Userregistration.this, Useractivity.class);
                                    startActivity(intent);
                                }
                            });

                        }
                        else{
                            Toast.makeText(Userregistration.this,"Error occured"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pbr.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Userlogin.class));
            }
        });
        madmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Adminlogin.class));
            }
        });

    }

}