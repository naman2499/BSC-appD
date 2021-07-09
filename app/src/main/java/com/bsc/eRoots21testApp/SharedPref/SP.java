package com.bsc.eRoots21testApp.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class SP {
    public static final String LoginData = "loginData";
    public static final String EmailSaved = "email";
    public static final String PassSaved = "pwd";
    public static final String CheckRem = "chk";
    public static SharedPreferences sharedpreferences;

    private static SP single_instance = null;

    private SP(Context context) {
        try {
            MasterKey mainKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            sharedpreferences = EncryptedSharedPreferences.create(
                    context,
                    LoginData,
                    mainKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static SP getInstance(Context context) {
        if (single_instance == null)
            single_instance = new SP(context);
        return single_instance;
    }
}

