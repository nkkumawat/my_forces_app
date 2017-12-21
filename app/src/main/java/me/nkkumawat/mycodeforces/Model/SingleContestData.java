package me.nkkumawat.mycodeforces.Model;

/**
 * Created by sonu on 21/12/17.
 */

public class SingleContestData {
    public String ContestCode;
    public String ProblemCode;
    public String ProblemName;

    public SingleContestData(String ContestCode , String ProblemCode , String ProblemName) {
        this.ContestCode = ContestCode;
        this.ProblemCode = ProblemCode;
        this.ProblemName = ProblemName;
    }
}
