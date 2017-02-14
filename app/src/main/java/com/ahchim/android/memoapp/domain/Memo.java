package com.ahchim.android.memoapp.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Ahchim on 2017-02-14.
 */
@DatabaseTable(tableName = "memo")
public class Memo {
    @DatabaseField(generatedId = true)      // id값 자동증가 옵션 > generatedId = true
    private int id;
    @DatabaseField
    private String content;
    @DatabaseField
    private Date editdate;

    Memo(){ }

    public Memo(String content){
        this.content = content;
        this.editdate = new Date(System.currentTimeMillis());
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void newEditdate(){
        this.editdate = new Date(System.currentTimeMillis());
    }

    public int getId() { return id; }

    public String getContent() {
        return content;
    }

    public Date getEditdate() {
        return editdate;
    }
}
