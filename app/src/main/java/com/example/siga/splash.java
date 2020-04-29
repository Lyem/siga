package com.example.siga;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class splash extends AppCompatActivity {
    int logado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logado = 0;
                if (logado == 0){
                    startActivity(new Intent(getBaseContext(),login.class));
                    finish();
                }else {
                    startActivity(new Intent(getBaseContext(),MainActivity.class));
                    finish();
                }
            }
        },1000);
    }
}
