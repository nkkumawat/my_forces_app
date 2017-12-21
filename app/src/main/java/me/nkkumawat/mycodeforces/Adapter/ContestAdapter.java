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
import me.nkkumawat.mycodeforces.R;

/**
 * Created by sonu on 20/12/17.
 */

public class ContestAdapter extends ArrayAdapter<ContestData> {
    private ArrayList<ContestData> contestData;
    public ContestAdapter(ArrayList<ContestData> contestData, Context mContext ) {
        super(mContext, R.layout.contest_list, contestData);
        this.contestData = contestData;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contest_list, parent, false);
        }
        ContestData contestData = getItem(position);
        ViewHolder viewHolder = new ViewHolder(convertView);
        assert contestData != null;
        viewHolder.tv_contest_name.setText(contestData.NameOfContest);
        viewHolder.tv_contest_date.setText(contestData.StartDateOfContest);
        viewHolder.tv_contest_code.setText(contestData.ContestCode);
        return convertView;
    }
    public class ViewHolder {
        TextView tv_contest_name;
        TextView tv_contest_date;
        TextView tv_contest_code;
        ViewHolder(View view) {
            tv_contest_name = (TextView) view.findViewById(R.id.tv_contest_name);
            tv_contest_date = (TextView) view.findViewById(R.id.tv_contest_date);
            tv_contest_code = (TextView) view.findViewById(R.id.tv_contest_code);
        }
    }
}