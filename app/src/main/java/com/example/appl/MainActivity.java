package com.example.appl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    TextView forgotPwd;
    EditText email;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        forgotPwd = findViewById(R.id.textView3);
    }

    public void logInClk(View view){
        email = findViewById(R.id.editTextTextEmailAddress);
        pass = findViewById(R.id.editTextTextPassword);
        String em = email.getText().toString();
        String pwd = pass.getText().toString();

        if(em.equals("test") && pwd.equals("test123")) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        }
        else{
            Snackbar.make(view, "Wrong login details entered", Snackbar.LENGTH_LONG).show();
        }
    }

    public void forgotPwdClk(View view){
        BottomSheetDialog bsd = new BottomSheetDialog(MainActivity.this, R.style.bsdTheme);
        View bsv = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottomsheetlayout, (LinearLayout)findViewById(R.id.bscontainer));
        bsd.setContentView(bsv);
        bsd.show();
    }
}