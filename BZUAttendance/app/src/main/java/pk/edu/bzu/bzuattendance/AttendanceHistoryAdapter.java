package pk.edu.bzu.bzuattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AttendanceHistoryAdapter extends ArrayAdapter<AttendanceHistory> {
    public AttendanceHistoryAdapter(Context context, ArrayList<AttendanceHistory> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        AttendanceHistory history = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_attendance_history, parent, false);
        }

        // Lookup view for data population
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        TextView tvTimeslot = (TextView) convertView.findViewById(R.id.tvTimeslot);
        TextView tvSubjectCode = (TextView) convertView.findViewById(R.id.tvSubjectCode);
        TextView tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
        TextView tvTeacher = (TextView) convertView.findViewById(R.id.tvTeacher);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);

        // Populate the data into the template view using the data object
        tvDate.setText(history.getDate().substring(0, history.getDate().length() - 8));
        tvTimeslot.setText(history.getTimeSlot());
        tvSubjectCode.setText(history.getSubjectCode());
        tvSubject.setText(history.getSubject());
        tvTeacher.setText(history.getTeacher());
        tvStatus.setText(history.getStatus());

        // Return the completed view to render on screen
        return convertView;
    }
}
