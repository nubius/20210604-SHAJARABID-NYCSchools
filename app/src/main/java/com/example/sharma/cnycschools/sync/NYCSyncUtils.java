
//region Using Namespaces
package com.example.sharma.cnycschools.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.sharma.cnycschools.data.NYCSchoolContract;
//endregion


public class NYCSyncUtils {

    //region Variables
    private static boolean sInitializedSchoolList;
    private static boolean sInitializedSchoolData;
    private static final String CLASS_NAME = NYCSyncUtils.class.getSimpleName();
    //endregion

    synchronized public static void initialize(@NonNull final Context context) {
        try {
            // execute this method only once in app life time.
            if (sInitializedSchoolList) return;
            sInitializedSchoolList = true;

            // check data exist for content provider. call it in separate thread.
            Thread checkForEmpty = new Thread(new Runnable() {
                @Override
                public void run() {

                /* URI for every row of weather data in our weather table*/
                    Uri contentURI = NYCSchoolContract.NYCSchoolList.CONTENT_URI;

                    String[] projectionColumns = {NYCSchoolContract.NYCSchoolList._ID};

                    Cursor cursor = context.getContentResolver().query(
                            contentURI,
                            projectionColumns,
                            null,
                            null,
                            null);

                    // if cursor null  syncdata
                    if (null == cursor || cursor.getCount() == 0)
                        startImmediateSync(context, true);

                    // close the cursor.
                    if (null != cursor) cursor.close();
                }
            });
            // once the thread is prepared, start it
            checkForEmpty.start();
        } catch (SQLiteException e) {
            Log.e(CLASS_NAME, "initialize(@NonNull final Context context)" + e.getMessage());
        }
    }

    synchronized public static void initializeDetail(@NonNull final Context context) {
        try {
            // execute this method only once in app life time.
            if (sInitializedSchoolData) return;
            sInitializedSchoolData = true;

            // check data exist for content provider. call it in separate thread.
            Thread checkForEmpty = new Thread(new Runnable() {
                @Override
                public void run() {

                /* URI for every row of weather data in our weather table*/
                    Uri contentURI = NYCSchoolContract.NYCSchoolDetail.CONTENT_URI;
                    String[] projectionColumns = {NYCSchoolContract.NYCSchoolDetail._ID};
                    Cursor cursor = context.getContentResolver().query(
                            contentURI,
                            projectionColumns,
                            null,
                            null,
                            null);

                    // if cursor null sync data
                    if (null == cursor || cursor.getCount() == 0)
                        startImmediateSync(context, false);

                    // close the cursor.
                    if (null != cursor) cursor.close();
                }
            });
            // once the thread is prepared, start it
            checkForEmpty.start();
        } catch (SQLiteException e) {
            Log.e(CLASS_NAME, "initializeDetail(@NonNull final Context context) " + e.getMessage());
        }
    }

    public static void startImmediateSync(@NonNull final Context context, boolean isSchoolList) {
        try {
            Intent intentToSyncImmediately = new Intent(context, NYCSchoolListIntentService.class);
            intentToSyncImmediately.putExtra(Intent.EXTRA_TEXT, isSchoolList);
            context.startService(intentToSyncImmediately);
        } catch (SQLiteException e) {
            Log.e(CLASS_NAME, "startImmediateSync(@NonNull final Context context, boolean isSchoolList)" + e.getMessage());
        }
    }
}


