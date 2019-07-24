
//region Using Namespaces
package com.example.sharma.cnycschools.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
//endregion

public class NYCSchoolListProvider extends ContentProvider {

    //region Variable
    private NYCSchoolDBHelper nycSchoolDBHelper;
    private static final int NYC_SCHOOL_LIST_ID = 100;
    private static final int NYC_SCHOOL_DETAIL_ID = 101;
    private static final String CLASS_NAME = NYCSchoolListProvider.class.getSimpleName();
    public static final UriMatcher sUriMatcher = buildUriMatcher();
    //endregion

    //region Public Methods
    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = null;
        try {
            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(NYCSchoolContract.AUTHORITY, NYCSchoolContract.PATH_SCHOOL_LIST, NYC_SCHOOL_LIST_ID);
            uriMatcher.addURI(NYCSchoolContract.AUTHORITY, NYCSchoolContract.PATH_SCHOOL_DETAIL, NYC_SCHOOL_DETAIL_ID);
            return uriMatcher;
        } catch (Exception e) {
            Log.e(CLASS_NAME, "buildUriMatcher() - " + e.getMessage());
            return uriMatcher;
        }
    }
    //endregion

    @Override
    public boolean onCreate() {
        try {
            nycSchoolDBHelper = new NYCSchoolDBHelper(getContext());
            return true;
        } catch (SQLiteException e) {
            Log.e(CLASS_NAME, "onCreate() - " + e.getMessage());
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrderBy) {
        Cursor returnCursor = null;
        try {
            final SQLiteDatabase db = nycSchoolDBHelper.getReadableDatabase();
            int matchID = sUriMatcher.match(uri);
            switch (matchID) {
                case NYC_SCHOOL_LIST_ID:
                    returnCursor = db.query(NYCSchoolContract.NYCSchoolList.TABLE_NAME, null, selection, selectionArgs, null, null, sortOrderBy);
                    break;
                case NYC_SCHOOL_DETAIL_ID:
                    returnCursor = db.query(NYCSchoolContract.NYCSchoolDetail.TABLE_NAME, null, selection, selectionArgs, null, null, sortOrderBy);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown Uri - " + uri);
            }
            if (returnCursor != null)
                returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
            return returnCursor;
        } catch (SQLiteException e) {
            Log.e(CLASS_NAME, "query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrderBy) - " + e.getMessage());
            return returnCursor;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted = 0;
        try {
            //Passing null wont give number of rows deleted, setting selection as 1 will return number of rows.
            if (null == selection) selection = "1";
            switch (sUriMatcher.match(uri)) { // Match URI to check weather it is of school list of school detail
                case NYC_SCHOOL_LIST_ID:
                    numRowsDeleted = nycSchoolDBHelper.getWritableDatabase().delete(
                            NYCSchoolContract.NYCSchoolList.TABLE_NAME,
                            selection,
                            selectionArgs);
                    break;
                case NYC_SCHOOL_DETAIL_ID:
                    numRowsDeleted = nycSchoolDBHelper.getWritableDatabase().delete(
                            NYCSchoolContract.NYCSchoolDetail.TABLE_NAME,
                            selection,
                            selectionArgs);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
            if (numRowsDeleted != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        } catch (SQLiteException e) {
            Log.e(CLASS_NAME, "delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) - " + e.getMessage());
            return numRowsDeleted;
        }
        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = nycSchoolDBHelper.getWritableDatabase();
        int matchID = sUriMatcher.match(uri);
        long rowsInserted = 0;
        switch (matchID) {
            case NYC_SCHOOL_LIST_ID:
                try {
                    db.beginTransaction();
                    for (ContentValues value : values) {
                        rowsInserted = db.insert(NYCSchoolContract.NYCSchoolList.TABLE_NAME, null, value);
                        if (rowsInserted != -1)
                            rowsInserted++;
                    }
                    db.setTransactionSuccessful();
                } catch (SQLiteException e) {
                    Log.e(CLASS_NAME, "bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) - NYC_SCHOOL_LIST_ID" + e.getMessage());
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0)
                    getContext().getContentResolver().notifyChange(uri, null);
                return (int) rowsInserted;
            case NYC_SCHOOL_DETAIL_ID:
                try {
                    db.beginTransaction();
                    for (ContentValues value : values) {
                        rowsInserted = db.insert(NYCSchoolContract.NYCSchoolDetail.TABLE_NAME, null, value);
                        if (rowsInserted != -1)
                            rowsInserted = rowsInserted + 1;
                    }
                    db.setTransactionSuccessful();
                } catch (SQLiteException e) {
                    Log.e(CLASS_NAME, "bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) - NYC_SCHOOL_DETAIL_ID" + e.getMessage());
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0)
                    getContext().getContentResolver().notifyChange(uri, null);
                return (int) rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
