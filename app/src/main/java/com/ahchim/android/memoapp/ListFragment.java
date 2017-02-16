package com.ahchim.android.memoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.ahchim.android.memoapp.domain.Memo;
import com.ahchim.android.memoapp.interfaces.ListInterface;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private View view = null;
    private Context context;

    private RecyclerView recyclerView;
    public ListAdapter listAdapter;

    private ListInterface listInterface = null;

    private ImageButton btnPlus;

    private List<Memo> datas = new ArrayList<>();

    public ListFragment(){ }

    @SuppressWarnings("unused")
    public static ListFragment newInstance(int columnCount) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.layout_list, container, false);

            recyclerView = (RecyclerView) view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            listAdapter = new ListAdapter(context, datas);
            recyclerView.setAdapter(listAdapter);

            btnPlus = (ImageButton) view.findViewById(R.id.btnPlus);
            btnPlus.setOnClickListener(this);

            return view;
        }
        else return view;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnPlus:
                //if(listAdapter.getItemCount() > 0){
                // listInterface.goWrite(listAdapter.getItemCount()-1);
                //} else{
                // 메인의 goWrite 함수를 호출한다.
                listInterface.goWrite();
                //}
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.listInterface = (ListInterface) context;
    }

    public void refreshAdapter(){
        listAdapter = new ListAdapter(context, datas);
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setData(List<Memo> datas) {
        this.datas = datas;
    }
}
