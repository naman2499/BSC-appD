package com.bsc.eRoots21testApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class InstructionsCookie extends AppCompatActivity {
    TextView instr,headInstr;
    Button cont;
    private AudioManager mAudioManager;
    private int mStreamVolume = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Instructions");
        setContentView(R.layout.activity_instructions_cookie);


        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        instr = findViewById(R.id.textView16);
        headInstr = findViewById(R.id.textView17);
        cont = findViewById(R.id.button3);
        headInstr.setText("Welcome to the Picture Description Task!\n\n");
        instr.setText("1. In the next screen, a picture will be displayed on the top of the screen.\n\n\n2. Please try to describe the picture in your own words, in as much detail you can.\n\n\n3. Ideally, speak for about a minute.");
        cont.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStreamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM); // getting system volume into var for later un-muting
                if(mStreamVolume==0){
                Intent intent = new Intent(InstructionsCookie.this, CookieJar.class);
                startActivity(intent);}
                else{
                    Toast.makeText(InstructionsCookie.this, "Please mute your system volume to proceed.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}