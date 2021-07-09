package com.bsc.eRoots21testApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amplitude.api.Amplitude;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import static com.bsc.eRoots21testApp.SharedPref.SP.*;

public class Dashboard extends AppCompatActivity {
    CardView cv1, cv2, cv3, cv4, cv5, cv6;
    Dialog dialog;
    RatingBar rb2;

    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 123;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        cv1 = findViewById(R.id.cv1);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);
        cv4 = findViewById(R.id.cv4);
        cv5 = findViewById(R.id.cv5);
        cv6 = findViewById(R.id.cv6);
        dialog = new Dialog(this);

        cv1.setOnClickListener(v -> {
            Amplitude.getInstance().logEvent("ANIMATION_CLK");
            Intent intent = new Intent(Dashboard.this, AnimTest.class);
            startActivity(intent);
        });
        cv2.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, BottomSheetFab.class);
            startActivity(intent);
        });
        cv3.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, CookieJar.class);
            startActivity(intent);
        });
        cv4.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, FabAnim.class);
            startActivity(intent);
        });
        cv5.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, AnimTest.AboutUs.class);
            startActivity(intent);

        });


        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                removeInstallStateUpdateListener();
            } else {
                Toast.makeText(getApplicationContext(), "InstallStateUpdatedListener: state: " + state.installStatus(), Toast.LENGTH_LONG).show();
            }
        };
        appUpdateManager.registerListener(installStateUpdatedListener);

        checkUpdate();
    }

    private void checkUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                startUpdateFlow(appUpdateInfo);             //STARTS THE UPDATE PROCESS - SHOWS THE DIALOG
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            }
        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, FLEXIBLE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLEXIBLE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        }
    }

    private void popupSnackBarForCompleteUpdate() {
        Snackbar.make(findViewById(android.R.id.content).getRootView(), "New app is ready!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Install", view -> {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.purple_500))
                .show();
    }

    private void removeInstallStateUpdateListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeInstallStateUpdateListener();
    }

    public void feedClk(View view) {
        TextView close;
        Button submit;
        dialog.setContentView(R.layout.activity_feedback);
        close = dialog.findViewById(R.id.close);
        submit = dialog.findViewById(R.id.submit);
        rb2 = dialog.findViewById(R.id.rbf);
        close.setOnClickListener(v -> {
                    dialog.dismiss();
                }
        );

        submit.setOnClickListener(v -> {
            dialog.dismiss();
            Snackbar.make(view, "Thank you for the Feedback!", Snackbar.LENGTH_LONG).show();
        });
        dialog.show();
        rb2.setOnTouchListener((View v, MotionEvent event) -> {
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

                    default:
                }
                return rb2.onTouchEvent(event);

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            if (sharedpreferences.getString(CheckRem, "").equals("true")) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(CheckRem, "logout").apply();
            } else {

                sharedpreferences.edit().clear().apply();

            }
            Intent intent = new Intent(Dashboard.this, LoginPage.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}


