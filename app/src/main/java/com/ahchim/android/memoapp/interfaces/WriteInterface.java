package com.ahchim.android.memoapp.interfaces;

import com.ahchim.android.memoapp.domain.Memo;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017-02-14.
 */

public interface WriteInterface {
    public void backToList();
    public void saveToList(Memo memo) throws SQLException;
}
