package com.bsc.eRoots21testApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.amplitude.api.Amplitude;
import com.amplitude.api.AmplitudeClient;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class AnimTest extends AppCompatActivity {
    private Handler mHandler = new Handler();
    RatingBar stars;
    float rStart= 0;
    RatingBar rb2;
    FloatingActionButton ffa;

//    Animation animScale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_test);

        stars = findViewById(R.id.ratingbar);
        rb2 = findViewById(R.id.ratingbar2);
        ffa = findViewById(R.id.fabr);

        float current = stars.getRating();
        rStart = current;
        System.out.println(current);
        setRatingAnimation(current);

        AmplitudeClient client = Amplitude.getInstance()
                .initialize(getApplicationContext(), "3bc9b2e711d1c1b3aebe747d43521114")
                .enableForegroundTracking(getApplication());


        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser){
                setRatingAnimation(rating);
                rStart = rating;
                String message = "Rating is recorded as:" + rating;
                Toast.makeText(AnimTest.this, message,Toast.LENGTH_SHORT).show();
                }


            }
        });
        rb2.setEnabled(true);
        rb2.setClickable(true);

//        rb2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                if(fromUser){
//                    String message = "Rating is recorded as:" + rating;
//                    Toast.makeText(MainActivity2.this, message,Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        rb2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                float rating = rb2.getRating();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(rb2,
                                "scaleX", 0.9f);
                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(rb2,
                                "scaleY", 0.9f);
                        scaleDownX.setDuration(100);
                        scaleDownY.setDuration(100);

                        AnimatorSet scaleDown = new AnimatorSet();
                        scaleDown.play(scaleDownX).with(scaleDownY);

                        scaleDown.start();
                        System.out.println(rb2.getRating());

                        break;

                    case MotionEvent.ACTION_UP:
                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(
                                rb2, "scaleX", 1f);
                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(
                                rb2, "scaleY", 1f);
                        scaleDownX2.setDuration(200);
                        scaleDownY2.setDuration(200);

                        AnimatorSet scaleDown2 = new AnimatorSet();
                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
                        scaleDown2.start();
                        break;
                }
                return rb2.onTouchEvent(event);
            }
        });



    }


    public void setRatingAnimation(float rate){
        ObjectAnimator anim = ObjectAnimator.ofFloat(stars, "rating", rStart, rate);
        anim.setDuration(3000);
        anim.start();
//        stars.startAnimation(anim);
    }
    //        mHandler.postDelayed(mRunnable, 2000);

    public void fabrClk(View view){
        Amplitude.getInstance().logEvent("ADD_CLICKED");
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("yoyoyoy");
            Toast.makeText(AnimTest.this, "delayed msg", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT);
        return true;
//        return super.onOptionsItemSelected(item);
    }

    CoordinatorLayout claa;

    public void fa2(View claa){
        claa =  findViewById(R.id.cl1);
       Snackbar sb = Snackbar.make(claa , "Welcome to BSC", Snackbar.LENGTH_LONG);
       sb.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
       sb.show();
    }

    public void aboutUsClk(MenuItem menu){
        Intent intent = new Intent(AnimTest.this, AboutUs.class);
        startActivity(intent);
    }


    public static class AboutUs extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about_us);
        }

        public void fabinfoclk(View view){
            Intent intent = new Intent(AboutUs.this, BottomNav.class);
            startActivity(intent);
        }


    }
}