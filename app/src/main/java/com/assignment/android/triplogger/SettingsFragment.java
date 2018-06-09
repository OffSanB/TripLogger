package com.assignment.android.triplogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment {
    private Settings settings;
    private Spinner mgender;
    private Button bSave;
    private Button bClear;
    private EditText name;
    private EditText email;
    private TextView id;
    private EditText comment;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Settings");
        setHasOptionsMenu(true);


        if(SettingsKeeper.get(getActivity()).getSettings()==null){
            settings =createSettings();
        }else{
            settings=SettingsKeeper.get(getActivity()).getSettings();
        }

    }

    public void onPause(){
        super.onPause();

        SettingsKeeper.get(getActivity()).updateSettings(settings);
    }

    private Settings createSettings(){
    Settings s=new Settings();
    s.setGender(1);
    s.setName("John Doe");
    s.setEmail("John@doe.com");
    s.setComment("This is the comment Section");
    SettingsKeeper.get(getActivity()).addSettings(s);
    return SettingsKeeper.get(getActivity()).getSettings();

}



    public View onCreateView(LayoutInflater inflater,
                             ViewGroup conatainer,
                             Bundle onSavedInstanceState){
        View v = inflater.inflate(R.layout.fragment_settings,conatainer,false);


        mgender=(Spinner)v.findViewById(R.id.gender);
        ArrayAdapter<String> myAdapter =new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.genders));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mgender.setAdapter(myAdapter);
        mgender.setSelection(settings.getGender());
        mgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                settings.setGender(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        name=(EditText) v.findViewById(R.id.settings_name);
        name.setText(settings.getName());
        name.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after){
                //No need to do anything
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count){
                settings.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s){
                //No need to do anything
            }
        });
        email=(EditText)v.findViewById(R.id.email);
        email.setText(settings.getEmail());
        email.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after){
                //No need to do anything
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count){
                settings.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s){
                //No need to do anything
            }
        });
        id=(TextView) v.findViewById(R.id.id);
        id.setText(settings.getId().toString());
        bSave=(Button)v.findViewById(R.id.settings_save_button);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(),MainActivity.class);
                Toast.makeText(getActivity(), "Settings Saved", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        bClear=(Button)v.findViewById(R.id.settings_clear_button);
        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),MainActivity.class);
                Toast.makeText(getActivity(), "Settings Cleared", Toast.LENGTH_SHORT).show();

               if(settings!=null){
                   SettingsKeeper.get(getActivity()).deleteSettings(settings);
               }
                startActivity(i);
            }
        });
        comment=(EditText) v.findViewById(R.id.settings_comment);
        comment.setText(settings.getComment());
        comment.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after){
                //No need to do anything
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count){
                settings.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s){
                //No need to do anything
            }
        });

        return v;
    }
}
