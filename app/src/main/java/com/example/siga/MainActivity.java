package com.example.siga;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.loopj.android.image.SmartImageView;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    TextView nome;
    TextView pr;
    TextView pp;
    TextView maiorpr;
    TextView av;
    String user;
    String passwd;
    String url;
    Element avisos;
    private SmartImageView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nome = (TextView) findViewById(R.id.name);
        foto = (SmartImageView) findViewById(R.id.perfil);
        pr = (TextView) findViewById(R.id.pr);
        pp = (TextView) findViewById(R.id.pp);
        maiorpr = (TextView) findViewById(R.id.toppr);
        av = (TextView) findViewById(R.id.aviso);
        try {
            leitura("myuser");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            leitura("mypass");
        }catch (IOException e){
            e.printStackTrace();
        }
        url = new network().home;

        if(user != null && passwd != null){
            System.out.println(user);
            System.out.println(passwd);
            new conection().execute();
        }

    }

    private void leitura(String nome) throws IOException{
        FileInputStream infl = openFileInput(nome);

        Scanner scanner = new Scanner(infl);
        try {
            StringBuilder sb = new StringBuilder();
            String line = scanner.nextLine();
            sb.append(line);
            if (nome.contains("mypass")){
                passwd = sb.toString();
            }else if (nome.contains("myuser")){
                user = sb.toString();
            }
        }finally {
            scanner.close();
        }

    }

    public class conection extends AsyncTask<Void, Void, Void> {
        String words;
        String pepe;
        String prpr;
        String topr;
        String teste;

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
                System.out.println(jsonObj);

                Connection.Response loginForm = Jsoup.connect("https://siga.cps.sp.gov.br/aluno/login.aspx")
                        .method(Connection.Method.GET)
                        .execute();

                Document doc = Jsoup.connect("https://siga.cps.sp.gov.br/aluno/login.aspx")
                        .data("vSIS_USUARIOID", user)
                        .data("vSIS_USUARIOSENHA", passwd)
                        .data("GXState", jsonObj.toString())
                        .cookies(loginForm.cookies())
                        .post();

                Connection connection = Jsoup.connect(url)
                        .cookies(loginForm.cookies())
                        .method(Connection.Method.GET);
                Connection.Response response = connection.execute();

                doc = response.parse();
                words = doc.select("span#span_MPW0041vPRO_PESSOALNOME").first().text();
                pepe = doc.select("span#span_MPW0041vACD_ALUNOCURSOINDICEPP").first().text();
                prpr = doc.select("span#span_MPW0041vACD_ALUNOCURSOINDICEPR").first().text();
                topr = doc.select("span#span_MPW0041vMAX_ACD_ALUNOCURSOINDICEPR").first().text();
                teste = doc.select("div#MPW0041FOTO").select("img").first().attr("src");
                avisos = doc;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            nome.setText(words);
            foto.setImageUrl(teste);
            pp.setText(pepe);
            pr.setText(prpr);
            maiorpr.setText(topr);
            av.setText(new clear().getPlainText(avisos.select("span#span_vTEXTO")));
        }
    }

}