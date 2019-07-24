
//region Using Namespaces
package com.example.sharma.cnycschools.model;
//endregion


public class SchoolDetail {

    private String dbn;
    private String schoolName;
    private String totalSATTAKER;
    private String readingSATScore;
    private String mathSATScore;
    private String writingSATScore;

    public SchoolDetail(String strDBN, String strSchoolName, String strTotalSATTAKER, String strReadingSATScore, String strMathSATScore, String strWritingSATScore) {
        this.dbn = strDBN;
        this.schoolName = strSchoolName;
        this.totalSATTAKER = strTotalSATTAKER;
        this.readingSATScore = strReadingSATScore;
        this.mathSATScore = strMathSATScore;
        this.writingSATScore = strWritingSATScore;
    }

    public String getDbn() {
        return dbn;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getTotalSATTAKER() {
        return totalSATTAKER;
    }

    public String getReadingSATScore() {
        return readingSATScore;
    }

    public String getMathSATScore() {
        return mathSATScore;
    }

    public String getWritingSATScore() {
        return writingSATScore;
    }
}
