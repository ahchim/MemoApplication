package com.ahchim.android.memoapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ahchim.android.memoapp.controller.DBController;
import com.ahchim.android.memoapp.controller.PermissionControl;
import com.ahchim.android.memoapp.domain.Memo;
import com.ahchim.android.memoapp.interfaces.ListInterface;
import com.ahchim.android.memoapp.interfaces.RTInterface;
import com.ahchim.android.memoapp.interfaces.WriteInterface;
import com.onegravity.rteditor.RTManager;
import com.onegravity.rteditor.api.RTApi;
import com.onegravity.rteditor.api.RTMediaFactoryImpl;
import com.onegravity.rteditor.api.RTProxyImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListInterface, WriteInterface, RTInterface {
    private final int REQ_PERMISSION = 100; // 권한 요청코드
    private final int REQ_CAMERA = 101;     // 카메라 요청코드
    private final int REQ_GALLERY = 102;    // 갤러리 요청코드

    ListFragment listFrag;
    WriteFragment writeFrag;

    FragmentManager manager;

    // create RTManager
    RTApi rtApi;
    RTManager rtManager;

    DBController dataControl = null;

    List<Memo> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            setContentView(R.layout.activity_main);
            rtApi = new RTApi(this, new RTProxyImpl(this), new RTMediaFactoryImpl(this, true));
            rtManager = new RTManager(rtApi, savedInstanceState);
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

        checkPermission();
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
        Bundle bundle = new Bundle();
        bundle.putInt("position", 0);
        bundle.putString("editText" + 0, null);
        writeFrag.setArguments(bundle);

        FragmentTransaction fragTrans = manager.beginTransaction();
        fragTrans.add(R.id.activity_main, writeFrag);
        fragTrans.addToBackStack(null);
        fragTrans.commit();
    }

    @Override
    public void goWrite(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("editText" + position, datas.get(position).getContent());
        writeFrag.setArguments(bundle);

        FragmentTransaction fragTrans = manager.beginTransaction();
        fragTrans.add(R.id.activity_main, writeFrag);
        fragTrans.addToBackStack(null);
        fragTrans.commit();
    }

    @Override
    public void backToList() {
        super.onBackPressed();
    }

    @Override
    public void saveToList(Memo memo) throws SQLException {
        dataControl = new DBController(this);
        dataControl.setData(memo);

        loadData();
        listFrag.setData(datas);
        super.onBackPressed();

        listFrag.refreshAdapter();
    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // 프로그램 실행
            if(PermissionControl.checkPermission(this, REQ_PERMISSION)){
                // do it!
            }
        } else {
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        rtManager.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        rtManager.onDestroy(false);
    }

    @Override
    public RTManager getRTManager() {
        return rtManager;
    }

    @Override
    public ListFragment getListFrag() { return listFrag; }
}