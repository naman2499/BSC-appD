package com.bsc.eRoots21testApp;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.StrictMode;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.common.util.IOUtils;

public class CookieJar extends Activity implements RecognitionListener {
    private TextView returnedText;
    private ToggleButton toggleButton;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognition";
    String speechString = "";
    boolean spechStarted = false;
    private AudioManager mAudioManager;
    private int mStreamVolume = 0;
//    private static final String LOCAL_URL = "http://45.77.223.13:8080/";
        private static final String LOCAL_URL = "http://eae2ffc2c563.ngrok.io/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie_jar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        returnedText =  findViewById(R.id.textView1);
        progressBar =  findViewById(R.id.progressBar1);
        toggleButton =  findViewById(R.id.toggleButton1);

        progressBar.setVisibility(View.INVISIBLE);

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);


//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
//        recognizerIntent.putExtra(RecognizerIntent.ACTION_WEB_SEARCH, true);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        toggleButton.setText("START");

        toggleButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));

        toggleButton.setTextOff("START");
        toggleButton.setTextOn("STOP");
        returnedText.setText("Press the START button below to start voice recording.");

        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    speechString = "";
                    returnedText.setText(speechString);
                    speech.setRecognitionListener(CookieJar.this);

                    toggleButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    speech.startListening(recognizerIntent);
                } else {

                    if(speechString.isEmpty())
                        Toast.makeText(CookieJar.this, "No speech detected. Please try again.", Toast.LENGTH_LONG).show();
                    else
                    Toast.makeText(CookieJar.this, "LOADING...PLEASE WAIT A FEW SECONDS", Toast.LENGTH_LONG).show();


                    System.out.println(speechString);
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    sendData();
                    toggleButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));

                    speech.stopListening();
                    speech.destroy();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        returnedText.setText("\n\n\nPress the START button below to start voice recording.");
    }

    public void sendData() {
        runOnUiThread(new Runnable() {
            public void run() {
//                String temp  = "{\"LogisticRegression\":{\"category\":1,\"accuracy\":0.7297297297297297,\"probabilty_0\":0.02547577271353152,\"probabilty_1\":0.9745242272864685},\"DecisionTreeClassifier\":{\"category\":1,\"accuracy\":0.6486486486486487,\"probabilty_0\":0.0,\"probabilty_1\":1.0},\"RandomForestClassifier\":{\"category\":1,\"accuracy\":0.6936936936936937,\"probabilty_0\":0.0,\"probabilty_1\":1.0},\"features\":[0.9411764705882353,450.71897776278877,0,-0.09928571428571331,2.2314285714285695,0,0.0,1,1,7.0]}";
                String newSpeechString = speechString.replaceAll(" ", "%20");

                try {
//
                    URL url = new URL(LOCAL_URL + "?text=" + newSpeechString);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");

                    System.out.println("RESPONSE IS:" + con.getResponseMessage());

                    InputStream in = con.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    for (int ch; (ch = in.read()) != -1; ) {
                        sb.append((char) ch);
                    }
                    System.out.println(sb.toString());
//                    Toast.makeText(CookieJar.this, sb.toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getBaseContext(), FinalResults.class);
                    intent.putExtra("jsonData", sb.toString());

//                    intent.putExtra("jsonData", temp);


                    startActivity(intent);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        spechStarted = true;
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
//        speech.startListening(recognizerIntent);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        spechStarted = false;
        Log.i(LOG_TAG, "onEndOfSpeech");
        speech.startListening(recognizerIntent);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
//        if (!spechStarted)
            speech.startListening(recognizerIntent);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");

        ArrayList<String> matches = arg0
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        returnedText.setText(speechString + matches.get(0));
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        speechString = speechString + ". " + matches.get(0);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }


    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
}