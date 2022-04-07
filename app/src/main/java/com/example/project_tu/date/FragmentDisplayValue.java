package com.example.project_tu.date;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_tu.R;

public class FragmentDisplayValue extends Fragment {
    private final String TAG = "Tu " + getClass().getSimpleName();
    private String resultValue;
    TextView dateTxt;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //récupération de la valeur de la table
        Bundle arguments = getArguments();
        if(arguments!=null)  {
            resultValue = getArguments().getString("value");
        }
        Log.d(TAG, "FragmentDisplayValue getArguments()="+getArguments());
    }
    View rootView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.date_fragment_down, null);
        dateTxt=rootView.findViewById(R.id.dateTxt);
        dateTxt.setText(resultValue);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/FredokaOneRegular.ttf");
        dateTxt.setTypeface(typeface);
        return rootView;
    }

}