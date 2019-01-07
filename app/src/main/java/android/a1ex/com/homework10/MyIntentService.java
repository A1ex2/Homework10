package android.a1ex.com.homework10;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MyIntentService extends IntentService {
    private static final String ACTION_GET_GROUPS = "android.a1ex.com.homework10.action.GET_GROUPS";
    private static final String ACTION_SAVE_GROUP = "android.a1ex.com.homework10.action.SAVE_GROUP";
    private static final String ACTION_UPDATE_GROUP = "android.a1ex.com.homework10.action.UPDATE_GROUP";
    private static final String ACTION_DELETE_GROUP = "android.a1ex.com.homework10.action.DELETE_GROUP";
    public static final String ACTION_GROUP_STUDENT = "android.a1ex.com.homework10.extra.GROUP_STUDENT";

    private static final String ACTION_GET_STUDENTS = "android.a1ex.com.homework10.action.GET_STUDENTS";
    private static final String ACTION_GET_STUDENT = "android.a1ex.com.homework10.action.GET_STUDENT";
    private static final String ACTION_SAVE_STUDENT = "android.a1ex.com.homework10.action.SAVE_STUDENT";
    private static final String ACTION_UPDATE_STUDENT = "android.a1ex.com.homework10.action.UPDATE_STUDENT";
    private static final String ACTION_DELETE_STUDENT = "android.a1ex.com.homework10.action.DELETE_STUDENT";

    private static final String EXTRA_PENDING_INTENT = "android.a1ex.com.homework10.extra.PENDING_INTENT";

    public static final String ACTION_CREATE_BACKUP = "android.a1ex.com.homework10.extra.CREATE_BACKUP";
    public static final String ACTION_RESTORE_BACKUP = "android.a1ex.com.homework10.extra.RESTORE_BACKUP";

    public static final String EXTRA_GET_GROUPS = "android.a1ex.com.homework10.extra.GET_GROUPS";
    public static final String EXTRA_SAVE_GROUP = "android.a1ex.com.homework10.extra.SAVE_GROUP";
    public static final String EXTRA_DELETE_GROUP = "android.a1ex.com.homework10.extra.DELETE_GROUP";
    public static final String EXTRA_UPDATE_GROUP = "android.a1ex.com.homework10.extra.UPDATE_GROUP";
    public static final String EXTRA_ID_GROUP = "android.a1ex.com.homework10.extra.ID_GROUP";

    public static final String EXTRA_GET_STUDENTS = "android.a1ex.com.homework10.extra.GET_STUDENTS";
    public static final String EXTRA_GET_STUDENT = "android.a1ex.com.homework10.extra.GET_STUDENT";
    public static final String EXTRA_SAVE_STUDENT = "android.a1ex.com.homework10.extra.SAVE_STUDENT";
    public static final String EXTRA_DELETE_STUDENT = "android.a1ex.com.homework10.extra.DELETE_STUDENT";
    public static final String EXTRA_UPDATE_STUDENT = "android.a1ex.com.homework10.extra.UPDATE_STUDENT";
    public static final String EXTRA_ID_STUDENT = "android.a1ex.com.homework10.extra.ID_STUDENT";

    public static final String EXTRA_NAME_BACKUP = "android.a1ex.com.homework10.extra.NAME_BACKUP";

    public static final int REQUEST_CODE_GET_GROUPS = 11;
    public static final int REQUEST_CODE_SAVE_GROUP = 12;
    public static final int REQUEST_CODE_DELETE_GROUP = 13;
    public static final int REQUEST_CODE_UPDATE_GROUP = 14;
    public static final int REQUEST_CODE_GROUP_STUDENT = 15;

    public static final int REQUEST_CODE_GET_STUDENTS = 21;
    public static final int REQUEST_CODE_GET_STUDENT = 22;
    public static final int REQUEST_CODE_SAVE_STUDENT = 23;
    public static final int REQUEST_CODE_DELETE_STUDENT = 24;
    public static final int REQUEST_CODE_UPDATE_STUDENT = 25;

    public static final int REQUEST_CODE_CREATE_BACKUP = 31;
    public static final int REQUEST_CODE_RESTORE_BACKUP = 32;

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void saveGroup(AppCompatActivity activity, Group group) {
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_SAVE_GROUP, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_SAVE_GROUP, group);
        intent.setAction(ACTION_SAVE_GROUP);

        activity.startService(intent);
    }

    public static void saveGroupForStudent(AppCompatActivity activity, Group group) {
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_GROUP_STUDENT, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_SAVE_GROUP, group);
        intent.setAction(ACTION_GROUP_STUDENT);

        activity.startService(intent);
    }

    public static void updateGroup(AppCompatActivity activity, Group group) {
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_UPDATE_GROUP, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_UPDATE_GROUP, group);
        intent.setAction(ACTION_UPDATE_GROUP);

        activity.startService(intent);
    }

    public static void getGroups(AppCompatActivity activity) {
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_GET_GROUPS, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.setAction(ACTION_GET_GROUPS);

        activity.startService(intent);
    }

    public static void deleteGroup(AppCompatActivity activity, Group group) {
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_DELETE_GROUP, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_DELETE_GROUP, group);
        intent.setAction(ACTION_DELETE_GROUP);

        activity.startService(intent);
    }

    public static void saveStudent(AppCompatActivity activity, Student student) {
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_SAVE_STUDENT, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_SAVE_STUDENT, student);
        intent.setAction(ACTION_SAVE_STUDENT);

        activity.startService(intent);
    }

    public static void updateStudent(AppCompatActivity activity, Student student) {
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_UPDATE_STUDENT, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_UPDATE_STUDENT, student);
        intent.setAction(ACTION_UPDATE_STUDENT);

        activity.startService(intent);
    }

    public static void getStudent(AppCompatActivity activity, long id) {
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_GET_STUDENT, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_GET_STUDENT, id);
        intent.setAction(ACTION_GET_STUDENT);

        activity.startService(intent);
    }

    public static void getStudents(AppCompatActivity activity, Group group) {
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_GET_STUDENTS, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_UPDATE_GROUP, group);
        intent.setAction(ACTION_GET_STUDENTS);

        activity.startService(intent);
    }

    public static void deleteStudent(AppCompatActivity activity, Student student) {
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_DELETE_STUDENT, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_DELETE_STUDENT, student);
        intent.setAction(ACTION_DELETE_STUDENT);

        activity.startService(intent);
    }

    public static void createBackup(AppCompatActivity activity, String nameBackup){
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_CREATE_BACKUP, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_NAME_BACKUP, nameBackup);
        intent.setAction(ACTION_CREATE_BACKUP);

        activity.startService(intent);
    }

    public static void restoreBackup(AppCompatActivity activity, String nameBackup){
        Intent intent = new Intent(activity, MyIntentService.class);
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_CODE_RESTORE_BACKUP, intent, 0);

        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_NAME_BACKUP, nameBackup);
        intent.setAction(ACTION_RESTORE_BACKUP);

        activity.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            String action = intent.getAction();
            DataBaseHelper helper = new DataBaseHelper(this);
            PendingIntent pendingIntent = intent.getParcelableExtra(EXTRA_PENDING_INTENT);
            Intent result = new Intent();

            if (ACTION_SAVE_GROUP.equals(action)) {
                Group group = intent.getParcelableExtra(EXTRA_SAVE_GROUP);
                long id = helper.insertGroup(group);
                result.putExtra(EXTRA_ID_GROUP, id);

            } else if (ACTION_GROUP_STUDENT.equals(action)) {
                Group group = intent.getParcelableExtra(EXTRA_SAVE_GROUP);
                long id = helper.insertGroup(group);
                result.putExtra(EXTRA_ID_GROUP, id);

            } else if (ACTION_UPDATE_GROUP.equals(action)) {
                Group group = intent.getParcelableExtra(EXTRA_UPDATE_GROUP);
                long id = helper.updateGroup(group);
                result.putExtra(EXTRA_ID_GROUP, id);

            } else if (ACTION_GET_GROUPS.equals(action)) {
                ArrayList<Group> groups = helper.getGroups();
                result.putExtra(EXTRA_GET_GROUPS, groups);

            } else if (ACTION_DELETE_GROUP.equals(action)) {
                Group group = intent.getParcelableExtra(EXTRA_DELETE_GROUP);
                helper.deleteGroup(group);

            } else if (ACTION_GET_STUDENT.equals(action)) {
                long id = intent.getParcelableExtra(EXTRA_GET_STUDENT);
                Student student = helper.getStudent(id);
                result.putExtra(EXTRA_GET_STUDENT, student);

            } else if (ACTION_SAVE_STUDENT.equals(action)) {
                Student student = intent.getParcelableExtra(EXTRA_SAVE_STUDENT);
                long id = helper.insertStudent(student);
                result.putExtra(EXTRA_ID_STUDENT, id);

            } else if (ACTION_UPDATE_STUDENT.equals(action)) {
                Student student = intent.getParcelableExtra(EXTRA_UPDATE_STUDENT);
                long id = helper.updateStudent(student);
                result.putExtra(EXTRA_ID_STUDENT, id);

            } else if (ACTION_GET_STUDENTS.equals(action)) {
                Group group = intent.getParcelableExtra(EXTRA_UPDATE_GROUP);
                ArrayList<Student> students = helper.getGroupStudents(group);
                result.putExtra(EXTRA_GET_STUDENTS, students);

            } else if (ACTION_DELETE_STUDENT.equals(action)) {
                Student student = intent.getParcelableExtra(EXTRA_DELETE_STUDENT);
                helper.deleteStudent(student);

            } else if (ACTION_CREATE_BACKUP.equals(action)) {
                String nameBackup = intent.getStringExtra(EXTRA_NAME_BACKUP);
                File folder = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/MyFolder");
                ArrayList<Group> mGroups = helper.getGroups();
                ArrayList<Student> mStudents = helper.getStudents();
                DataJson mDataJson = new DataJson(mGroups, mStudents);
                Gson gson = new GsonBuilder().create();
                String json = gson.toJson(mDataJson);
                saveExternalFile(folder, nameBackup, json);

            } else if (ACTION_RESTORE_BACKUP.equals(action)) {
                String nameBackup = intent.getStringExtra(EXTRA_NAME_BACKUP);
                File folder = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/MyFolder");
                String json = readExternalFile(folder, nameBackup);
                Gson gson = new GsonBuilder().create();
                DataJson mDataJson = gson.fromJson(json, DataJson.class);
                ArrayList<Group> mGroups = mDataJson.getGroups();
                ArrayList<Student> mStudents = mDataJson.getStudents();
                helper.deleteAll();
                helper.addGroups(mGroups);
                helper.addStudents(mStudents);

            }

            try {
                pendingIntent.send(this, Activity.RESULT_OK, result);
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveExternalFile(File folder, String fileName, String data) {
        try {
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(folder, fileName);

            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));

            writer.write(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readExternalFile(File folder, String fileName) {
        try {
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(folder, fileName);

            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
