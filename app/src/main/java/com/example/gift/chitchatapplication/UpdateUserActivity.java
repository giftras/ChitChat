package com.example.gift.chitchatapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateUserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUsers;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        name = (EditText) findViewById(R.id.changeusername);
        //to make sure that the user actually login
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() ==  null){
                    startActivity(new Intent(UpdateUserActivity.this,LoginActivity.class));
                }
            }
        };
    }

    public void updateClicked(View view){
        mCurrentUser = mAuth.getCurrentUser();
        final String name_content;
        name_content = name.getText().toString().trim();
        if (!TextUtils.isEmpty(name_content)){
            mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
            mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mDatabaseUsers.child(mCurrentUser.getUid()).child("Name").setValue(name_content);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        startActivity(new Intent(UpdateUserActivity.this,MainActivity.class));
    }
}
