package com.example.siga;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navigationView;
    String nome, foto, pp, pr, maiorpr, av, user,passwd;
    network infos = new network();
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
            infos.setPasswd(passwd);
            infos.setUser(user);
            homeinfo();
            new set().execute();
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

    public void homeinfo(){
        infos.setUrl(infos.home);
        Document data = infos.getData();
        nome = data.select("span#span_MPW0041vPRO_PESSOALNOME").first().text();
        foto = data.select("div#MPW0041FOTO").first().select("img").attr("src");
        pp = data.select("span#span_MPW0041vACD_ALUNOCURSOINDICEPP").first().text();
        pr = data.select("span#span_MPW0041vACD_ALUNOCURSOINDICEPR").first().text();
        maiorpr = data.select("span#span_MPW0041vMAX_ACD_ALUNOCURSOINDICEPR").first().text();
        String txt = new clear().getPlainText(data.select("span#span_vTEXTO"));
        av = txt;
    }

    public class set extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            h.setNome(nome);
            h.setFotourl(foto);
            h.setPpp(pp);
            h.setPr(pr);
            h.setMaiorpr(maiorpr);
            h.setAv(av);
        }
    }

}