package mad.nil.listviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nilan on 2/26/2018.
 */

public class StudentAdapter extends ArrayAdapter<Student> {
    public StudentAdapter(@NonNull Context context, int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Student student = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = convertView.findViewById(R.id.text1);
            viewHolder.textView2 = convertView.findViewById(R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView1.setText(student.getId());
        viewHolder.textView2.setText(student.getName());
        return convertView;
    }

    private static class ViewHolder {
        TextView textView1;
        TextView textView2;
    }
}
