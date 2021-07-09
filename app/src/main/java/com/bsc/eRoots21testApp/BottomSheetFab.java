package com.bsc.eRoots21testApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.animation.ObjectAnimator;
import android.os.Bundle;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BottomSheetFab extends AppCompatActivity {
    //use previous offset of bottom sheet
    float prevOffset = 0;

    FloatingActionButton fab;
    ObjectAnimator objectAnimator;
    LinearLayout llBottomSheet;
    BottomSheetBehavior bottomSheetBehavior;

    //for storing fabHeight/2
    int fabh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_fab);

        fab = findViewById(R.id.fab_power);
        llBottomSheet = findViewById(R.id.botsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        //for initial FAB Placement on the Y axis along the peek height
        fab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                fab.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                fabh = fab.getHeight() / 2;
                fab.setTranslationY(-bottomSheetBehavior.getPeekHeight() + fabh);
            }
        });


        //For getting the slideOffset of the bottom sheet : 0 at peek & 1 at fully expanded
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //intentional blank override
            }

            @Override
            public void onSlide(@NonNull View llBottomSheet, float slideOffset) {
                customAnimate(slideOffset);
            }
        });
    }

    //setting FAB placement after resume
    @Override
    protected void onResume() {
        super.onResume();
        if (prevOffset == 0)
            fab.setTranslationY(-bottomSheetBehavior.getPeekHeight() + fabh);
        else
            fab.setTranslationY(-fabh);
    }

    void customAnimate(float slideOffset) {
        if (slideOffset > 1 || slideOffset < 0) {
            //do nothing
        } else {
            //replace fab.getHeight() by the amount of distance the FAB should be above the bottom of layout at it's minimum position.
            fab.animate().translationYBy((slideOffset - prevOffset) * (bottomSheetBehavior.getPeekHeight() - fab.getHeight())).setDuration(0).start();
        }
        prevOffset = slideOffset;
    }
}