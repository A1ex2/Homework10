package android.a1ex.com.homework10;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {
    private DataBaseHelper helper;
    private RecyclerView recycler;
    private ArrayList<Group> mGroups = new ArrayList<>();
    private RecyclerAdapter adapter;

    public static final String EXTRA_GROUP = "android.a1ex.com.homework10.extra.GROUP";
    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        helper = new DataBaseHelper(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(this, R.layout.groupe_item, mGroups);
        recycler.setAdapter(adapter);

        adapter.setActionListener(new RecyclerAdapter.ActionListener() {
            @Override
            public void onClick(Group group) {
                Intent intent = new Intent(MainActivity.this, ActivityGroup.class);
                intent.putExtra(EXTRA_GROUP, group);
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
                Group group = mGroups.get(fromPos);
                helper.deleteGroup(group);
                Toast.makeText(MainActivity.this, "удалено " + group.name, Toast.LENGTH_LONG).show();

                initList();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recycler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String nameBackup;
        SharedPreferences preferences;

        switch (item.getItemId()) {
            case R.id.itemPreferences:

                startActivity(new Intent(this, Settings.class));

                break;
            case R.id.makeBackup: {
                preferences = PreferenceManager.getDefaultSharedPreferences(this);

                if (!preferences.getBoolean("check_box_backup", true)) {
                    Toast.makeText(this, "Настройками запрещено ипользование бекапов", Toast.LENGTH_LONG).show();
                    return false;
                }

                nameBackup = preferences.getString("edit_text_name_backup", "МойФайлБекапа") + ".txt";

                if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File folder = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/MyFolder");

                        ArrayList<Group> mGroups = helper.getGroups();
                        ArrayList<Student> mStudents = helper.getStudents();

                        DataJson mDataJson = new DataJson(mGroups, mStudents);
                        Gson gson = new GsonBuilder().create();
                        String json = gson.toJson(mDataJson);
                        saveExternalFile(folder, nameBackup, json);

                        Toast.makeText(this, "Бекап создан", Toast.LENGTH_LONG).show();
                    }
                } else {
                    requestPermission();
                }
            }
            break;
            case R.id.restoreBackup:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);

                if (!preferences.getBoolean("check_box_backup", true)) {
                    Toast.makeText(this, "Настройками запрещено ипользование бекапов", Toast.LENGTH_LONG).show();
                    return false;
                }

                nameBackup = preferences.getString("edit_text_name_backup", "МойФайлБекапа") + ".txt";

                if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File folder = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/MyFolder");

                        String json = readExternalFile(folder, nameBackup);
                        Gson gson =new GsonBuilder().create();
                        DataJson mDataJson = gson.fromJson(json, DataJson.class);

                        ArrayList<Group> mGroups = mDataJson.getGroups();
                        ArrayList<Student> mStudents = mDataJson.getStudents();

                        helper.deleteAll();
                        Toast.makeText(this, "База очищена", Toast.LENGTH_LONG).show();

                        helper.addGroups(mGroups);
                        helper.addStudents(mStudents);

                        Toast.makeText(this, "Бекап загружен", Toast.LENGTH_LONG).show();

                        initList();
                    }
                } else {
                    requestPermission();
                }
                break;
        }
        return false;
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

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            requestPermissions(permissions, 0);
        }
    }


    private void initList() {
        ArrayList<Group> items = helper.getGroups();

        mGroups.clear();
        mGroups.addAll(items);

        adapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addGroupe:

                Intent intent = new Intent(MainActivity.this, ActivityGroup.class);
                intent.putExtra(EXTRA_GROUP, new Group());
                startActivityForResult(intent, REQUEST_CODE);

                break;
        }
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
