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
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Group.TABLE_NAME, Group.COLUM_ID + "=" + group.id, null);
    }

    public long insertStudent(Student student, long idGroup){
        SQLiteDatabase db = getReadableDatabase();
        long id = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Student.COLUM_FIRST_NAME, student.firstName);
            values.put(Student.COLUM_ID_GROUP, idGroup);

            id = db.insert(Student.TABLE_NAME, null, values);
        } catch (Exception e){
            e.printStackTrace();
        }

        return id;
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
