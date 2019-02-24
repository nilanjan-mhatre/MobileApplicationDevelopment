package mad.nil.listviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by nilan on 2/26/2018.
 */

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.StudentViewHolder> {
    List<Student> studentList;

    public StudentRecyclerAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.textView1.setText(student.getId());
        holder.textView2.setText(student.getName() );
        holder.textView1.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Hi", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView textView2;

        public StudentViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.text1);
            textView2 = itemView.findViewById(R.id.text2);
        }
    }

}