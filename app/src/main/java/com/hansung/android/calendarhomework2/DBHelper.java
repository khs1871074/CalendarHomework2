package com.hansung.android.calendarhomework2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="SQLiteDBTest";

    public DBHelper(Context context) {
        super(context, UserContract.DB_NAME, null, UserContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(UserContract.Users.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(UserContract.Users.DELETE_TABLE);
        onCreate(db);
    }

    public void insertUserBySQL(String title, String year, String month, String day,
                                String sth, String stm, String eth, String etm,
                                String location, String memo) {
        try {
            String sql = String.format (
                    "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    UserContract.Users.KEY_TIME_TITLE,
                    UserContract.Users.KEY_YEAR,
                    UserContract.Users.KEY_MONTH,
                    UserContract.Users.KEY_DAY,
                    UserContract.Users.KEY_TIME_START_HOUR,
                    UserContract.Users.KEY_TIME_START_MINUTE,
                    UserContract.Users.KEY_TIME_END_HOUR,
                    UserContract.Users.KEY_TIME_END_MINUTE,
                    UserContract.Users.KEY_TIME_LOCATION,
                    UserContract.Users.KEY_TIME_MEMO,
                    title,
                    year,
                    month,
                    day,
                    sth,
                    stm,
                    eth,
                    etm,
                    location,
                    memo);

            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in inserting recodes");
        }
    }

    public Cursor getAllUsersBySQL() {
        String sql = "Select * FROM " + UserContract.Users.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql,null);
    }

    public void deleteUserBySQL(String id) {
        try {
            String sql = String.format (
                    "DELETE FROM %s WHERE %s = %s",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users._ID,
                    id);
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in deleting recodes");
        }
    }

    public void updateUserBySQL(String _id, String name, String phone) {
        try {
            String sql = String.format (
                    "UPDATE  %s SET %s = '%s', %s = '%s' WHERE %s = %s",
                    UserContract.Users.TABLE_NAME,
                    UserContract.Users.KEY_TIME_TITLE,
                    UserContract.Users.KEY_TIME_START_HOUR,
                    UserContract.Users.KEY_TIME_START_MINUTE,
                    UserContract.Users.KEY_TIME_END_HOUR,
                    UserContract.Users.KEY_TIME_END_MINUTE,
                    UserContract.Users.KEY_TIME_LOCATION,
                    UserContract.Users.KEY_TIME_MEMO,
                    UserContract.Users._ID, _id) ;
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG,"Error in updating recodes");
        }
    }

    public long insertUserByMethod(String name, String phone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.Users.KEY_TIME_TITLE,phone);
        values.put(UserContract.Users.KEY_TIME_START_HOUR,phone);
        values.put(UserContract.Users.KEY_TIME_START_MINUTE,phone);
        values.put(UserContract.Users.KEY_TIME_END_HOUR,phone);
        values.put(UserContract.Users.KEY_TIME_END_MINUTE,phone);
        values.put(UserContract.Users.KEY_TIME_LOCATION,phone);
        values.put(UserContract.Users.KEY_TIME_MEMO,phone);

        return db.insert(UserContract.Users.TABLE_NAME,null,values);
    }

    public Cursor getAllUsersByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(UserContract.Users.TABLE_NAME,null,null,null,null,null,null);
    }

    public long deleteUserByMethod(String _id) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = UserContract.Users._ID +" = ?";
        String[] whereArgs ={_id};
        return db.delete(UserContract.Users.TABLE_NAME, whereClause, whereArgs);
    }

    public long updateUserByMethod(String _id, String name, String phone) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.Users.KEY_TIME_TITLE,phone);
        values.put(UserContract.Users.KEY_TIME_START_HOUR,phone);
        values.put(UserContract.Users.KEY_TIME_START_MINUTE,phone);
        values.put(UserContract.Users.KEY_TIME_END_HOUR,phone);
        values.put(UserContract.Users.KEY_TIME_END_MINUTE,phone);
        values.put(UserContract.Users.KEY_TIME_LOCATION,phone);
        values.put(UserContract.Users.KEY_TIME_MEMO,phone);

        String whereClause = UserContract.Users._ID +" = ?";
        String[] whereArgs ={_id};

        return db.update(UserContract.Users.TABLE_NAME, values, whereClause, whereArgs);
    }

}