/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 2:52 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class SplashScreen extends AppCompatActivity {

    private ImageView logo;
    private TextView logo_txt;
    private static int SPLASH_TIME = 3500;
    private SharedPreferences mSahredPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        logo = findViewById(R.id.splash_logo_circle);
        logo_txt = findViewById(R.id.splash_fitify);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSahredPref = getSharedPreferences("SharedPref", MODE_PRIVATE);
                boolean isFirstTime = mSahredPref.getBoolean("firstTime", true);
                boolean isEntered = mSahredPref.getBoolean("isDetailsEntered",false);
                if (isFirstTime) {
                    SharedPreferences.Editor editor = mSahredPref.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();
                    Intent intent = new Intent(SplashScreen.this, OnboardingActivity.class);
                    startActivity(intent);
                    finish();
                }

                else {
                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        // Its Available...
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user!=null){

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(SplashScreen.this, DeatailsForm.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });

                        }
                        else {
                            Intent intent1 = new Intent(SplashScreen.this, LoginActivity.class);
                            startActivity(intent1);
                            finish();
                        }
                    } else {
                        // Not Available...
                        Intent intent = new Intent(SplashScreen.this,NoInternetAcitivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        }, SPLASH_TIME);

    }



}