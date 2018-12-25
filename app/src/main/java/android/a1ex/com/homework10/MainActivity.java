package android.a1ex.com.homework10;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

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

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
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

    private void initList(){
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
