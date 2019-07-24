
//region Using Namespaces
package com.example.sharma.cnycschools.utilities;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import com.example.sharma.cnycschools.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//endregion

public class NYCSchoolUtils {

    //region Variables
    private static final String CLASS_NAME = NYCSchoolUtils.class.getSimpleName();
    private static final String SCHOOL_LIST_URL = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json";
    private static final String SCHOOL_DETAIL_URL = "https://data.cityofnewyork.us/resource/f9bf-2cp4.json";
    //endregion

    //region Public Methods
    /*
    This Method Builds URL to fetch school Lists 0r school details
    @param - isSchoolList -- to identify between two url
    @return - URL
    @throws MalformedURLException
     */

    public static URL buildSchoolListOrDetailURL(boolean isSchoolList) {
        Uri builtUri = Uri.parse(isSchoolList ? SCHOOL_LIST_URL : SCHOOL_DETAIL_URL).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(CLASS_NAME, "buildSchoolListOrDetailURL(boolean isSchoolList) - " + e.getMessage());
            e.printStackTrace();
        }
        return url;
    }

        /*
      This method returns the entire result from the HTTP response.
      @param url The URL to fetch the HTTP response from.
      @return The contents of the HTTP response.
      @throws IOException Related to network and stream reading
     */

    public static String getResponseFromHttpUrl(URL url) {
        HttpURLConnection urlConnection = null;
        String data = "";
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while (line != null) {
                line = bf.readLine();
                data = data + line;
            }
        } catch (IOException e) {
            Log.e(CLASS_NAME, "getResponseFromHttpUrl(URL url) - " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return data;
    }
    //endregion
}
