package me.nkkumawat.mycodeforces;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.service.quicksettings.Tile;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import me.nkkumawat.mycodeforces.Adapter.SingleContestAdapter;
import me.nkkumawat.mycodeforces.Model.SingleContestData;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SingleProblem extends AppCompatActivity {
    private String contestCode;
    private String problemCode;
    private ProgressDialog progress;
    public TextView Title;
    public TextView Statement;
    public TextView Input;
    public TextView OutPut;
    public TextView ExampleInput;
    public TextView ExampleOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_problem);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            contestCode = bundle.getString("ccode");
            problemCode = bundle.getString("pcode");
        }

        progress=new ProgressDialog(this);
        progress.setMessage("Loading ...");
        progress.setCancelable(false);
        Title = (TextView) findViewById(R.id.tv_problem_name);
        Statement = (TextView) findViewById(R.id.tv_problem_statement);
        Input = (TextView) findViewById(R.id.tv_problem_input);
        OutPut = (TextView) findViewById(R.id.tv_problem_output);
        ExampleInput = (TextView) findViewById(R.id.tv_problem_example_input);
        ExampleOutput = (TextView) findViewById(R.id.tv_problem_example_output);
        try {
            getProblems();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void getProblems() throws IOException {
        progress.show();
        String url = "http://192.168.1.70:3000/problemById?cId="+contestCode+"&pId="+problemCode;
        Log.d("nk      " , url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                SingleProblem.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        setTextViews(myResponse);
//                        Toast.makeText(SingleContest.this , myResponse  , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void  setTextViews(String result){
        try {
            JSONArray jArray = new JSONArray(result);
            Log.d("nk" , jArray.length()+ "  ");
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                String title = json_data.getString("title").replace("\n" , " ");
                String statement  =  json_data.getString("statement").replace("\n" , " ");
                String input  =  json_data.getString("input").replace("\n" , " ");
                String output  =  json_data.getString("output").replace("\n" , " ");
                String exampleInput  =  json_data.getString("exampleInput").replace("\n" , " ").replace("<br>" , "\n");
                String exampleOutput  =  json_data.getString("exampleOutput").replace("\n" , " ").replace("<br>" , "\n");
                getSupportActionBar().setTitle(title);
                Title.setText(title);
                Statement.setText(statement);
                Input.setText("INPUT\n" +input);
                OutPut.setText("OUTPUT\n" + output);
                ExampleOutput.setText("OUTPUT\n" + exampleOutput);
                ExampleInput.setText("INPUT\n" + exampleInput);
            }
        }
        catch (JSONException e) {
            Log.d("nk" , e+ "  ");
            // Toast.makeText(MainActivity.this, "Check you Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}
