
//region Namespaces
package com.example.sharma.cnycschools;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sharma.cnycschools.data.NYCSchoolContract;
import com.example.sharma.cnycschools.model.SchoolList;
import com.example.sharma.cnycschools.sync.NYCSyncUtils;
//endregion


public class SchoolDetail extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //region Variables
    private static final String CLASS_NAME = SchoolDetail.class.getSimpleName();
    private static final int ID_LOADER = 15;
    public static final String[] MAIN_SCHOOOL_DETAIL_PROJECTION = {
            NYCSchoolContract.NYCSchoolDetail.DBN,
            NYCSchoolContract.NYCSchoolDetail.SCHOOL_NAME,
            NYCSchoolContract.NYCSchoolDetail.TOTAL_SAT_TAKER,
            NYCSchoolContract.NYCSchoolDetail.READING_SAT_SCORE,
            NYCSchoolContract.NYCSchoolDetail.MATH_SAT_SCORE,
            NYCSchoolContract.NYCSchoolDetail.WRITING_SAT_SCORE
    };
    private String dbn = "";
    private TextView tvSat;
    private TextView tvSatAvgReadScore;
    private TextView tvSatAvgMathScore;
    private TextView tvSatAvgWritingScore;
    private TextView tvSatTotalTaker;
    //endregion

    //region Private Method
    private SpannableString getSpannableData(String fieldName, String fieldValue) {
        SpannableString spannableString = new SpannableString("");
        try {
            String spannableText = String.format("%s %s", fieldName, fieldValue);
            spannableString = new SpannableString(spannableText);
            spannableString.setSpan(new RelativeSizeSpan(.9f), fieldName.length(), spannableText.length(), 0); // set size
            spannableString.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.colorGrayDark)), fieldName.length(), spannableText.length(), 0);// set color
            return spannableString;
        } catch (Exception e) {
            Log.e(CLASS_NAME, "getSpannableData(String fieldName, String fieldValue) - " + e.getMessage());
        }
        return spannableString;
    }

    private void setUpSATStatistic(String totalNumber, String readingSat, String mathSat, String writingSat) {
        try {
            if (totalNumber.equals("") || totalNumber.trim().equals(""))
                tvSatTotalTaker.setVisibility(View.GONE);
            else {
                tvSatTotalTaker.setText(getSpannableData(tvSatTotalTaker.getText().toString(), totalNumber));
                tvSat.setVisibility(View.VISIBLE);
                tvSatTotalTaker.setVisibility(View.VISIBLE);
            }
            if (readingSat.equals("") || readingSat.trim().equals(""))
                tvSatAvgReadScore.setVisibility(View.GONE);
            else {
                tvSatAvgReadScore.setText(getSpannableData(tvSatAvgReadScore.getText().toString(), readingSat));
                tvSatAvgReadScore.setVisibility(View.VISIBLE);
            }
            if (mathSat.equals("") || mathSat.trim().equals(""))
                tvSatAvgMathScore.setVisibility(View.GONE);
            else {
                tvSatAvgMathScore.setText(getSpannableData(tvSatAvgMathScore.getText().toString(), readingSat));
                tvSatAvgMathScore.setVisibility(View.VISIBLE);
            }

            if (writingSat.equals("") || writingSat.trim().equals(""))
                tvSatAvgWritingScore.setVisibility(View.GONE);
            else {
                tvSatAvgWritingScore.setText(getSpannableData(tvSatAvgWritingScore.getText().toString(), writingSat));
                tvSatAvgWritingScore.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, "setUpSATStatistic(String totalNumber, String readingSat, String mathSat, String writingSat)" + e.getMessage());
        }
    }
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);
        setTitle(getString(R.string.school_detail));
        NYCSyncUtils.initializeDetail(this);

        TextView tvSchoolName = findViewById(R.id.schoolNameTV);
        TextView tvOfficeHour = findViewById(R.id.officeHourTV);
        TextView tvPhoneNumber = findViewById(R.id.phoneNumberTV);
        TextView tvEmail = findViewById(R.id.emailTV);
        TextView tvWebsite = findViewById(R.id.websiteTV);
        TextView tvTotalStudent = findViewById(R.id.totalStudentTV);
        tvSat = findViewById(R.id.satTV);
        tvSatAvgReadScore = findViewById(R.id.satAvgReadScore);
        tvSatAvgMathScore = findViewById(R.id.satAvgMathScore);
        tvSatAvgWritingScore = findViewById(R.id.satAvgWritingScore);
        tvSatTotalTaker = findViewById(R.id.satTakerTV);
        TextView tvOverview = findViewById(R.id.overviewTV);
        TextView tvOverviewData = findViewById(R.id.overviewDataTV);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            SchoolList schoolList = intent.getParcelableExtra(Intent.EXTRA_TEXT);

            dbn = schoolList.getDBN();
            if (schoolList.getSchoolName() == null || schoolList.getSchoolName().trim().equals(""))
                tvSchoolName.setVisibility(View.GONE);
            else {
                tvSchoolName.setText(schoolList.getSchoolName());
                setTitle(schoolList.getSchoolName());
            }
            String startTime = schoolList.getStartTime();
            String endTime = schoolList.getEndTime();
            if (startTime.equals("") || startTime.trim().equals("") || endTime.equals("") || endTime.trim().equals(""))
                tvOfficeHour.setVisibility(View.GONE);
            else
                tvOfficeHour.setText(getSpannableData(tvOfficeHour.getText().toString(), String.format("%s - %s", startTime, endTime)));

            final String phoneNumber = schoolList.getPhoneNumber();
            if (phoneNumber == null || phoneNumber.trim().equals(""))
                tvPhoneNumber.setVisibility(View.GONE);
            else {
                tvPhoneNumber.setText(getSpannableData(tvPhoneNumber.getText().toString(), phoneNumber));
                tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:+1" + phoneNumber));
                            startActivity(intent);
                        } catch (Exception e) {
                            Log.e(CLASS_NAME + "call -> onClick(View v)", e.getMessage());
                        }
                    }
                });
            }
            final String email = schoolList.getEmailAddress();
            if (email == null || email.trim().equals(""))
                tvEmail.setVisibility(View.GONE);
            else {
                tvEmail.setText(getSpannableData(tvEmail.getText().toString(), email));

                tvEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email));
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello");
                            startActivity(emailIntent);
                        } catch (Exception e) {
                            Log.e(CLASS_NAME + "call -> onClick(View v)", e.getMessage());
                        }
                    }
                });
            }
            final String website = schoolList.getWebsite();
            if (website == null || website.trim().equals(""))
                tvWebsite.setVisibility(View.GONE);
            else {
                tvWebsite.setText(getSpannableData(tvWebsite.getText().toString(), website));

                tvWebsite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Uri uri = Uri.parse(website);
                            if (!website.startsWith("http://") && !website.startsWith("https://"))
                                uri = Uri.parse("http://" + website);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } catch (Exception e) {
                            Log.e(CLASS_NAME + "call -> onClick(View v)", e.getMessage());
                        }
                    }
                });
            }

            int totalStudent = schoolList.getTotalStudents();
            if (totalStudent == 0)
                tvTotalStudent.setVisibility(View.GONE);
            else
                tvTotalStudent.setText(getSpannableData(tvTotalStudent.getText().toString(), totalStudent + ""));

            String overView = schoolList.getSchoolOverview();
            if (overView == null || overView.trim().equals("")) {
                tvOverview.setVisibility(View.GONE);
                tvOverviewData.setVisibility(View.GONE);
            } else {
                tvOverview.setVisibility(View.VISIBLE);
                tvOverviewData.setText(overView);
            }
        }
        getSupportLoaderManager().initLoader(ID_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        try {
            switch (loaderId) {
                case ID_LOADER:
                /* URI for all rows of school data in our school table */
                    Uri schoolListURI = NYCSchoolContract.NYCSchoolDetail.CONTENT_URI;
                    // to retrieve data based on dbn value
                    String selection = NYCSchoolContract.NYCSchoolDetail.DBN + " LIKE '%" + dbn + "'";
                    return new CursorLoader(this,
                            schoolListURI,
                            MAIN_SCHOOOL_DETAIL_PROJECTION,
                            selection,
                            null,
                            null);
                default:
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
            if (data.getCount() != 0) {
                data.moveToPosition(0);
                String totalNumber = data.getString(data.getColumnIndex(NYCSchoolContract.NYCSchoolDetail.TOTAL_SAT_TAKER));
                String readingSat = data.getString(data.getColumnIndex(NYCSchoolContract.NYCSchoolDetail.READING_SAT_SCORE));
                String mathSat = data.getString(data.getColumnIndex(NYCSchoolContract.NYCSchoolDetail.MATH_SAT_SCORE));
                String writingSat = data.getString(data.getColumnIndex(NYCSchoolContract.NYCSchoolDetail.WRITING_SAT_SCORE));

                setUpSATStatistic(totalNumber, readingSat, mathSat, writingSat);
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, "onLoadFinished(Loader<Cursor> loader, Cursor data) " + e.getMessage());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        tvSatTotalTaker.setVisibility(View.GONE);
        tvSatAvgReadScore.setVisibility(View.GONE);
        tvSatAvgWritingScore.setVisibility(View.GONE);
        tvSatAvgMathScore.setVisibility(View.GONE);

    }
}
