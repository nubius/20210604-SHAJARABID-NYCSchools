

//region Import Namespaces
package com.example.sharma.cnycschools.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.sharma.cnycschools.data.NYCSchoolContract;
import com.example.sharma.cnycschools.utilities.JSONUtils;
import com.example.sharma.cnycschools.utilities.NYCSchoolUtils;

import java.net.URL;
//endregion

public class NYCSchoolListSyncTask {

    synchronized public static void syncNYCSchoolData(Context context, boolean isSchoolList) {
        try {
            URL url = NYCSchoolUtils.buildSchoolListOrDetailURL(isSchoolList);

            String jsonOpenAirResponse = NYCSchoolUtils.getResponseFromHttpUrl(url);

            ContentValues[] contentValues = isSchoolList ? JSONUtils.parseSchoolListResponse(jsonOpenAirResponse) : JSONUtils.parseSchoolDetailResponse(jsonOpenAirResponse);

            if (contentValues != null && contentValues.length > 0) {
                // build ContextResolver to deleted old data and insert New Data
                ContentResolver contentResolver = context.getContentResolver();

                // delete Old NYC SCHOOL Data
                Uri content_URI = isSchoolList ? NYCSchoolContract.NYCSchoolList.CONTENT_URI : NYCSchoolContract.NYCSchoolDetail.CONTENT_URI;
                contentResolver.delete(content_URI, null, null);

                // Insert New Data
                contentResolver.bulkInsert(content_URI, contentValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(NYCSchoolListSyncTask.class.getSimpleName(), "syncNYCSchoolData(Context context, boolean isSchoolList) - " + e.getMessage());
        }
    }
}
