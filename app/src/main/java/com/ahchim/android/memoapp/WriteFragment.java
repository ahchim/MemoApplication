package com.ahchim.android.memoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ahchim.android.memoapp.domain.Memo;
import com.ahchim.android.memoapp.interfaces.ListInterface;
import com.ahchim.android.memoapp.interfaces.RTInterface;
import com.ahchim.android.memoapp.interfaces.WriteInterface;
import com.onegravity.rteditor.RTEditText;
import com.onegravity.rteditor.RTManager;
import com.onegravity.rteditor.RTToolbar;
import com.onegravity.rteditor.api.format.RTFormat;
import com.onegravity.rteditor.api.format.RTText;

import java.sql.SQLException;

public class WriteFragment extends Fragment implements View.OnClickListener {
    Context context = null;
    WriteInterface writeInterface = null;
    ListInterface listInterface = null;
    RTInterface rtInterface = null;
    View view = null;

    int position = -1;
    static String editTextArgu;

    ImageButton btnSave;
    ImageButton btnCancle;
    EditText editText;
    RTEditText rtEditText;

    // register toolbar
    ViewGroup toolbarContainer;
    RTToolbar rtToolbar;

    public WriteFragment() {
        // Required empty public constructor
    }

    public static WriteFragment newInstance() {
        WriteFragment fragment = new WriteFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.writeInterface = (WriteInterface) context;
        this.listInterface = (ListInterface) context;
        this.rtInterface = (RTInterface) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            position = getArguments().getInt("position");
            editTextArgu = getArguments().getString("editText" + position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null){
            view = inflater.inflate(R.layout.fragment_write, container, false);

            toolbarContainer = (ViewGroup) view.findViewById(R.id.rte_toolbar_container);
            rtToolbar = (RTToolbar) view.findViewById(R.id.rte_toolbar);
            if (rtToolbar != null) {
                rtInterface.getRTManager().registerToolbar(toolbarContainer, rtToolbar);
            }

            btnSave = (ImageButton) view.findViewById(R.id.btnSave);
            btnCancle = (ImageButton) view.findViewById(R.id.btnCancle);
            editText = (EditText) view.findViewById(R.id.editText);
            rtEditText = (RTEditText) view.findViewById(R.id.rtEditText);

            rtInterface.getRTManager().registerEditor(rtEditText, true);

            btnSave.setOnClickListener(this);
            btnCancle.setOnClickListener(this);

            Log.i("Memo writeFrag","editTextargu============================================="+editTextArgu);
            editText.setText(editTextArgu);
            rtEditText.setRichTextEditing(true, editTextArgu);

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
    public void onResume(){
        super.onResume();
        Log.i("writeFragment", "rtEditText toText===============\n" + editTextArgu + "\n==================");
        // 플러스가 눌리면 editText 화면 초기화
        if(listInterface.getListFrag().btnPlusClicked == true){
            editText.setText("");
            rtEditText.setRichTextEditing(true, "");

            listInterface.getListFrag().btnPlusClicked = false;
        }
        // editText에 들어온 텍스트값이 있을 때
        else if(editTextArgu != null) {
            editText.setText(editTextArgu);
            rtEditText.setRichTextEditing(true, editTextArgu);
        }
    }

    private Memo makeMemo(){
        Log.i("writeFragment", "rtEditText content\n" + rtEditText.getText(RTFormat.HTML));
        return new Memo(rtEditText.getText(RTFormat.HTML));
    }
}
