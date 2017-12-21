package me.nkkumawat.mycodeforces.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.nkkumawat.mycodeforces.Model.ContestData;
import me.nkkumawat.mycodeforces.Model.SingleContestData;
import me.nkkumawat.mycodeforces.R;

import static me.nkkumawat.mycodeforces.R.*;

/**
 * Created by sonu on 21/12/17.
 */

public class SingleContestAdapter extends ArrayAdapter<SingleContestData> {
    private ArrayList<SingleContestData> singleContestData;
    public SingleContestAdapter(ArrayList<SingleContestData> singleContestData, Context mContext ) {
        super(mContext, layout.single_contest_list, singleContestData);
        this.singleContestData = singleContestData;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout.single_contest_list, parent, false);
        }
        SingleContestData singleContestData = getItem(position);
        SingleContestAdapter.ViewHolder viewHolder = new SingleContestAdapter.ViewHolder(convertView);
        assert singleContestData != null;
        viewHolder.tv_problem_name.setText(singleContestData.ProblemName);
        viewHolder.tv_problem_code.setText(singleContestData.ProblemCode);
        viewHolder.tv_contest_code.setText(singleContestData.ContestCode);
        return convertView;
    }
    public class ViewHolder {
        TextView tv_problem_name;
        TextView tv_problem_code;
        TextView tv_contest_code;
        ViewHolder(View view) {
            tv_problem_name = (TextView) view.findViewById(id.tv_problem_name);
            tv_problem_code = (TextView) view.findViewById(id.tv_problem_code);
            tv_contest_code = (TextView) view.findViewById(id.tv_contest_code);
        }
    }
}