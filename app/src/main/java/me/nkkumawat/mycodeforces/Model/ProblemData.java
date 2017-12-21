package me.nkkumawat.mycodeforces.Model;

/**
 * Created by sonu on 21/12/17.
 */

public class ProblemData {

    public String Title;
    public String Statement;
    public String Input;
    public String OutPut;
    public String ExampleInput;
    public String ExampleOutput;

    public ProblemData(String Title , String Statement , String Input  , String  OutPut , String ExampleInput , String ExampleOutput) {
        this.Title = Title;
        this.Statement = Statement;
        this.ExampleInput = ExampleInput;
        this.ExampleOutput = ExampleOutput;
        this.Input = Input;
        this.OutPut = OutPut;
    }


}
