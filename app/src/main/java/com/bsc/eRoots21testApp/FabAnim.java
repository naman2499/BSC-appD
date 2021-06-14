package com.bsc.eRoots21testApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class FabAnim extends AppCompatActivity {
    ProgressBar p1,p2;
    int prog = 20;
    ConstraintLayout c;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_anim);
        p1 = findViewById(R.id.fabProgress);
        p2 = findViewById(R.id.fabProgressDim);
        p1.setProgress(prog);
        c = findViewById(R.id.clayer);
        c.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }


    public void fabProgClk(View view){
        prog = prog+20;
        if(prog>100) {
            p1.setProgress(0);
            prog = 0;
        }

        else {
            p1.setProgress(prog,true);
        }
    }

    public void dimClk(View view) {
        if (p2.getProgress() == 100){p2.setProgress(0);c.setBackgroundColor(ContextCompat.getColor(this, R.color.white));}
        else {p2.setProgress(100);
            c.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray));
        }
    }
}