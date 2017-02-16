package com.ahchim.android.memoapp.interfaces;

import com.ahchim.android.memoapp.ListFragment;

/**
 * Created by Administrator on 2017-02-14.
 */

public interface ListInterface {
    public void goWrite();
    public void goWrite(int position);
    public ListFragment getListFrag();
}
