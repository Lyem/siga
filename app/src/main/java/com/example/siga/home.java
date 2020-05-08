package com.example.siga;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.loopj.android.image.SmartImageView;

public class home extends Fragment {
    String name,prr,ppp,mpr,avv,fotourl;
    TextView nome;
    TextView pr;
    TextView pp;
    TextView maiorpr;
    TextView av;
    private SmartImageView foto;
    private home mListener;
    public home() {

    }


    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home2, container, false);
        foto = (SmartImageView)view.findViewById(R.id.perfil);
        pr = (TextView)view.findViewById(R.id.pr);
        pp = (TextView)view.findViewById(R.id.pp);
        maiorpr = (TextView)view.findViewById(R.id.toppr);
        av = (TextView)view.findViewById(R.id.aviso);
        nome = (TextView)view.findViewById(R.id.name);
        nome.setText(name);
        pr.setText(prr);
        pp.setText(ppp);
        maiorpr.setText(mpr);
        av.setText(avv);
        foto.setImageUrl(fotourl);
        return view;
    }

    public void setNome(String ovo){
        name = ovo;
        nome.setText(name);
    }

    public void setFotourl(String fu){
        fotourl = fu;
        foto.setImageUrl(fotourl);
    }

    public void setPpp(String pppp){
        ppp = pppp;
        pp.setText(ppp);
    }

    public void setPr(String prrr){
        prr = prrr;
        pr.setText(prr);
    }

    public void setMaiorpr(String mprr){
        mpr = mprr;
        maiorpr.setText(mpr);
    }

    public void setAv(String avvv){
        avv = avvv;
        av.setText(avv);
    }
}
