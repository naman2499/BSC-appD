package com.bsc.eRoots21testApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class BottomNav extends AppCompatActivity {
    private BottomSheetBehavior mbsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        BottomNavigationView bnv = findViewById(R.id.botnav);
        bnv.setOnNavigationItemSelectedListener(navListner);
        View bottomSheet = findViewById(R.id.bsNestedScrollView);
        mbsv = BottomSheetBehavior.from(bottomSheet);
    }


    public BottomNavigationView.OnNavigationItemSelectedListener navListner  =
             item -> {
                    switch (item.getItemId()){
                        case R.id.option1:
                            mbsv.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                            break;
                        case R.id.option2:
                            mbsv.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            break;
                        case R.id.option3:
                            mbsv.setState(BottomSheetBehavior.STATE_HIDDEN);
                            break;

                        default:
                            break;
                    }return true;
            };







}