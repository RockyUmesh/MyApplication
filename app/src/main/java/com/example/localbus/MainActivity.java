package com.example.localbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;

public class MainActivity extends AppCompatActivity {

    EditText eoru_ma,pwd_ma;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    String TAG;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        eoru_ma=findViewById(R.id.usrnmla);
        pwd_ma=findViewById(R.id.pwdla);

        progressBar=findViewById(R.id.progressBar_ma);
        progressBar.setVisibility(View.GONE);

    }


    public void btnsigninla(View view) {
        String username=eoru_ma.getText().toString().trim();
        String pwd=pwd_ma.getText().toString().trim();

        if(username.isEmpty()){
            eoru_ma.setError("Email is required");
            eoru_ma.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            eoru_ma.setError("Please provide valid email");
            eoru_ma.requestFocus();
            return;
        }
        if(pwd.isEmpty()){
            pwd_ma.setError("Password is required");
            pwd_ma.requestFocus();
            return;
        }
        if(pwd.length()<6){
            pwd_ma.setError("Min. password length should be 6");
            pwd_ma.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        AuthCredential credential = EmailAuthProvider.getCredential(username, pwd);

        FirebaseUserMetadata metadata = mAuth.getCurrentUser().getMetadata();
        if (metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp()) {

            // The user is new, show them a fancy intro screen!
            mAuth.signInWithEmailAndPassword(username,pwd)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                if(user.isEmailVerified()){
                                    Toast.makeText(getApplicationContext(),"Signing you in!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this,SrchAct.class));
                                }
                                else {
                                    user.sendEmailVerification();
                                    Toast.makeText(getApplicationContext(),"Check email to verify your account!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Failed to login! Please check your credentials or Internet Connection or Sign Up!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
              // This is an existing user, show them a welcome back screen.
              startActivity(new Intent(MainActivity.this,SrchAct.class));
        }
    }


    public void btnfrgtpwdla(View view) {
        startActivity(new Intent(MainActivity.this,ForgotPassword.class));
        finish();
    }

    public void btnregisterla(View view) {
        startActivity(new Intent(MainActivity.this,RegisterUser.class));
        finish();
    }

}