package com.example.localbus;

////
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SrchAct extends AppCompatActivity {

    EditText ebf,ebt;
    TextView textViewData;
    int progress=0;
    ProgressBar pbh;
    Handler handler;
    Button bfsnp;
    String TAG;
    ConstraintLayout constraintLayout;
    int theme;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference notebookref = db.collection("Bus Data Collection");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srch);

        ebf=findViewById(R.id.bsNm2);
        ebt=findViewById(R.id.sa_to2);
        textViewData = findViewById(R.id.text_view_data);

        mAuth = FirebaseAuth.getInstance();

        constraintLayout=(ConstraintLayout) findViewById(R.id.clsa);
        //pbh=(ProgressBar) findViewById(R.id.progressBarr);//progress bar horizontal
        //bfsnp=findViewById(R.id.bsearch);
        //pbh.setVisibility(View.GONE);//to make the horizontal progress bar invisible

        //bfsnp.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                //for the horizontal action bar
        //                //handler=new Handler();
        //                //                handler.postDelayed(new Runnable() {
        //                //                    @Override
        //                //                    public void run() {
        //                //                        pbh.setVisibility(View.VISIBLE); //to make the horizontal progress bar visible
        //                //                        if(pbh.getProgress()<100){
        //                //                            pbh.setProgress(progress);
        //                //                            progress++;
        //                //                            handler.postDelayed(this,100);
        //                //                        }
        //                //                    }
        //                //                },100);
        //
        //
        //                //pbh.setVisibility(View.GONE);
        ebt.setVisibility(View.GONE);
        textViewData.setVisibility(View.GONE);
    }

    public void btnfab(View view) {
        Intent i=new Intent(SrchAct.this,Dtgvnactivity.class);
        startActivity(i);
    }

    public void infosrch(View view) {

        String from1s=ebf.getText().toString().toLowerCase();
        String todi1s=ebt.getText().toString().toLowerCase();
        String finale1s;
        finale1s=from1s.concat(todi1s);

        if(from1s.isEmpty()){
            ebf.setError("It is required");
            ebf.requestFocus();
            return;
        }

        if(todi1s.isEmpty()){
            ebt.setError("Fill it");
            ebt.requestFocus();
            return;
        }

        int flnt=finale1s.length();
        int frmlen=from1s.length();
        int tolen=todi1s.length();

        if(!(from1s.isEmpty() && todi1s.isEmpty())){
            //Collectingdata note = new Collectingdata(bi);
            notebookref.whereArrayContains("bi", finale1s).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            String data = "Buses List with Name\nDeparture Time\nArrival Time(OPTIONAL!)\nBus Number(OPTIONAL!!)...\n";
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Collectingdata note = documentSnapshot.toObject(Collectingdata.class);
                                note.setDocumentId(documentSnapshot.getId());

                                //String documentId = note.getDocumentId();
                                //prnt.add(documentId);
                                //data += "ID: " + documentId;
                                int i=1;
                                for(String bi : note.getBi()) {
                                    if(i%4==0){
                                        data += "\n" + bi;
                                    }
                                    else{
                                        i++;
                                    }
                                }
                                data += "\n\n";

                            }
                            textViewData.setVisibility(View.VISIBLE);
                            textViewData.setText(data);//textView
                            Snackbar.make(view, "Data Fetched", Snackbar.LENGTH_LONG)
                                    .setAction("View Details", null).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(view, "Failed", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
        }
    }

    public void froms(View view) {
        ebt.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_srch,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.adduser:
                Intent dd=new Intent(SrchAct.this,RegisterUser.class);
                startActivity(dd);
                break;

            case R.id.drkthm:
                Toast.makeText(getApplicationContext(),"Theme changed!",Toast.LENGTH_SHORT).show();
                //constraintLayout.setBackgroundColor(Color.BLACK);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Intent i1=new Intent(SrchAct.this,MainActivity.class);
                startActivity(i1);
                break;
            case R.id.lgththm:
                Toast.makeText(getApplicationContext(),"Theme changed!",Toast.LENGTH_SHORT).show();
                //constraintLayout.setBackgroundColor(Color.WHITE);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Intent i2=new Intent(SrchAct.this,MainActivity.class);
                startActivity(i2);
                break;
            case R.id.abtd:
                showAlertDialog();
                break;
            case R.id.deleteacc:
                deleteaccount();
                break;
            case  R.id.cntctd:
                contactdeveloper();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void contactdeveloper() {
        Intent i=new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse("email"));
        String[] s={"020upc92@gmail.com"};
        i.putExtra(Intent.EXTRA_EMAIL,s);
        i.putExtra(Intent.EXTRA_SUBJECT,"email subject");
        i.putExtra(Intent.EXTRA_TEXT,"Via apk requests query");
        i.setType("message/rfc822");
        Intent chooser = Intent.createChooser(i,"Launch Email");
        startActivity(chooser);
    }

    private void deleteaccount() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to Delete this account?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // [START delete_user]
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User account deleted.");
                                }
                            }
                        });
                // [END delete_user]
                Toast.makeText(getApplicationContext(),"Account Deleted!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void showAlertDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("About Developer");
        builder.setMessage("Hi there,My name is UMESH KUMAR AHIRWAR from MITS college,Gwalior and Iam the man behind this app." +
                "If you have any suggestions or query just contact me on 020upc92@gmail.com."+"Thank you!");
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}
