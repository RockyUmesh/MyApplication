package com.example.localbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    EditText fullname_i,age_i,email_i,pwd_i;
    private ProgressBar progessBar;

    private TextView banner,registeruser;

    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        registeruser=findViewById(R.id.registerUser);
        registeruser.setOnClickListener(this);

        fullname_i=findViewById(R.id.usernamerua);
        age_i=findViewById(R.id.agerua);
        email_i=findViewById(R.id.emailrua);
        pwd_i=findViewById(R.id.pwdrua);

        progessBar=findViewById(R.id.progressBarrua);
        progessBar.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerUser:
                registeruser(v);
                break;
        }
    }
    private void registeruser(View view){
        String fullname=fullname_i.getText().toString().trim();
        String age=age_i.getText().toString();
        String email=email_i.getText().toString().trim();
        String pwd=pwd_i.getText().toString().trim();
        if(fullname.isEmpty()){
            fullname_i.setError("Full name is required");
            fullname_i.requestFocus();
            return;
        }
        if(age.isEmpty()){
            age_i.setError("Age is required");
            age_i.requestFocus();
            return;
        }
        //int g = Integer.parseInt(age);
        //        if(g>=13 && g<100){
        //            age_i.setError("You are not eligible to use this app");
        //            age_i.requestFocus();
        //            return;
        //        }
        //some error is there
        if(email.isEmpty()){
            email_i.setError("Email is required");
            email_i.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_i.setError("Please provide valid email");
            email_i.requestFocus();
            return;
        }
        if(pwd.isEmpty()){
            pwd_i.setError("Password is required");
            pwd_i.requestFocus();
            return;
        }

        if(pwd.length()<6){
            pwd_i.setError("Min. password length should be 6 characters");
            pwd_i.requestFocus();
            return;
        }

        progessBar.setVisibility(View.VISIBLE);

        //connecting to the firebase authentication

        mAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(fullname,age,email);
                            //FirebaseDatabase.getInstance().getReference("users")
                            //                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            //                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            //                                @Override
                            //                                public void onComplete(@NonNull Task<Void> task) {
                            //                                    if(task.isSuccessful()){
                            //                                        progessBar.setVisibility(View.GONE);
                            //                                        Snackbar.make(view,"Registration Successful!", Snackbar.LENGTH_LONG)
                            //                                                .setAction("View Details", null).show();
                            //                                    }
                            //                                    else {
                            //                                        progessBar.setVisibility(View.VISIBLE);
                            //                                        Snackbar.make(view,"FAILED to registered, TRY AGAIN", Snackbar.LENGTH_LONG)
                            //                                                .setAction("View Details", null).show();
                            //                                    }
                            //                                }
                            //                            });
                            Map<String,Object> userdata = new HashMap<>();
                            userdata.put("Time stamp:", FieldValue.serverTimestamp());
                            userdata.put("User Name:",fullname);
                            userdata.put("User Age:",age);
                            userdata.put("User Email id:",email);
                            userdata.put("User Password",pwd);
                            db.collection("User Data")
                                    .document()
                                    .set(userdata)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progessBar.setVisibility(View.GONE);
                                            Snackbar.make(view,"Registration Successful!", Snackbar.LENGTH_LONG)
                                                    .setAction("View Details", null).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progessBar.setVisibility(View.VISIBLE);
                                            Snackbar.make(view,"Registration Successful!", Snackbar.LENGTH_LONG)
                                                    .setAction("View Details", null).show();
                                        }
                                    });
                        }else {
                            progessBar.setVisibility(View.VISIBLE);
                            Snackbar.make(view,"FAILED to registered, TRY AGAIN", Snackbar.LENGTH_LONG)
                                    .setAction("View Details", null).show();
                        }
                    }
                });
    }
}