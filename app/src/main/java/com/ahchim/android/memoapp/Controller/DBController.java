package com.ahchim.android.memoapp.Controller;

import android.content.Context;

import com.ahchim.android.memoapp.data.DBHelper;
import com.ahchim.android.memoapp.domain.Memo;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ahchim on 2017-02-14.
 */

public class DBController {
    private DBHelper dbHelper = null;
    private Dao<Memo, Long> memoDao = null;

    public DBController(Context context) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        memoDao = dbHelper.getMemoDao();
    }

    public List<Memo> getDataList() throws SQLException {
        if(memoDao != null) {
            return memoDao.queryForAll();
        } else return null;
    }

    public void setData(Memo memo) throws SQLException {
        memoDao.create(memo);
    }
}
