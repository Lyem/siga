package com.example.siga;

import android.os.AsyncTask;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class network {
//rotas
    String home = "https://siga.cps.sp.gov.br/aluno/home.aspx";
    String notas = "https://siga.cps.sp.gov.br/aluno/historico.aspx";

//conection info
    String user, passwd, url;
    Document words = null;

    public Document getData(){
        new conection().execute();
        while (words == null) { }
        return words;
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
                words = doc;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            return;
        }
    }

    public void setUser(String userr){
        this.user = userr;
    }

    public void  setPasswd(String passwdd){
        this.passwd = passwdd;
    }

    public void setUrl(String urll){
        this.url = urll;
    }

}
