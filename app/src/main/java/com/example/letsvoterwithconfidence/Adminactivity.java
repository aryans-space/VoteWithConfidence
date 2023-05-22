package com.example.letsvoterwithconfidence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Adminactivity extends AppCompatActivity {
    Button bcreate,bdelete,blogout,bviewall,bdeclare,bclose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminactivity);
        bcreate=findViewById(R.id.create);
        bdelete=findViewById(R.id.delete);
        blogout=findViewById(R.id.logout);
        bviewall=findViewById(R.id.viewcandidate);
        bdeclare=findViewById(R.id.declare);
        bclose=findViewById(R.id.close);
        bcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Createrepresentatitve.class));
            }
        });
        bviewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Viewcandidates.class));
            }
        });
        bdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Deletecandidate.class));
            }
        });
        blogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Adminactivity.this, Chooserole.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent); ;

            }
        });
        bclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Closevotinglines.class));
            }
        });
        bdeclare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Declareresult.class));
            }
        });


    }
    @Override
    public void onBackPressed () {
        Toast.makeText(Adminactivity.this,"Please logout first", Toast.LENGTH_SHORT).show();
    }

}