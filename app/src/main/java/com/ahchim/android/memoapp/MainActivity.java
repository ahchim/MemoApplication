package com.ahchim.android.memoapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ahchim.android.memoapp.Controller.DBController;
import com.ahchim.android.memoapp.data.DBHelper;
import com.ahchim.android.memoapp.domain.Memo;
import com.ahchim.android.memoapp.interfaces.ListInterface;
import com.ahchim.android.memoapp.interfaces.WriteInterface;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListInterface, WriteInterface{
    ListFragment listFrag;
    WriteFragment writeFrag;
    FragmentManager manager;

    DBController dataControl;

    List<Memo> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            setContentView(R.layout.activity_main);
        } else return;

        listFrag = ListFragment.newInstance(1);
        writeFrag = WriteFragment.newInstance();

        manager = getSupportFragmentManager();

        try {
            loadData();
        } catch (SQLException e) {
            Log.e("", e + "");
            e.printStackTrace();
        }
        listFrag.setData(datas);
        setList();
    }

    public void loadData() throws SQLException{
        dataControl = new DBController(this);
        datas = dataControl.getDataList();
    }


    private void setList() {
        FragmentTransaction fragTrans = manager.beginTransaction();
        fragTrans.add(R.id.activity_main, listFrag);
        fragTrans.commit();
    }

    @Override
    public void goWrite() {
        FragmentTransaction fragTrans = manager.beginTransaction();
        fragTrans.add(R.id.activity_main, writeFrag);
        fragTrans.addToBackStack(null);
        fragTrans.commit();
    }

    @Override
    public void goWrite(int position) {

    }

    @Override
    public void backToList() {
        super.onBackPressed();
    }

    @Override
    public void saveToList(Memo memo) throws SQLException {
        dataControl.setData(memo);

        loadData();
        listFrag.setData(datas);
        super.onBackPressed();

        listFrag.refreshAdapter();
    }
}
