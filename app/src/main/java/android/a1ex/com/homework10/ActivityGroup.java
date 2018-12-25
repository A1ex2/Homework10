package android.a1ex.com.homework10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
    private RecyclerAdapterStudents adapter;

    private Group mGroup;

    public static final String EXTRA_STUDENT = "android.a1ex.com.homework10.extra.STUDENT";
    public static final String EXTRA_GROUP = "android.a1ex.com.homework10.extra.GROUP";
    private static final int REQUEST_CODE = 1;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerStudent.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapterStudents(this, R.layout.student_item, mStudents);
        recyclerStudent.setAdapter(adapter);

        adapter.setActionListener(new RecyclerAdapterStudents.ActionListener() {
            @Override
            public void onClick(Student student) {
                Intent intent = new Intent(ActivityGroup.this, EditActivityStudent.class);
                intent.putExtra(EXTRA_STUDENT, student.id);
                intent.putExtra(EXTRA_GROUP, mGroup.id);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        initList();

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder targer) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int fromPos = viewHolder.getAdapterPosition();
                Student student = mStudents.get(fromPos);
                helper.deleteStudent(student);
                Toast.makeText(ActivityGroup.this, "удалено " + student.toString(), Toast.LENGTH_LONG).show();

                initList();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerStudent);

    }

    public void onClickAddStudent(View view) {
        switch (view.getId()) {
            case R.id.addStudent:
                if (mGroup.id == -1){
                    mGroup.name = String.valueOf(groupName.getText());
                    long id = mGroup.id;
                    id = helper.insertGroup(mGroup);
                    Toast.makeText(this, "Группа добавлена", Toast.LENGTH_LONG).show();
                    mGroup.id = id;
                }

                Intent intent = new Intent(ActivityGroup.this, EditActivityStudent.class);
                long mId = -1;
                intent.putExtra(EXTRA_STUDENT, mId);
                intent.putExtra(EXTRA_GROUP, mGroup.id);
                startActivityForResult(intent, REQUEST_CODE);

                break;
        }
    }

    private void initList() {
        ArrayList<Student> items = helper.getGroupStudents(mGroup);
//        ArrayList<Student> items = helper.getStudents();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK || requestCode == REQUEST_CODE) {
            if (data != null) {
                initList();
            }
        }
    }
}
