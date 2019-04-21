package com.example.awogni.carnetdenotes;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.carnet2notesmetier.Carnet;

import java.util.zip.Inflater;

/**
 * Created by Awogni on 18/03/2018.
 */

public class FragmentDate extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);



        View v = inflater.inflate(R.layout.fragment_date_layout,container,false);

        Button validation = (Button) v.findViewById(R.id.btnValiderDate);
        final DatePicker date = (DatePicker)v.findViewById(R.id.date_Picker);

        validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int J = date.getDayOfMonth() ;
                int M = date.getMonth()+1; // +1 car les mois sont numéroté de 0 à 11;
                int A = date.getYear();

                String jour = J+""; if (jour.length()==1){jour = "0"+jour ;}
                String mois = M+""; if (mois.length()==1){mois = "0"+mois ;}

                String  dateExam = jour+"/"+mois+"/"+A ;
                AjoutNoteActivity.setDate(dateExam);

            }
        });
        return v ;



    }
}
