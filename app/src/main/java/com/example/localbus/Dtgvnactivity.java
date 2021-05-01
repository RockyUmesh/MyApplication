package com.example.localbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dtgvnactivity extends AppCompatActivity {

    ProgressBar pbh;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference notebookref = db.collection("Bus Data Collection");

    EditText busname_di,busnum_di,from_di,to_di,tmofdp_di,tmofarr_di;
    ConstraintLayout constraintLayoutda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtgvnactivity);

        busname_di=findViewById(R.id.bsNm2);
        from_di=findViewById(R.id.sa_to2);
        to_di=findViewById(R.id.to2);
        tmofdp_di=findViewById(R.id.dt2);
        tmofarr_di=findViewById(R.id.at2);
        busnum_di=findViewById(R.id.bno2);

        constraintLayoutda=findViewById(R.id.clda);

        pbh=findViewById(R.id.progressBar2);
        pbh.setVisibility(View.GONE);

    }

    public void svbtn(View view) {

        pbh.setVisibility(View.VISIBLE);

        String busname=busname_di.getText().toString().trim();
        String from=from_di.getText().toString().trim();
        String todi=to_di.getText().toString().trim();
        String dpt=tmofdp_di.getText().toString().trim();//c1
        String toa=tmofarr_di.getText().toString().trim();//c2
        String busnum=busnum_di.getText().toString().trim();//optional
        String finale1;
        finale1=from.concat(todi);

        //  char[] i = new char[3];
        //    i[0] = myStr.charAt(0);
        //    i[1]=str.charAt(0);
        //    s=String.valueOf(i);
        if(busnum.isEmpty()){
            busnum_di.setError("optional");
            busnum_di.requestFocus();
        }
        if(from.isEmpty())
        {
            from_di.setError("Fill it");
            from_di.requestFocus();
            return;
        }
        if(todi.isEmpty())
        {
            to_di.setError("Fill it");
            to_di.requestFocus();
            return;
        }
        if(dpt.isEmpty()){
            tmofdp_di.setError("Fill the Departure time");
            tmofdp_di.requestFocus();
            return;
        }
        if(toa.isEmpty()){
            tmofarr_di.setError("Fill the Arrival time");
            tmofarr_di.requestFocus();
            return;
        }
        if(!(busname.isEmpty() && from.isEmpty() && todi.isEmpty() && dpt.isEmpty() && toa.isEmpty())) {
            ArrayList<String> bi = new ArrayList<String>();
            bi.add(finale1.toLowerCase());
            bi.add(from);
            bi.add(todi);
            bi.add(busname);
            bi.add(dpt);
            bi.add(toa);
            bi.add(busnum);

            //Collectingdata note = new Collectingdata(bi);
            //notebookref.add(note);

            //// time stamp = FieldValue.serverTimestamp()
            Map<String,Object> busdata = new HashMap<>();
            //busdata.put("Time stamp",FieldValue.serverTimestamp());
            busdata.put("bi", bi);
            //
            db.collection("Bus Data Collection").document().set(busdata)
                    .addOnSuccessListener(new  OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
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
        else{
            Snackbar.make(view, "Fill it correctly!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}