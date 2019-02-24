package mad.nil.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student("1", "ABC"));
        studentList.add(new Student("2", "XYZ"));

        ListView listView = findViewById(R.id.list);
//        final ArrayAdapter<Student> arrayAdapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, android.R.id.text1, studentList);
        final StudentAdapter studentAdapter = new StudentAdapter(this, R.layout.list, studentList);
        listView.setAdapter(studentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = findViewById(R.id.text);
                textView.setText(studentList.get(position).getName());
            }
        });

        Button add = findViewById(R.id.button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id = findViewById(R.id.edit_id);
                EditText name = findViewById(R.id.edit_name);
                studentAdapter.add(new Student(id.getText().toString(), name.getText().toString()));
                studentAdapter.notifyDataSetChanged();
            }
        });

        ///////////////////////////////////////////////////////

        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        RecyclerView.Adapter adapter = new StudentRecyclerAdapter(studentList);
        recyclerView.setAdapter(adapter);
    }
}
class Student {
    private String id;
    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + ":" + name;
    }
}
