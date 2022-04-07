package com.example.project_tu.sections;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.view.*;
import android.view.View.*;

import com.example.project_tu.R;

public class SectionAdapter extends BaseAdapter {
    private SectionList mListS;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;


    public SectionAdapter(Context context, SectionList aListS) {
        mContext = context;
        mListS = aListS;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return mListS.size();
    }

    public Object getItem(int position) {
        return mListS.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem;

        //(1) : Réutilisation des layouts
        if (convertView == null) {
            //Initialisation de notre item à partir du  layout XML ""
            layoutItem = (RelativeLayout) mInflater.inflate(R.layout.section_layout, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }

        //(2) : Récupération des TextView de notre layout
        TextView btnSection = (TextView) layoutItem.findViewById(R.id.btnSection);

        //(3) : Renseignement des valeurs
        btnSection.setText(mListS.get(position).getNom());

        btnSection.setTag(position);
        btnSection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer)v.getTag();
                sendListener(mListS.get(position), position);
            }
        });
        //On retourne l'item créé.
        return layoutItem;
    }

    //abonnement pour click sur le nom...
    private ArrayList<SectionAdapterListener> mListListener = new ArrayList<SectionAdapterListener>();
    public void addListener(SectionAdapterListener aListener) {
        mListListener.add(aListener);
    }
    private void sendListener(Section item, int position) {
        for(int i = mListListener.size()-1; i >= 0; i--) {
            mListListener.get(i).onClickNom(item, position);
        }
    }


    // Interface pour écouter les évènements sur le nom du diplome

    public interface SectionAdapterListener {
        public void onClickNom(Section item, int position);
    }
}
