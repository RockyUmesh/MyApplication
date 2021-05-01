package com.example.localbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView tv;
    Handler handler;
    ProgressBar pbh;
    int progress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tv=findViewById(R.id.wlcmtxtvss);
        pbh=findViewById(R.id.pbss);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //to make the horizontal progress bar visible
                          if(pbh.getProgress()<100){
                           pbh.setProgress(progress);
                           progress+=10;
                           handler.postDelayed(this,1);
                             }
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
                          }
                    },100);

        //try {
        //            Thread.sleep(3000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
    }
}