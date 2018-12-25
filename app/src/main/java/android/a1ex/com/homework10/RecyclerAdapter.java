package android.a1ex.com.homework10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.GroupViewHolder> {
    private int mResourse;
    private ArrayList<Group> mGroups;
    private LayoutInflater mInflater;

    public RecyclerAdapter(Context context, int resourse, ArrayList<Group> groups){
        mResourse = resourse;
        mGroups = groups;
        mInflater = LayoutInflater.from(context);
    }

    public interface ActionListener {
        void onClick(Group group);
    }

    private ActionListener mListener;

    public void setActionListener(ActionListener listener){
        mListener = listener;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(mResourse, null);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder groupViewHolder, int i) {
        final Group group = mGroups.get(i);
        groupViewHolder.set(group);

        if (mListener != null){
            groupViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(group);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;

        public GroupViewHolder( View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.nameGroup);
        }

        public void set(Group group){
            mName.setText(group.name);
        }
    }
}
