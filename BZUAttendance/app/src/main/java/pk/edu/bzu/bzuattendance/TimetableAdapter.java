package pk.edu.bzu.bzuattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TimetableAdapter extends ArrayAdapter<Timetable> {
    public TimetableAdapter(Context context, ArrayList<Timetable> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Timetable timetable = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_timetable, parent, false);
        }

        // Lookup view for data population
        TextView tvRoom = (TextView) convertView.findViewById(R.id.tvRoom);
        TextView tvTeacher = (TextView) convertView.findViewById(R.id.tvTeacher);
        TextView tvSubjectCode = (TextView) convertView.findViewById(R.id.tvSubjectCode);
        TextView tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
        TextView tvTimeslot = (TextView) convertView.findViewById(R.id.tvTimeslot);
        TextView tvSession = (TextView) convertView.findViewById(R.id.tvSession);

        // Populate the data into the template view using the data object
        tvRoom.setText(timetable.getRoom());
        tvTimeslot.setText(timetable.getTimeSlot());
        tvSubjectCode.setText(timetable.getSubjectCode());
        tvSubject.setText(timetable.getSubject());
        tvTeacher.setText(timetable.getTeacher());
        tvSession.setText(timetable.getSession());

        // Return the completed view to render on screen
        return convertView;
    }
}
