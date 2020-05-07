package com.example.siga;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.loopj.android.image.SmartImageView;

import java.io.IOException;

public class home extends Fragment {
    String name;
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

        return view;
    }

    public void setNome(String ovo){
        name = ovo;
    }
}
