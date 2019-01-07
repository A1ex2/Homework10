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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityGroup extends AppCompatActivity {
    private TextView groupName;
    private RecyclerView recyclerStudent;
    private ProgressBar mProgressBar;

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

        groupName = findViewById(R.id.groupName);
        recyclerStudent = findViewById(R.id.recyclerStudent);
        mProgressBar = findViewById(R.id.progressBar2);
        mProgressBar.setVisibility(View.INVISIBLE);

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

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder targer) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int fromPos = viewHolder.getAdapterPosition();
                Student student = mStudents.get(fromPos);
                MyIntentService.deleteStudent(ActivityGroup.this, student);

                initList();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerStudent);

    }

    public void onClickAddStudent(View view) {
        switch (view.getId()) {
            case R.id.addStudent:
                if (mGroup.id == -1) {
                    mGroup.name = String.valueOf(groupName.getText());
                    MyIntentService.saveGroupForStudent(this, mGroup);
                } else {
                    addStudent();
                }
                break;
        }
    }

    public void addStudent() {
        Intent intent = new Intent(ActivityGroup.this, EditActivityStudent.class);
        long mId = -1;
        intent.putExtra(EXTRA_STUDENT, mId);
        intent.putExtra(EXTRA_GROUP, mGroup.id);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void initList() {
        mProgressBar.setVisibility(View.VISIBLE);
        MyIntentService.getStudents(this, mGroup);
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
            MyIntentService.saveGroup(this, mGroup);
        } else {
            MyIntentService.updateGroup(this, mGroup);
        }

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                initList();
            }
        } else if (requestCode == MyIntentService.REQUEST_CODE_GET_STUDENTS) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    ArrayList<Student> items = data.getParcelableArrayListExtra(MyIntentService.EXTRA_GET_STUDENTS);
                    mStudents.clear();
                    mStudents.addAll(items);
                    adapter.notifyDataSetChanged();

                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }

        } else if (requestCode == MyIntentService.REQUEST_CODE_SAVE_GROUP) {
            if (resultCode == RESULT_OK) {
                long id = data.getLongExtra(MyIntentService.EXTRA_ID_GROUP, -1);
                Toast.makeText(this, "группа добавлена", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == MyIntentService.REQUEST_CODE_GROUP_STUDENT) {
            if (resultCode == RESULT_OK) {
                long id = data.getLongExtra(MyIntentService.EXTRA_ID_GROUP, -1);
                mGroup.id = id;
                addStudent();
                Toast.makeText(this, "группа добавлена", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == MyIntentService.REQUEST_CODE_UPDATE_GROUP) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "группа изменена", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == MyIntentService.REQUEST_CODE_DELETE_STUDENT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(ActivityGroup.this, "студент удален ", Toast.LENGTH_LONG).show();
            }

        }
    }
}
