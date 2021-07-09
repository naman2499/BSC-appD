package com.bsc.eRoots21testApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.amplitude.api.Amplitude;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import static com.bsc.eRoots21testApp.SharedPref.SP.*;


public class LoginPage extends AppCompatActivity {
    TextView forgotPwd;
    EditText email;
    EditText pass;
    CheckBox rememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        forgotPwd = findViewById(R.id.textView3);
        email = findViewById(R.id.editTextTextEmailAddress);
        pass = findViewById(R.id.editTextTextPassword);
        rememberMe = findViewById(R.id.checkBox);

        Context context = getApplicationContext();
        getInstance(context);


        String cbCheck = sharedpreferences.getString(CheckRem, "");
        if(cbCheck.equals("true")){
            Log.d("AUTO_LOGIN","Logged IN from prev data");

            if(sharedpreferences.getString(EmailSaved,"").equals("test") && sharedpreferences.getString(PassSaved,"").equals("test123")){
                Intent intent = new Intent(LoginPage.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        }
            else if (cbCheck.equals("logout")){
                if(sharedpreferences.getString(EmailSaved,"").equals("test") && sharedpreferences.getString(PassSaved,"").equals("test123"))
                {
                    email.setText("test");
                    pass.setText("test123");
            }
        }
    }
//    @Override
//    public void onResume(){
//        super.onResume();
//        String cbCheck = sharedpreferences.getString(CheckRem, "");
//        if(cbCheck.equals("true")){
//            if(sharedpreferences.getString(EmailSaved,"").equals("test") && sharedpreferences.getString(PassSaved,"").equals("test123")){
//                email.setText("test");
//                pass.setText("test123");
//            }
//        }
//                else{
//        email.setText("");
//        pass.setText("");
//    }}

    public void logInClk(View view){
        String em = email.getText().toString();
        String pwd = pass.getText().toString();

        if(em.equals("test") && pwd.equals("test123")) {
            if(rememberMe.isChecked()){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(EmailSaved, em);
                editor.putString(PassSaved, pwd);
                editor.putString(CheckRem, "true");
                editor.apply();
            }else{
                sharedpreferences.edit().clear().apply();
            }
            Amplitude.getInstance().logEvent("LOGIN CLICKED");
            Intent intent = new Intent(LoginPage.this, Dashboard.class);
            startActivity(intent);finish();
        }
        else{
            sharedpreferences.edit().clear().apply();
            Snackbar.make(view, "Wrong login details entered...", Snackbar.LENGTH_LONG).show();
        }
    }

    public void forgotPwdClk(View view){
        BottomSheetDialog bsd = new BottomSheetDialog(LoginPage.this, R.style.bsdTheme);
        View bsv = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_forgot_password, findViewById(R.id.bscontainer));
        bsd.setContentView(bsv);
        bsd.show();
    }
}