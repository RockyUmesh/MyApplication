package com.example.localbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText email_et;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        auth = FirebaseAuth.getInstance();

        email_et=findViewById(R.id.email_fpa);
        progressBar=findViewById(R.id.pb_fpa);
        progressBar.setVisibility(View.GONE);
    }

    public void btnreset_fpa(View view) {
        String email=email_et.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);

        if(email.isEmpty()){
            email_et.setError("Email is required");
            email_et.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_et.setError("Please provide valid email");
            email_et.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Snackbar snackbar=Snackbar.make(view, "Check", Snackbar.LENGTH_INDEFINITE)
                            .setAction("View Details",null);
                                    //new View.OnClickListener() {
                                //@Override
                                //public void onClick(View v) {
                                    //Intent i=new Intent(Intent.ACTION_SEND);
                                    //                                    i.setData(Uri.parse("email"));
                                    //                                    String[] s={"rocky11brooklyn10@gmail.com"};
                                    //                                    i.putExtra(Intent.EXTRA_EMAIL,s);
                                    //                                    i.putExtra(Intent.EXTRA_SUBJECT,"email subject");
                                    //                                    i.putExtra(Intent.EXTRA_TEXT,"hi this is a email body");
                                    //                                    i.setType("message/rfc822");
                                    //Intent chooser = Intent.createChooser(i,"Launch Email");
                                    //startActivity(chooser);
                                //}
                           // }).setActionTextColor(Color.RED);
                    snackbar.show();
                }
                else{
                    Snackbar.make(view, "Try again!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}