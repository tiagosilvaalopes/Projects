package com.example.hospitalapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hospitalapp.services.GlobalProvider;

public class CheckInPopUp extends DialogFragment {

    private Button doneButton;
    private Button cancelButton;
    private Spinner corSenha;
    private EditText numSenha;
    private GlobalProvider globalProvider;
    private ArrayAdapter<CharSequence> adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        globalProvider = GlobalProvider.getInstance();
        View view = inflater.inflate(R.layout.checkin_popup, container, false);
        corSenha = view.findViewById(R.id.corSenha);
        numSenha = view.findViewById(R.id.numSenha);

        adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.array_cor_senha, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        corSenha.setAdapter(adapter);

        doneButton = view.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                concludeCheckIn(view);
            }
        });

        cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                cancelCheckIn(view);
            }
        });

        return view;
    }

    public void concludeCheckIn(View view){
        this.globalProvider.setCorSenha(this.corSenha.getSelectedItem().toString());

        String value= this.numSenha.getText().toString();
        int num =Integer.parseInt(value);
        this.globalProvider.setNumSenha(num);

        this.dismiss();
    }

    public void cancelCheckIn(View view){
        this.globalProvider.checkoutHospital();

        this.dismiss();
    }
}
