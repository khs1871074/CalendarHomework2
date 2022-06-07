package com.hansung.android.calendarhomework2;

import android.provider.BaseColumns;

public final class UserContract {
    public static final String DB_NAME="user.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserContract() {}

    /* Inner class that defines the table contents */
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME= "Users";
        public static final String KEY_TIME_TITLE = "TITLE";
        public static final String KEY_YEAR = "YEAR";
        public static final String KEY_MONTH = "MONTH";
        public static final String KEY_DAY = "DAY";
        public static final String KEY_TIME_START_HOUR = "TIME_START_HOUR";
        public static final String KEY_TIME_START_MINUTE = "TIME_START_MINUTE";
        public static final String KEY_TIME_END_HOUR = "TIME_END_HOUR";
        public static final String KEY_TIME_END_MINUTE = "TIME_END_MINUTE";
        public static final String KEY_TIME_LOCATION = "LOCATION";
        public static final String KEY_TIME_MEMO = "MEMO";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_TIME_TITLE + TEXT_TYPE + COMMA_SEP +
                KEY_YEAR + TEXT_TYPE + COMMA_SEP +
                KEY_MONTH + TEXT_TYPE + COMMA_SEP +
                KEY_DAY + TEXT_TYPE + COMMA_SEP +
                KEY_TIME_START_HOUR + TEXT_TYPE + COMMA_SEP +
                KEY_TIME_START_MINUTE + TEXT_TYPE + COMMA_SEP +
                KEY_TIME_END_HOUR + TEXT_TYPE + COMMA_SEP +
                KEY_TIME_END_MINUTE + TEXT_TYPE + COMMA_SEP +
                KEY_TIME_LOCATION + TEXT_TYPE + COMMA_SEP +
                KEY_TIME_MEMO + TEXT_TYPE +  " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
