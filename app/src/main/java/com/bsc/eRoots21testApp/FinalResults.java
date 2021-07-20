package com.bsc.eRoots21testApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.sql.SQLOutput;

public class FinalResults extends AppCompatActivity {

    TextView textView;
    TextView textView1;
    TextView textViewLR, textViewDT, textViewRF;

    TextView tvF, tvLR, tvDT, tvRF;
    Dialog modelDialog;

    TextView tvmsg, tvInfoMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Picture Description Test");
        setContentView(R.layout.activity_final_results);


        String jsonData = getIntent().getStringExtra("jsonData");

        ProgressBar progressBar = findViewById(R.id.progressBar);
        ProgressBar progressBarLR = findViewById(R.id.progressBarLR);
        ProgressBar progressBarDT = findViewById(R.id.progressBarDT);
        ProgressBar progressBarRF = findViewById(R.id.progressBarRF);

        textView1 = findViewById(R.id.textView7);
        textViewLR = findViewById(R.id.textViewLR);
        textViewDT = findViewById(R.id.textViewDT);
        textViewRF = findViewById(R.id.textViewRF);

        tvF = findViewById(R.id.textView8);
        tvLR = findViewById(R.id.textView9);
        tvDT = findViewById(R.id.textView10);
        tvRF = findViewById(R.id.textView11);

        tvmsg = findViewById(R.id.textView);
        tvInfoMsg = findViewById(R.id.textView15);


        modelDialog = new Dialog(this);
        System.out.println(jsonData);
        Gson gson = new Gson();
        ModelData modelData = gson.fromJson(jsonData, ModelData.class);

        int probLR=0, probDT=0, probRF=0, probF=0;
        float probLRF =0, probDTF=0, probRFF=0, probFF=0;



        if(modelData.LR_category.equals("1")) {
            tvLR.setText("Dementia");
            probLRF = Float.parseFloat(modelData.LR_prob_1);
            progressBarLR.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        else{
            tvLR.setText("Healthy");
            probLRF = Float.parseFloat(modelData.LR_prob_0);
        }
        probLRF = probLRF*100;
        probLR = Math.round(probLRF);


        if(modelData.DT_category.equals("1")) {
            modelData.DT_prob_1 = modelData.DT_accuracy;
            modelData.DT_prob_0 = Float.toString(1 - Float.parseFloat(modelData.DT_accuracy));
            tvDT.setText("Dementia");
            probDTF = Float.parseFloat(modelData.DT_prob_1);
            progressBarDT.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        else{
            modelData.DT_prob_0 = modelData.DT_accuracy;
            modelData.DT_prob_1 = Float.toString(1 - Float.parseFloat(modelData.DT_accuracy));

            tvDT.setText("Healthy");
            probDTF = Float.parseFloat(modelData.DT_prob_0);
        }
        probDTF = probDTF*100;
        probDT = Math.round(probDTF);


        if(modelData.RF_category.equals("1")) {
            tvRF.setText("Dementia");
            probRFF = Float.parseFloat(modelData.RF_prob_1);
            progressBarRF.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        else{
            tvRF.setText("Healthy");
            probRFF = Float.parseFloat(modelData.RF_prob_0);
        }
        probRFF = probRFF*100;
        probRF = Math.round(probRFF);



        float probFOne = Float.parseFloat(modelData.LR_prob_1) + Float.parseFloat(modelData.DT_prob_1) + Float.parseFloat(modelData.RF_prob_1);
        float probFZero = Float.parseFloat(modelData.LR_prob_0) + Float.parseFloat(modelData.DT_prob_0) + Float.parseFloat(modelData.RF_prob_0);

        if(probFOne>probFZero){
            tvF.setText("Dementia");
            tvmsg.setText("YOUR DIAGNOSIS INDICATES A POSSIBLE RISK OF DEVELOPING ALZHEIMER'S DISEASE");
            tvInfoMsg.setText("You might have a risk of developing Dementia. It is recommended to contact a local physician for further diagnosis.");
            probFF = probFOne;
            progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));

        }
        else{
            tvF.setText("Healthy");
            tvmsg.setText("YOUR DIAGNOSIS DOES NOT INDICATE ANY SIGNS OF ALZHEIMER'S DISEASE");
            tvInfoMsg.setText("Click on any of the models to know more details about the analysis.");

            probFF = probFZero;
        }
        probFF = probFF*100f;
        probFF = probFF/3;
        probF = Math.round(probFF);



        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, probF); // see this max value coming back here, we animate towards that value
        animation.setDuration(5000); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        ValueAnimator animator = ValueAnimator.ofInt(0, probF);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView1.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();



        ObjectAnimator animationLR = ObjectAnimator.ofInt(progressBarLR, "progress", 0, probLR);
        animationLR.setDuration(5000); // in milliseconds
        animationLR.setInterpolator(new DecelerateInterpolator());
        animationLR.start();

        ValueAnimator animatorLR = ValueAnimator.ofInt(0,probLR);
        animatorLR.setDuration(5000);
        animatorLR.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textViewLR.setText(animation.getAnimatedValue().toString());
            }
        });
        animatorLR.start();


        ObjectAnimator animationDT = ObjectAnimator.ofInt(progressBarDT, "progress", 0, probDT);
        animationDT.setDuration(5000); // in milliseconds
        animationDT.setInterpolator(new DecelerateInterpolator());
        animationDT.start();

        ValueAnimator animatorDT = ValueAnimator.ofInt(0,probDT);
        animatorDT.setDuration(5000);
        animatorDT.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textViewDT.setText(animation.getAnimatedValue().toString());
            }
        });
        animatorDT.start();



        ObjectAnimator animationRF = ObjectAnimator.ofInt(progressBarRF, "progress", 0, probRF);
        animationRF.setDuration(5000); // in milliseconds
        animationRF.setInterpolator(new DecelerateInterpolator());
        animationRF.start();

        ValueAnimator animatorRF = ValueAnimator.ofInt(0,probRF);
        animatorRF.setDuration(5000);
        animatorRF.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textViewRF.setText(animation.getAnimatedValue().toString());
            }
        });
        animatorRF.start();






        progressBarLR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView close;
                TextView mName, mDetails, mAccuracy, mConOne, mConZero;

                modelDialog.setContentView(R.layout.activity_model_details);
                mAccuracy = modelDialog.findViewById(R.id.textView20);
                mName = modelDialog.findViewById(R.id.textView18);
                mDetails = modelDialog.findViewById(R.id.textView19);
                mConOne = modelDialog.findViewById(R.id.textView21);
                mConZero = modelDialog.findViewById(R.id.textView22);

                close = modelDialog.findViewById(R.id.close);
                close.setOnClickListener(view -> {
                            modelDialog.dismiss();
                        }
                );
                mName.setText("Logistic Regression");
                mConOne.setText("Dementia: " + modelData.LR_prob_1.substring(0,Math.min(modelData.LR_prob_1.length(), 4)));
                mConZero.setText("Healthy: " + modelData.LR_prob_0.substring(0,Math.min(modelData.LR_prob_1.length(), 4)));
                mAccuracy.setText("Model Accuracy: " + Float.parseFloat(modelData.LR_accuracy.substring(0,Math.min(modelData.LR_accuracy.length(), 4)))*100 + "%");
                mDetails.setText("Logistic Regression is a Machine Learning algorithm which is used for the classification problems, it is a predictive analysis algorithm and based on the concept of probability.");
                modelDialog.show();
            }
        });




        progressBarDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView close;
                TextView mName, mDetails, mAccuracy, mConOne, mConZero;

                modelDialog.setContentView(R.layout.activity_model_details);
                mAccuracy = modelDialog.findViewById(R.id.textView20);
                mName = modelDialog.findViewById(R.id.textView18);
                mDetails = modelDialog.findViewById(R.id.textView19);
                mConOne = modelDialog.findViewById(R.id.textView21);
                mConZero = modelDialog.findViewById(R.id.textView22);

                close = modelDialog.findViewById(R.id.close);
                close.setOnClickListener(view -> {
                            modelDialog.dismiss();
                        }
                );
                mName.setText("Decision Tree");
                mConOne.setText("Dementia: " + modelData.DT_category);
                String catzero = "1";
                if(Integer.parseInt(modelData.DT_category) == 1){
                    catzero = "0";
                }
                mConZero.setText("Healthy: " + catzero);
                mAccuracy.setText("Model Accuracy: " + Float.parseFloat(modelData.DT_accuracy.substring(0,Math.min(modelData.DT_accuracy.length(), 4)))*100 + "%");
                mDetails.setText("Decision Tree is a Supervised learning technique that is majorly used for classification problems. It consists of two types of nodes, the Decision Node and Leaf Node. Decision nodes are used to make any decision and have multiple branches, whereas Leaf nodes are the output of those decisions.");
                modelDialog.show();


            }
        });

        progressBarRF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView close;
                TextView mName, mDetails, mAccuracy, mConOne, mConZero;

                modelDialog.setContentView(R.layout.activity_model_details);
                mAccuracy = modelDialog.findViewById(R.id.textView20);
                mName = modelDialog.findViewById(R.id.textView18);
                mDetails = modelDialog.findViewById(R.id.textView19);
                mConOne = modelDialog.findViewById(R.id.textView21);
                mConZero = modelDialog.findViewById(R.id.textView22);

                close = modelDialog.findViewById(R.id.close);
                close.setOnClickListener(view -> {
                            modelDialog.dismiss();
                        }
                );
                mName.setText("Random Forest");
                mConOne.setText("Dementia: " + modelData.RF_prob_1.substring(0,Math.min(modelData.RF_prob_1.length(), 4)));
                mConZero.setText("Healthy: " + modelData.RF_prob_0.substring(0,Math.min(modelData.RF_prob_1.length(), 4)));
                mAccuracy.setText("Model Accuracy: " + Float.parseFloat(modelData.RF_accuracy.substring(0,Math.min(modelData.RF_accuracy.length(), 4)))*100 + "%");
                mDetails.setText("A random forest is a supervised machine learning algorithm, consisting of many decision trees. It establishes the outcome based on the predictions of the decision trees. It predicts by taking the average or mean of the output from various trees. It’s more accurate than the decision tree algorithm.");
                modelDialog.show();

            }
        });

    }
}