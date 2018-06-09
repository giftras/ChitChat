package com.example.gift.chitchatapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText = (EditText) findViewById(R.id.userEmail);
        passText = (EditText) findViewById(R.id.userPass);
        //connect to email authentication
        mAuth = FirebaseAuth.getInstance();
        //database that which users is child element
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    public void loginButtonClicked(View view) {
        String email = emailText.getText().toString().trim();
        String pass = passText.getText().toString().trim();
        final Context now = this;
        // user need to fill all value
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_LONG).show();
        } else {
            //Firebase SignIn function
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(now, "log in success", Toast.LENGTH_LONG).show();
                        checkUserExists();
                    } else {
                        Toast.makeText(now, "Incorrect email or password", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }
        public void checkUserExists() {
            final String user_id = mAuth.getCurrentUser().getUid();
            //if found uid of the database then give the access to MainActivity.class
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(user_id)) {
                        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(loginIntent);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
    }
    //if clicked register button will lead to RegisterActivity.class
    public void registerButtonClicked(View view){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }


}
