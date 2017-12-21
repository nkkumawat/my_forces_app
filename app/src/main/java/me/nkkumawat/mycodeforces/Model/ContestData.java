package me.nkkumawat.mycodeforces.Model;

/**
 * Created by sonu on 20/12/17.
 */

public class ContestData {
    public String NameOfContest;
    public String StartDateOfContest;
    public String ContestCode;

    public ContestData(String NameOfContest , String StartDateOfContest, String ContestCode) {
        this.NameOfContest = NameOfContest;
        this.StartDateOfContest = StartDateOfContest;
        this.ContestCode = ContestCode;
    }
}
