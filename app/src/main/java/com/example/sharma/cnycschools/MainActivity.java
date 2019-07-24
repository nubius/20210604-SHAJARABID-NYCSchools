package com.example.sharma.cnycschools;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sharma.cnycschools.data.NYCSchoolContract;
import com.example.sharma.cnycschools.model.SchoolList;
import com.example.sharma.cnycschools.sync.NYCSyncUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        SchoolListAdapter.SchoolAdapterOnClickHandler {

    //region Variables
    private static final String CLASS_NAME = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private TextView tvErrorMessage;
    private ProgressBar mProgressBar;
    private static final int ID_NYC_LOADER = 11;
    private SchoolListAdapter mSchoolListAdapter;
    private String sortParameter = NYCSchoolContract.NYCSchoolList.SCHOOL_NAME + " ASC";
    private String menuSort = "";
    private int mPosition = RecyclerView.NO_POSITION;
    public static final String[] MAIN_NYC_SCHOOOL_PROJECTION = {
            NYCSchoolContract.NYCSchoolList.DBN,
            NYCSchoolContract.NYCSchoolList.SCHOOL_NAME,
            NYCSchoolContract.NYCSchoolList.SCHOOL_OVERVIEW,
            NYCSchoolContract.NYCSchoolList.PHONE_NUMBER,
            NYCSchoolContract.NYCSchoolList.EMAIL_ADDRESS,
            NYCSchoolContract.NYCSchoolList.TOTAL_STUDENT,
            NYCSchoolContract.NYCSchoolList.START_TIME,
            NYCSchoolContract.NYCSchoolList.END_TIME,
            NYCSchoolContract.NYCSchoolList.WEBSITE,
            NYCSchoolContract.NYCSchoolList.ADDRESS,
            NYCSchoolContract.NYCSchoolList.CITY,
            NYCSchoolContract.NYCSchoolList.LATITUDE,
            NYCSchoolContract.NYCSchoolList.LONGITUDE
    };
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            setTitle(getString(R.string.school_list));
            mRecyclerView = findViewById(R.id.nycSchoolRV);
            tvErrorMessage = findViewById(R.id.errorMessageTV);
            mProgressBar = findViewById(R.id.loadingPB);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            mSchoolListAdapter = new SchoolListAdapter(this, this);
            mRecyclerView.setAdapter(mSchoolListAdapter);
            //  menuSort = getResources().getString(R.string.count_sort_desc);
            getSupportLoaderManager().initLoader(ID_NYC_LOADER, null, this);
            NYCSyncUtils.initialize(this);
        } catch (Exception e) {
            Log.e(CLASS_NAME, "onCreate(Bundle savedInstanceState) - " + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_sort, menu);
        } catch (Exception e) {
            Log.e(CLASS_NAME, "onCreateOptionsMenu(Menu menu) - " + e.getMessage());
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if (item.getItemId() == R.id.action_asc) {
                if (!menuSort.equals(getResources().getString(R.string.count_sort_asc))) {
                    menuSort = getResources().getString(R.string.count_sort_asc);
                    sortParameter = NYCSchoolContract.NYCSchoolList.SCHOOL_NAME + " ASC";
                    mProgressBar.setVisibility(View.VISIBLE);
                    //restart the loader
                    getSupportLoaderManager().restartLoader(ID_NYC_LOADER, null, this);
                }
            } else if (item.getItemId() == R.id.action_desc) {
                if (!menuSort.equals(getResources().getString(R.string.count_sort_desc))) {
                    menuSort = getResources().getString(R.string.count_sort_desc);
                    sortParameter = NYCSchoolContract.NYCSchoolList.SCHOOL_NAME + " DESC";
                    mProgressBar.setVisibility(View.VISIBLE);
                    getSupportLoaderManager().restartLoader(ID_NYC_LOADER, null, this);
                }
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, "onOptionsItemSelected(MenuItem item) - " + e.getMessage());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        try {
            switch (loaderId) {
                case ID_NYC_LOADER:
                /* URI for all rows of school data in our school table */
                    Uri schoolListURI = NYCSchoolContract.NYCSchoolList.CONTENT_URI;
                /* Sort order: Ascending by date */
                    //  String sortOrder = OpenAirContract.OpenAirEntry.MEASUREMENT_COUNT + " DESC";
                    return new CursorLoader(this,
                            schoolListURI,
                            MAIN_NYC_SCHOOOL_PROJECTION,
                            null,
                            null,
                            sortParameter);
                default:
                    showErrorMessage();
                    throw new RuntimeException("Loader Not Implemented: " + loaderId);
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, "onCreateLoader(int loaderId, Bundle bundle) - loaderId - " + loaderId + " " + e.getMessage());
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        try {
            mSchoolListAdapter.swapCursor(data);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            mRecyclerView.smoothScrollToPosition(mPosition);
            if (data.getCount() != 0) showNYCSchoolListView();
        } catch (Exception e) {
            Log.e(CLASS_NAME, "onLoadFinished(Loader<Cursor> loader, Cursor data) - " + e.getMessage());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        try {
            mSchoolListAdapter.swapCursor(null);
        } catch (Exception e) {
            Log.e(CLASS_NAME, "onLoaderReset(Loader<Cursor> loader) - " + e.getMessage());
        }
    }

    @Override
    public void onClick(SchoolList schoolList) {
        try {
            Intent intent = new Intent(this, SchoolDetail.class);
            intent.putExtra(Intent.EXTRA_TEXT, schoolList);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(CLASS_NAME, "onClick(SchoolList schoolList) - " + e.getMessage());
        }
    }
    //endregion

    //region Private Methods
    private void showErrorMessage() {
        try {
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            tvErrorMessage.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.e(CLASS_NAME, "showErrorMessage() - " + e.getMessage());
        }
    }

    private void showNYCSchoolListView() {
        try {
            mProgressBar.setVisibility(View.GONE);
            tvErrorMessage.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.e(CLASS_NAME, "showNYCSchoolListView() - " + e.getMessage());
        }
    }
    //endregion
}
