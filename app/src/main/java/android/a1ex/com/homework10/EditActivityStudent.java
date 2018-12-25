package android.a1ex.com.homework10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivityStudent extends AppCompatActivity {
    private DataBaseHelper helper;

    public TextView firstName;
    public TextView lastName;
    public TextView age;

    public Student mStudent;
    public long id = -1;
    public long idGroup = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        helper = new DataBaseHelper(this);

        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        age = findViewById(R.id.editAge);

        Intent intent = getIntent();

        id = intent.getLongExtra(ActivityGroup.EXTRA_STUDENT, id);
        idGroup = intent.getLongExtra(ActivityGroup.EXTRA_GROUP, idGroup);

        if (id == -1) {
            mStudent = new Student();

        } else {
            mStudent = helper.getStudent(id);
            firstName.setText(mStudent.firstName);
            lastName.setText(mStudent.lastName);
            age.setText(String.valueOf(mStudent.age));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.student_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemOk:
                saveStudent();
                break;
        }
        return false;
    }

    public void saveStudent() {
        mStudent.firstName = firstName.getText().toString();
        mStudent.lastName = lastName.getText().toString();
        int mAge;
        try {
            mAge = Integer.valueOf(age.getText().toString());
        } catch (
                NumberFormatException e)
        {
            mAge = 0;
        }
        mStudent.age = mAge;
        mStudent.idGroup = idGroup;
        if (id == -1) {
            id = helper.insertStudent(mStudent);
            Toast.makeText(this, "Студент добавлен", Toast.LENGTH_LONG).show();
        } else {
            helper.updateStudent(mStudent);
            Toast.makeText(this, "Студент изменен", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);

        finish();
    }
}
