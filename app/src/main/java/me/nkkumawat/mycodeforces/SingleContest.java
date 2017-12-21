package me.nkkumawat.mycodeforces;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.nkkumawat.mycodeforces.Adapter.ContestAdapter;
import me.nkkumawat.mycodeforces.Adapter.SingleContestAdapter;
import me.nkkumawat.mycodeforces.Model.ContestData;
import me.nkkumawat.mycodeforces.Model.SingleContestData;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SingleContest extends AppCompatActivity {
    private String contestCode;
    private String contestName;
    private ProgressDialog progress;
    private ArrayList<SingleContestData> singleContestData;
    private ListView singleContestList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contest);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            contestCode = bundle.getString("code");
            contestName = bundle.getString("name");
        }
        getSupportActionBar().setTitle(contestName);
        progress=new ProgressDialog(this);
        progress.setMessage("Loading ...");
        progress.setCancelable(false);
        singleContestList = (ListView) findViewById(R.id.single_contest_list);
        try {
            getProblemList();
        } catch (IOException e) {
            e.printStackTrace();
        }


        singleContestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingleContestData tempSingle = singleContestData.get(position);
                Intent intent = new Intent(SingleContest.this, SingleProblem.class);
                intent.putExtra("ccode", tempSingle.ContestCode);
                intent.putExtra("pcode", tempSingle.ProblemCode);
                startActivity(intent);
            }
        });
    }

    void getProblemList() throws IOException {
        progress.show();
        String url = "http://192.168.1.70:3000/contestDetails?id="+contestCode;
        Log.d("nk            " , url);
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
                SingleContest.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        setAdapter(myResponse);
//                        Toast.makeText(SingleContest.this , myResponse  , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void  setAdapter(String result){
        singleContestData = new ArrayList<>();
        try {
            JSONArray jArray = new JSONArray(result);
            Log.d("nk" , jArray.length()+ "  ");
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                String problemCode = json_data.getString("problemCode").replace("\n" , "").replace(" " , "");
                String problemName  =  json_data.getString("problemName").replace("\n" , "").replace(" " , "");
                SingleContestData tempContest = new SingleContestData(contestCode , problemCode , problemName);
                singleContestData.add(tempContest);

            }

            SingleContestAdapter singleContestAdapter = new SingleContestAdapter( singleContestData , this);
            singleContestList.setAdapter(singleContestAdapter);
        }
        catch (JSONException e) {
            Log.d("nk" , e+ "  ");
        // Toast.makeText(MainActivity.this, "Check you Internet Connection", Toast.LENGTH_LONG).show();
        }
    }


}
