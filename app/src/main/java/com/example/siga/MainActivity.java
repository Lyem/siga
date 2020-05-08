package com.example.siga;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navigationView;
    String nome, foto, pp, pr, maiorpr, av;
    home h = new home();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            leitura("mypass");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            leitura("myuser");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user != null && passwd != null){
            new conection().execute();
        }
        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);
        Fragment init = h;
        openFragment(init);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home: {
                fragment = h;
                openFragment(fragment);
                break;
            }
            case R.id.nav_aulas: {
                fragment = new aulas();
                openFragment(fragment);
                break;
            }
            case R.id.nav_sobre: {
                fragment = new sobre();
                openFragment(fragment);
                break;
            }
        }

        return true;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    String user, passwd;

    private void leitura(String nome) throws IOException {
        FileInputStream infl = openFileInput(nome);

        Scanner scanner = new Scanner(infl);
        try {
            StringBuilder sb = new StringBuilder();
            String line = scanner.nextLine();
            sb.append(line);
            if (nome.contains("mypass")) {
                passwd = sb.toString();
            } else if (nome.contains("myuser")) {
                user = sb.toString();
            }
        } finally {
            network nt = new network();
            scanner.close();
        }

    }

    public class conection extends AsyncTask<Void, Void, Void> {
        String words;
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
                        .data("vSIS_USUARIOID", user)
                        .data("vSIS_USUARIOSENHA", passwd)
                        .data("GXState", jsonObj.toString())
                        .cookies(loginForm.cookies())
                        .post();

                Connection connection = Jsoup.connect("https://siga.cps.sp.gov.br/aluno/home.aspx")
                        .cookies(loginForm.cookies())
                        .method(Connection.Method.GET);
                Connection.Response response = connection.execute();

                doc = response.parse();
                words = doc.select("span#span_MPW0041vPRO_PESSOALNOME").first().text();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            nome = words;
            h.setNome(nome);
        }
    }
}