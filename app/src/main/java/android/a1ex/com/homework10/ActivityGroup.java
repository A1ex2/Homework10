package android.a1ex.com.homework10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityGroup extends AppCompatActivity {
    private TextView groupName;
    private RecyclerView recyclerStudent;

    private DataBaseHelper helper;
    private ArrayList<Student> mStudents = new ArrayList<>();
    private RecyclerAdapter adapter;

    private Group mGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        helper = new DataBaseHelper(this);

        groupName = findViewById(R.id.groupName);
        recyclerStudent = findViewById(R.id.recyclerStudent);

        Intent intent = getIntent();
        mGroup = intent.getParcelableExtra(MainActivity.EXTRA_GROUP);

        groupName.setText(mGroup.name);
    }

    public void onClickAddStudent(View view) {
        switch (view.getId()) {
            case R.id.addStudent:

                long id = helper.insertStudent(new Student("Ivan", "Ivanov", 22), mGroup.id);
                Toast.makeText(this, String.valueOf(id), Toast.LENGTH_LONG).show();

                initList();
                break;
        }
    }

    private void initList() {
        ArrayList<Student> items = helper.getStudents();

        mStudents.clear();
        mStudents.addAll(items);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemOk:
                saveGroup();
                break;
        }
        return false;
    }

    public void saveGroup() {
        mGroup.name = String.valueOf(groupName.getText());
        long id = mGroup.id;
        if (id == -1) {
            id = helper.insertGroup(mGroup);
            Toast.makeText(this, "Группа добавлена", Toast.LENGTH_LONG).show();
        } else {
            helper.updateGroup(mGroup);
            Toast.makeText(this, "Группа изменена", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        finish();
    }
}
