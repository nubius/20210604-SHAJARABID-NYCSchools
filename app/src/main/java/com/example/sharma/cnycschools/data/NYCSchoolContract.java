
//region Namespaces
package com.example.sharma.cnycschools.data;

import android.net.Uri;
import android.provider.BaseColumns;
//endregion

public class NYCSchoolContract {

    //region Constructor
    private NYCSchoolContract() {
    }
    //endregion

    //region Constants
    public static final String AUTHORITY = "com.example.sharma.cnycschools";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_SCHOOL_LIST = "school_list";
    public static final String PATH_SCHOOL_DETAIL = "school_detail";
    //endregion

    public static class NYCSchoolList implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCHOOL_LIST).build();

        public static final String TABLE_NAME = "school_list";
        public static final String DBN = "dbn";
        public static final String SCHOOL_NAME = "school_name";
        public static final String SCHOOL_OVERVIEW = "overview_paragraph";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String EMAIL_ADDRESS = "school_email";
        public static final String TOTAL_STUDENT = "total_students";
        public static final String START_TIME = "start_time";
        public static final String END_TIME = "end_time";
        public static final String WEBSITE = "website";
        public static final String ADDRESS = "primary_address_line_1";
        public static final String CITY = "city";
        public static final String ZIP = "zip";
        public static final String STATE_CODE = "state_code";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    }

    public static class NYCSchoolDetail implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCHOOL_DETAIL).build();

        public static final String TABLE_NAME = "school_detail";
        public static final String DBN = "dbn";
        public static final String SCHOOL_NAME = "school_name";
        public static final String TOTAL_SAT_TAKER = "num_of_sat_test_takers";
        public static final String READING_SAT_SCORE = "sat_critical_reading_avg_score";
        public static final String MATH_SAT_SCORE = "sat_math_avg_score";
        public static final String WRITING_SAT_SCORE = "sat_writing_avg_score";
    }
}
