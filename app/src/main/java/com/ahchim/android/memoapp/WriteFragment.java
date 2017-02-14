package com.ahchim.android.memoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ahchim.android.memoapp.domain.Memo;
import com.ahchim.android.memoapp.interfaces.WriteInterface;

import java.sql.SQLException;


public class WriteFragment extends Fragment implements View.OnClickListener {
    Context context = null;
    WriteInterface writeInterface = null;
    View view = null;

    int position = -1;

    ImageButton btnSave;
    ImageButton btnCancle;
    EditText editText;

    public WriteFragment() {
        // Required empty public constructor
    }

    public static WriteFragment newInstance() {
        WriteFragment fragment = new WriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null){
            view = inflater.inflate(R.layout.fragment_write, container, false);

            btnSave = (ImageButton) view.findViewById(R.id.btnSave);
            btnCancle = (ImageButton) view.findViewById(R.id.btnCancle);
            editText = (EditText) view.findViewById(R.id.editText);

            btnSave.setOnClickListener(this);
            btnCancle.setOnClickListener(this);

            return view;
        }
        else return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSave:
                try{
                    Memo memo = makeMemo();
                    writeInterface.saveToList(memo);
                }catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            case R.id.btnCancle:
                writeInterface.backToList();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.writeInterface = (WriteInterface) context;
    }


    private Memo makeMemo(){
        return new Memo(editText.getText().toString());
    }
}
