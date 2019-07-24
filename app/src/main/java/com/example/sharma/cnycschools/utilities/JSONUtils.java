
//region Import Namespace
package com.example.sharma.cnycschools.utilities;

import android.content.ContentValues;
import android.util.Log;

import com.example.sharma.cnycschools.data.NYCSchoolContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//endregion


public class JSONUtils {

    //region Variables
    private static final String CLASS_NAME = JSONUtils.class.getSimpleName();
    //endregion

    //region Public Methods
    public static ContentValues[] parseSchoolListResponse(String json) {
        try {
            if (json == "" || json.isEmpty()) return null;
            JSONArray results = new JSONArray(json);
            if (results == null || results.length() <= 0) return null;
            ContentValues[] schoolContentValues = new ContentValues[results.length()];
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = (JSONObject) results.get(i);
                ContentValues contentValue = new ContentValues();
                contentValue.put(NYCSchoolContract.NYCSchoolList.DBN, result.has(NYCSchoolContract.NYCSchoolList.DBN) ? result.getString(NYCSchoolContract.NYCSchoolList.DBN) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.SCHOOL_NAME, result.has(NYCSchoolContract.NYCSchoolList.SCHOOL_NAME) ? result.getString(NYCSchoolContract.NYCSchoolList.SCHOOL_NAME) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.SCHOOL_OVERVIEW, result.has(NYCSchoolContract.NYCSchoolList.SCHOOL_OVERVIEW) ? result.getString(NYCSchoolContract.NYCSchoolList.SCHOOL_OVERVIEW) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.PHONE_NUMBER, result.has(NYCSchoolContract.NYCSchoolList.PHONE_NUMBER) ? result.getString(NYCSchoolContract.NYCSchoolList.PHONE_NUMBER) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.EMAIL_ADDRESS, result.has(NYCSchoolContract.NYCSchoolList.EMAIL_ADDRESS) ? result.getString(NYCSchoolContract.NYCSchoolList.EMAIL_ADDRESS) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.TOTAL_STUDENT, result.has(NYCSchoolContract.NYCSchoolList.TOTAL_STUDENT) ? result.getInt(NYCSchoolContract.NYCSchoolList.TOTAL_STUDENT) : 0);
                contentValue.put(NYCSchoolContract.NYCSchoolList.START_TIME, result.has(NYCSchoolContract.NYCSchoolList.START_TIME) ? result.getString(NYCSchoolContract.NYCSchoolList.START_TIME) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.END_TIME, result.has(NYCSchoolContract.NYCSchoolList.END_TIME) ? result.getString(NYCSchoolContract.NYCSchoolList.END_TIME) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.WEBSITE, result.has(NYCSchoolContract.NYCSchoolList.WEBSITE) ? result.getString(NYCSchoolContract.NYCSchoolList.WEBSITE) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.ADDRESS, result.has(NYCSchoolContract.NYCSchoolList.ADDRESS) ? result.getString(NYCSchoolContract.NYCSchoolList.ADDRESS) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.CITY, result.has(NYCSchoolContract.NYCSchoolList.CITY) ? result.getString(NYCSchoolContract.NYCSchoolList.CITY) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.ZIP, result.has(NYCSchoolContract.NYCSchoolList.ZIP) ? result.getInt(NYCSchoolContract.NYCSchoolList.ZIP) : 0);
                contentValue.put(NYCSchoolContract.NYCSchoolList.STATE_CODE, result.has(NYCSchoolContract.NYCSchoolList.STATE_CODE) ? result.getString(NYCSchoolContract.NYCSchoolList.STATE_CODE) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolList.LATITUDE, result.has(NYCSchoolContract.NYCSchoolList.LATITUDE) ? result.getDouble(NYCSchoolContract.NYCSchoolList.LATITUDE) : 0.0);
                contentValue.put(NYCSchoolContract.NYCSchoolList.LONGITUDE, result.has(NYCSchoolContract.NYCSchoolList.LONGITUDE) ? result.getDouble(NYCSchoolContract.NYCSchoolList.LONGITUDE) : 0.0);
                schoolContentValues[i] = contentValue;
            }
            return schoolContentValues;
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "parseSchoolListResponse(String json) - " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static ContentValues[] parseSchoolDetailResponse(String json) {
        try {
            if (json == "" || json.isEmpty()) return null;
            JSONArray results = new JSONArray(json);
            if (results == null || results.length() <= 0) return null;
            ContentValues[] schoolContentValues = new ContentValues[results.length()];
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = (JSONObject) results.get(i);
                ContentValues contentValue = new ContentValues();
                contentValue.put(NYCSchoolContract.NYCSchoolDetail.DBN, result.has(NYCSchoolContract.NYCSchoolDetail.DBN) ? result.getString(NYCSchoolContract.NYCSchoolDetail.DBN) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolDetail.SCHOOL_NAME, result.has(NYCSchoolContract.NYCSchoolDetail.SCHOOL_NAME) ? result.getString(NYCSchoolContract.NYCSchoolDetail.SCHOOL_NAME) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolDetail.TOTAL_SAT_TAKER, result.has(NYCSchoolContract.NYCSchoolDetail.TOTAL_SAT_TAKER) ? result.getString(NYCSchoolContract.NYCSchoolDetail.TOTAL_SAT_TAKER) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolDetail.READING_SAT_SCORE, result.has(NYCSchoolContract.NYCSchoolDetail.READING_SAT_SCORE) ? result.getString(NYCSchoolContract.NYCSchoolDetail.READING_SAT_SCORE) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolDetail.MATH_SAT_SCORE, result.has(NYCSchoolContract.NYCSchoolDetail.MATH_SAT_SCORE) ? result.getString(NYCSchoolContract.NYCSchoolDetail.MATH_SAT_SCORE) : "");
                contentValue.put(NYCSchoolContract.NYCSchoolDetail.WRITING_SAT_SCORE, result.has(NYCSchoolContract.NYCSchoolDetail.WRITING_SAT_SCORE) ? result.getString(NYCSchoolContract.NYCSchoolDetail.WRITING_SAT_SCORE) : "");
                schoolContentValues[i] = contentValue;
            }
            return schoolContentValues;
        } catch (JSONException e) {
            Log.e(CLASS_NAME, "parseSchoolDetailResponse(String json) - " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    //endregion
}
