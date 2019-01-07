package android.a1ex.com.homework10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context) {
        super(context, "MyBD.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + Group.TABLE_NAME + " ("
                + Group.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Group.COLUM_NAME + " TEXT NOT NULL)");


        db.execSQL("CREATE TABLE " + Student.TABLE_NAME + "("
                + Student.COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Student.COLUM_ID_GROUP + " INTEGER NOT NULL,"
                + Student.COLUM_FIRST_NAME + " TEXT NOT NULL,"
                + Student.COLUM_LAST_NAME + " TEXT NOT NULL,"
                + Student.COLUM_AGE + " INTEGER NOT NULL,"
                + "FOREIGN KEY ("+Student.COLUM_ID_GROUP+") REFERENCES "
                + Group.TABLE_NAME + "(" + Student.COLUM_ID + "))");
    }

    public void deleteAll(){
        ArrayList<Group> mGroups = getGroups();
        for (int i = 0; i < mGroups.size(); i++){
            deleteGroup(mGroups.get(i));
        }
    }

    public long insertGroup(Group group){
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Group.COLUM_NAME, group.name);

            id = db.insert(Group.TABLE_NAME, null, values);
        } catch (Exception e){
            e.printStackTrace();
        }

        return id;
    }

    public void addGroups(ArrayList<Group> mGroups){
        SQLiteDatabase db = getReadableDatabase();

        for (int i = 0; i < mGroups.size(); i++){
            long id = 0;

            Group group = mGroups.get(i);
            try {
                ContentValues values = new ContentValues();

                values.put(Group.COLUM_ID, group.id);
                values.put(Group.COLUM_NAME, group.name);

                id = db.insert(Group.TABLE_NAME, null, values);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Group> getGroups(){
        ArrayList<Group> groups = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Group.TABLE_NAME,null,null,null,null,null,null);

            if (cursor.moveToNext()){
                while (!cursor.isAfterLast()){
                    Group group = new Group();
                    group.id = cursor.getLong(cursor.getColumnIndex(Group.COLUM_ID));
                    group.name = cursor.getString(cursor.getColumnIndex(Group.COLUM_NAME));

                    groups.add(group);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return groups;
    }

    public int updateGroup(Group group){
        SQLiteDatabase db = getWritableDatabase();
        int count = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Group.COLUM_NAME, group.name);

            count = db.update(Group.TABLE_NAME, values, Group.COLUM_ID + "=" +group.id, null);
        } catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }

    public void deleteGroup(Group group){
        ArrayList<Student> students = getGroupStudents(group);
        for (int i = 0; i < students.size(); i++){
            deleteStudent(students.get(i));
        }

        SQLiteDatabase db = getWritableDatabase();
        db.delete(Group.TABLE_NAME, Group.COLUM_ID + "=" + group.id, null);
    }

    public long insertStudent(Student student) {
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Student.COLUM_FIRST_NAME, student.firstName);
            values.put(Student.COLUM_LAST_NAME, student.lastName);
            values.put(Student.COLUM_AGE, student.age);
            values.put(Student.COLUM_ID_GROUP, student.idGroup);

            id = db.insert(Student.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public void addStudents(ArrayList<Student> mStudents) {
        SQLiteDatabase db = getReadableDatabase();
        for (int i = 0; i < mStudents.size(); i++) {
            Student student = mStudents.get(i);

            try {
                long id = 0;
                ContentValues values = new ContentValues();

                values.put(Student.COLUM_ID, student.id);
                values.put(Student.COLUM_FIRST_NAME, student.firstName);
                values.put(Student.COLUM_LAST_NAME, student.lastName);
                values.put(Student.COLUM_AGE, student.age);
                values.put(Student.COLUM_ID_GROUP, student.idGroup);

                id = db.insert(Student.TABLE_NAME, null, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Student getStudent(long id) {
        Student student = new Student();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Student.TABLE_NAME,null,Student.COLUM_ID + " = " + id,null,null,null,null);

            if (cursor.moveToNext()){
                    student.id = cursor.getLong(cursor.getColumnIndex(Student.COLUM_ID));
                    student.firstName = cursor.getString(cursor.getColumnIndex(Student.COLUM_FIRST_NAME));
                    student.lastName = cursor.getString(cursor.getColumnIndex(Student.COLUM_LAST_NAME));
                    student.age = cursor.getInt(cursor.getColumnIndex(Student.COLUM_AGE));
                    student.idGroup = cursor.getLong(cursor.getColumnIndex(Student.COLUM_ID_GROUP));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return student;
    }

    public ArrayList<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Student.TABLE_NAME,null,null,null,null,null,null);

            if (cursor.moveToNext()){
                while (!cursor.isAfterLast()){
                    Student student = new Student();
                    student.id = cursor.getLong(cursor.getColumnIndex(Student.COLUM_ID));
                    student.firstName = cursor.getString(cursor.getColumnIndex(Student.COLUM_FIRST_NAME));
                    student.lastName = cursor.getString(cursor.getColumnIndex(Student.COLUM_LAST_NAME));
                    student.age = cursor.getInt(cursor.getColumnIndex(Student.COLUM_AGE));
                    student.idGroup = cursor.getLong(cursor.getColumnIndex(Student.COLUM_ID_GROUP));

                    students.add(student);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return students;
    }

    public ArrayList<Student> getGroupStudents(Group group){
        ArrayList<Student> students = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Student.TABLE_NAME, null, Student.TABLE_NAME + "." + Student.COLUM_ID_GROUP + " = " + group.id,
                    null,null,null,null);

            if (cursor.moveToNext()){
                while (!cursor.isAfterLast()){
                    Student student = new Student();
                    student.id = cursor.getLong(cursor.getColumnIndex(Student.COLUM_ID));
                    student.firstName = cursor.getString(cursor.getColumnIndex(Student.COLUM_FIRST_NAME));
                    student.lastName = cursor.getString(cursor.getColumnIndex(Student.COLUM_LAST_NAME));
                    student.age = cursor.getInt(cursor.getColumnIndex(Student.COLUM_AGE));
                    student.idGroup = cursor.getLong(cursor.getColumnIndex(Student.COLUM_ID_GROUP));

                    students.add(student);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }

        return students;
    }

    public int updateStudent(Student student){
        SQLiteDatabase db = getWritableDatabase();
        int count = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Student.COLUM_FIRST_NAME, student.firstName);
            values.put(Student.COLUM_LAST_NAME, student.lastName);
            values.put(Student.COLUM_AGE, student.age);
            values.put(Student.COLUM_ID_GROUP, student.idGroup);

            count = db.update(Student.TABLE_NAME, values, Student.COLUM_ID + "=" +student.id, null);
        } catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }

    public void deleteStudent(Student student){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Student.TABLE_NAME, Student.COLUM_ID + "=" + student.id, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
