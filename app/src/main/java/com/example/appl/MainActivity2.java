package com.example.appl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

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
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        startActivity(intent);
    }


}