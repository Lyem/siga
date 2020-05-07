package com.example.siga;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import org.jsoup.nodes.Document;

import java.io.FileOutputStream;

public class login extends AppCompatActivity {
    int onoff = 0;
    Button bt;
    EditText user;
    EditText passwd;
    TextView ts;
    ToggleButton olho;
    String url;
    String filenameu = "myuser";
    String filenames = "mypass";
    String fileContentsu;
    String fileContentss;
    FileOutputStream outputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt = (Button) findViewById(R.id.loginbutton);
        user = (EditText) findViewById(R.id.user);
        passwd = (EditText) findViewById(R.id.passwd);
        ts = (TextView) findViewById(R.id.teste);
        olho = (ToggleButton) findViewById(R.id.eye);
        String icon="closed_eyes_24";
        url = new network().home;

        olho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onoff == 0){
                    onoff = 1;
                    passwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else if (onoff == 1){
                    onoff = 0;
                    passwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                System.out.println(onoff);

            }
        });

        ts.setText("");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileContentsu = user.getText().toString() + "sp";
                fileContentss = passwd.getText().toString();
                network nt = new network();
                nt.setUser(fileContentsu);
                nt.setPasswd(fileContentss);
                nt.setUrl(nt.home);
                Document info = nt.getData();
                String word = info.select("title").first().text();
                if (word.contains("login")){
                    ts.setText("Login_error");
                }else if (word.contains("home")){
                    try {
                        outputStream = openFileOutput(filenameu, Context.MODE_PRIVATE);
                        outputStream.write(fileContentsu.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        outputStream = openFileOutput(filenames, Context.MODE_PRIVATE);
                        outputStream.write(fileContentss.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(getBaseContext(),MainActivity.class));
                    finish();
                }
            }
        });

    }

}
