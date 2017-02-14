package com.ahchim.android.memoapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ahchim.android.memoapp.domain.Memo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Ahchim on 2017-02-14.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper{
    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;

    // 기본도 만들어줘야 한다.
    // 클래스로 Memo를 만들어서 저장하기 때문에, 맨 마지막 인자로 integer를 주고 데이터베이스 설정파일을 raw에다 저장하는 짓을 하지 않는다.
    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 생성자에서 호출되는 super(context... 에서 database.db 파일이 생성되어 있지 않으면 호출된다.
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            // Memo.class 파일에 정의된 테이블을 생성한다.
            TableUtils.createTable(connectionSource, Memo.class);
        } catch(SQLException e){     // java.sql.SQLException 이다.
            e.printStackTrace();
        }
    }

    /**
     *  생성자에서 호출되는 super(context...  에서 database.db 파일이 존재하지만 DB_VERSION이 증가되면 호출된다.
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    // 버전업할 때 내부에서 onCreate() 호출하는 방식으로 동작.
    // 나중에 입력받은 버전별로 분기처리를 해줘야 한다.
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            // Memo.class에 정의된 테이블 삭제
            TableUtils.dropTable(connectionSource, Memo.class, false);
            // 데이터를 보존해야 될 필요성이 있으면 중간처리를 해줘야만 한다.
            // TODO : 임시테이블을 생성한 후 데이터를 먼저 저장하고 onCreate 이후에 데이터를 입력해 준다.
            // onCreate를 호출해서 테이블을 생성해 준다.
            onCreate(database, connectionSource);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // DBHelper 를 싱글턴으로 사용하기 때문에 dao 객체도 열어놓고 사용가능하다.
    private Dao<Memo, Long> memoDao = null;

    public Dao<Memo, Long> getMemoDao() throws SQLException{
        if(memoDao == null){
            memoDao = getDao(Memo.class);
        }
        return memoDao;
    }

    public void releaseBbsDao(){
        if(memoDao != null){
            memoDao = null;
        }
    }
}
