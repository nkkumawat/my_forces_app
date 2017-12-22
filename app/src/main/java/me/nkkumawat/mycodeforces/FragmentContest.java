package me.nkkumawat.mycodeforces;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import me.nkkumawat.mycodeforces.Adapter.ContestAdapter;
import me.nkkumawat.mycodeforces.Model.ContestData;

/**
 * Created by sonu on 20/12/17.
 */

public class FragmentContest extends Fragment {

    private ListView contestList;
    private JSONArray myResponse;
    ArrayList<ContestData> contestData ;
    private String InstanceId;
    SharedPreferences sharedpreferences;
    public FragmentContest() {
    }

    @SuppressLint("ValidFragment")
    public FragmentContest(JSONArray myResponse , String InstanceId) {
        this.myResponse = myResponse;
        this.InstanceId = InstanceId;
    }

    public static FragmentContest newInstance(JSONArray myResponse , String InstanceId) {
        return new FragmentContest(myResponse , InstanceId);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contest_main, container, false);
        contestList = (ListView) rootView.findViewById(R.id.contest_list);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        setAdapter(myResponse);
        contestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(InstanceId.equals("PAST")) {
                    ContestData contest = contestData.get(position);
                    Intent intent = new Intent(getActivity(), SingleContest.class);
                    intent.putExtra("code", contest.ContestCode);
                    intent.putExtra("name", contest.NameOfContest);
                    startActivity(intent);
                }else {
				Snackbar.make(view,"CONTEST IS NOT STARTED" , Snackbar.LENGTH_LONG).setAction("No action", null).show();
                }
            }
        });
    }
    public void  setAdapter(JSONArray jArray){
        contestData = new ArrayList<>();
        try {

            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("contestDetails", jArray.toString());
            editor.putString("status", "1");
            editor.apply();
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                String name = json_data.getString("contestName").replace("\n" , "");
                String date  =  json_data.getString("startDate").replace("\n" , "").replace(" " , "");
                String code  =  json_data.getString("contestCode").replace("\n" , "").replace(" " , "");
                if(name.length() > 40){
                    name = name.substring(0,37) + "...";
                }
                ContestData tempContest = new ContestData(name , date , code);
                contestData.add(tempContest);
            }

            ContestAdapter contestAdapter = new ContestAdapter( contestData , getActivity());
            contestList.setAdapter(contestAdapter);
        }
        catch (JSONException e) {
//            Toast.makeText(MainActivity.this, "Check you Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}

