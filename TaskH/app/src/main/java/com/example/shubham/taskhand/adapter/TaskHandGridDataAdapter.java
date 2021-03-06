package com.example.shubham.taskhand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shubham.taskhand.R;
import com.example.shubham.taskhand.constants.StringConstants;
import com.example.shubham.taskhand.database.TaskHandDataModel;
import com.example.shubham.taskhand.utility.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter Class for TaskHandGridFragment
 * <p/>
 * Created by shubham on 7/12/16.
 */
public class TaskHandGridDataAdapter extends BaseAdapter {
    private ArrayList<TaskHandDataModel> mTaskHandDataModelArrayList;
    private TaskHandDataModel mListProvider;
    private Context mContext;

    public TaskHandGridDataAdapter(Context context, ArrayList<TaskHandDataModel> arrayList) {
        this.mContext = context;
        this.mTaskHandDataModelArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return mTaskHandDataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTaskHandDataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_hand_grid_column_item, parent, false);
            viewHolder = new ViewHolder();
            //set tag for view Holder
            viewHolder.mTaskHandNameTextView = (TextView) convertView.findViewById(R.id.task_hand_column_name);
            viewHolder.mTaskHandReminderTextView = (TextView) convertView.findViewById(R.id.task_hand_column_date);
            viewHolder.mTaskHandDetailTextView = (TextView) convertView.findViewById(R.id.task_hand_column_detail);
            viewHolder.mTaskHandImageView = (ImageView) convertView.findViewById(R.id.task_hand_alarm_grid_imageView);
            viewHolder.mBottomView = (View) convertView.findViewById(R.id.bottom_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //setting data in views
        mListProvider = mTaskHandDataModelArrayList.get(position);
        viewHolder.mTaskHandNameTextView.setText(mListProvider.getTaskName());
        Logger.debug("Data", "" + mListProvider.getTaskName());
        viewHolder.mTaskHandDetailTextView.setText(mListProvider.getTaskDetail());
        long datetime = mListProvider.getTaskReminderTime();

        if (datetime != 0) {
            SimpleDateFormat simpleDateFormat;
            SimpleDateFormat date = new SimpleDateFormat("dd");

            if (date.format(new Date()).equals(date.format(datetime))) {
                simpleDateFormat = new SimpleDateFormat("HH-mm");
                Logger.debug("ic_time of alarm ", " " + simpleDateFormat.format(datetime));
            } else {
                simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Logger.debug("date of alarm", " " + simpleDateFormat.format(datetime));
            }

            Logger.debug("ic_time", "" + simpleDateFormat.format(datetime));
            viewHolder.mTaskHandReminderTextView.setText(simpleDateFormat.format(datetime));
            viewHolder.mTaskHandReminderTextView.setVisibility(View.VISIBLE);
            viewHolder.mTaskHandImageView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mTaskHandReminderTextView.setVisibility(View.INVISIBLE);
            viewHolder.mTaskHandImageView.setVisibility(View.INVISIBLE);
        }

        //colouring views according to priority
        String priority = mListProvider.getTaskPriority();
        switch (priority) {
            case StringConstants.PRIORITY_LOWEST:
                viewHolder.mBottomView.setBackgroundColor(mContext.getResources().getColor(R.color.holo_orange_dark));
                break;
            case StringConstants.PRIORITY_MEDIUM:
                viewHolder.mBottomView.setBackgroundColor(mContext.getResources().getColor(R.color.holo_red_light));
                break;
            case StringConstants.PRIORITY_HIGHEST:
                viewHolder.mBottomView.setBackgroundColor(mContext.getResources().getColor(R.color.holo_purple));
                break;
            default:
                viewHolder.mBottomView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                break;
        }
        return convertView;
    }

    /***
     * viewHolder class for smooth scrolling
     */
    final static class ViewHolder {
        TextView mTaskHandNameTextView;
        TextView mTaskHandReminderTextView;
        TextView mTaskHandDetailTextView;
        ImageView mTaskHandImageView;
        View mBottomView;

        public ViewHolder() {
        }
    }
}