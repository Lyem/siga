package com.example.siga;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
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
    String word;
    network nt = new network();
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
        url = nt.home;

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
                ts.setText("");
                fileContentsu = user.getText().toString() + "sp";
                fileContentss = passwd.getText().toString();
                new conection().execute();
            }
        });

    }

    public class conection extends AsyncTask<Void, Void, Void> {

        @Override
        public Void doInBackground(Void... voids) {
            try {
                JSONObject extra = new JSONObject();
                extra.put("MPW0005", "login_top");
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("_EventName", "E'EVT_CONFIRMAR'.");
                jsonObj.put("_EventGridId", "");
                jsonObj.put("_EventRowId", "");
                jsonObj.put("MPW0005_CMPPGM", "login_top.aspx");
                jsonObj.put("MPW0005GX_FocusControl", "");
                jsonObj.put("vSAIDA", "");
                jsonObj.put("vREC_SIS_USUARIOID", "");
                jsonObj.put("GX_FocusControl", "vSIS_USUARIOID");
                jsonObj.put("GX_AJAX_KEY", "A5B9F9DDEA7C4D2B545B2C46A249948A");
                jsonObj.put("AJAX_SECURITY_TOKEN", "35931FC765E3775CD4A095723E12D8C584849F732AAA908C903CF485FE2C1C38");
                jsonObj.put("GX_CMP_OBJS", extra);
                jsonObj.put("sCallerURL", "");
                jsonObj.put("GX_RES_PROVIDER", "GXResourceProvider.aspx");
                jsonObj.put("GX_THEME", "GeneXusX");
                jsonObj.put("_MODE", "");
                jsonObj.put("Mode", "");

                Connection.Response loginForm = Jsoup.connect("https://siga.cps.sp.gov.br/aluno/login.aspx")
                        .method(Connection.Method.GET)
                        .execute();

                Document doc = Jsoup.connect("https://siga.cps.sp.gov.br/aluno/login.aspx")
                        .data("vSIS_USUARIOID", fileContentsu)
                        .data("vSIS_USUARIOSENHA", fileContentss)
                        .data("GXState", jsonObj.toString())
                        .cookies(loginForm.cookies())
                        .post();

                Connection connection = Jsoup.connect(url)
                        .cookies(loginForm.cookies())
                        .method(Connection.Method.GET);
                Connection.Response response = connection.execute();

                doc = response.parse();
                word = doc.select("title").first().text();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
            return;
        }
    }

}
