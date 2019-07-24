
//region Using Namespaces
package com.example.sharma.cnycschools.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//endregion


public class NYCSchoolDBHelper extends SQLiteOpenHelper {

    //region Constants
    private static final String CLASS_NAME = NYCSchoolDBHelper.class.getSimpleName();
    private static final String DB_NAME = "nycschool.db";
    public static final int VERSION_NUMBER = 2;
    //endregion


    //region Constructor
    public NYCSchoolDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION_NUMBER);
    }
    //endregion


    //region Override
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {

            final String SQL_NYC_SCHOOL_LIST_TABLE = "CREATE TABLE " +
                    NYCSchoolContract.NYCSchoolList.TABLE_NAME + "( " +
                    NYCSchoolContract.NYCSchoolList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NYCSchoolContract.NYCSchoolList.DBN + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.SCHOOL_NAME + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.SCHOOL_OVERVIEW + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.PHONE_NUMBER + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.EMAIL_ADDRESS + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.TOTAL_STUDENT + " INTEGER NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.START_TIME + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.END_TIME + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.WEBSITE + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.ADDRESS + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.CITY + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.ZIP + " INTEGER NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.STATE_CODE + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolList.LATITUDE + " REAL," +
                    NYCSchoolContract.NYCSchoolList.LONGITUDE + " REAL" +
                    " );";

            final String SQL_NYC_SCHOOL_DETAIL_TABLE = "CREATE TABLE " +
                    NYCSchoolContract.NYCSchoolDetail.TABLE_NAME + "( " +
                    NYCSchoolContract.NYCSchoolDetail._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NYCSchoolContract.NYCSchoolDetail.DBN + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolDetail.SCHOOL_NAME + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolDetail.TOTAL_SAT_TAKER + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolDetail.READING_SAT_SCORE + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolDetail.MATH_SAT_SCORE + " TEXT NOT NULL," +
                    NYCSchoolContract.NYCSchoolDetail.WRITING_SAT_SCORE + " TEXT NOT NULL" +
                    " );";

            sqLiteDatabase.execSQL(SQL_NYC_SCHOOL_LIST_TABLE);
            sqLiteDatabase.execSQL(SQL_NYC_SCHOOL_DETAIL_TABLE);
        } catch (SQLException e) {
            Log.e(CLASS_NAME, "onCreate(SQLiteDatabase sqLiteDatabase) - " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NYCSchoolContract.NYCSchoolList.CONTENT_URI);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NYCSchoolContract.NYCSchoolDetail.CONTENT_URI);
            onCreate(sqLiteDatabase);

        } catch (SQLException e) {
            Log.e(CLASS_NAME, "onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) - " + e.getMessage());
            e.printStackTrace();
        }
    }
    //endregion
}
