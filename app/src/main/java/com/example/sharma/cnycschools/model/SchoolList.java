
//region Using Namespaces
package com.example.sharma.cnycschools.model;

import android.os.Parcel;
import android.os.Parcelable;
//endregion

// Created Parcelable object using http://www.parcelabler.com/

public class SchoolList implements Parcelable {

    private String dbn;
    private String schoolName;
    private String schoolOverview;
    private String phoneNumber;
    private String emailAddress;
    private int totalStudents;
    private String startTime;
    private String endTime;
    private String website;
    private String address;
    private String city;
    private int zip;
    private String stateCode;
    private double latitude;
    private double longitude;

    public String getEmailAddress() {
        return emailAddress;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getWebsite() {
        return website;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public int getZip() {
        return zip;
    }

    public String getStateCode() {
        return stateCode;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDBN() {
        return dbn;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolOverview() {
        return schoolOverview;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public SchoolList(String strDBN, String strSchoolName, String strSchoolOverview, String strPhoneNumber, String strEmailAddress, String strCity, int intTotalStudents, String strStartTime,
                      String strEndTime, String strWebsite, String strAddress, int intZipCode, String strStateCode, double dbLatitude, double dbLongitude) {

        this.dbn = strDBN;
        this.schoolName = strSchoolName;
        this.schoolOverview = strSchoolOverview;
        this.phoneNumber = strPhoneNumber;
        this.emailAddress = strEmailAddress;
        this.totalStudents = intTotalStudents;
        this.startTime = strStartTime;
        this.endTime = strEndTime;
        this.website = strWebsite;
        this.address = strAddress;
        this.city = strCity;
        this.zip = intZipCode;
        this.stateCode = strStateCode;
        this.latitude = dbLatitude;
        this.longitude = dbLongitude;
    }


    protected SchoolList(Parcel in) {
        dbn = in.readString();
        schoolName = in.readString();
        schoolOverview = in.readString();
        phoneNumber = in.readString();
        emailAddress = in.readString();
        totalStudents = in.readInt();
        startTime = in.readString();
        endTime = in.readString();
        website = in.readString();
        address = in.readString();
        zip = in.readInt();
        stateCode = in.readString();
        city = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dbn);
        dest.writeString(schoolName);
        dest.writeString(schoolOverview);
        dest.writeString(phoneNumber);
        dest.writeString(emailAddress);
        dest.writeInt(totalStudents);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(website);
        dest.writeString(address);
        dest.writeInt(zip);
        dest.writeString(stateCode);
        dest.writeString(city);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SchoolList> CREATOR = new Creator<SchoolList>() {
        @Override
        public SchoolList createFromParcel(Parcel in) {
            return new SchoolList(in);
        }

        @Override
        public SchoolList[] newArray(int size) {
            return new SchoolList[size];
        }
    };
}
