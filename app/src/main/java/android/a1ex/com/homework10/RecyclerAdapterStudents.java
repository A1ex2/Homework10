package android.a1ex.com.homework10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapterStudents extends RecyclerView.Adapter<RecyclerAdapterStudents.StudentViewHolder> {
    private int mResourse;
    private ArrayList<Student> mStudents;
    private LayoutInflater mInflater;

    public RecyclerAdapterStudents(Context context, int resourse, ArrayList<Student> Students){
        mResourse = resourse;
        mStudents = Students;
        mInflater = LayoutInflater.from(context);
    }

    public interface ActionListener {
        void onClick(Student Student);
    }

    private ActionListener mListener;

    public void setActionListener(ActionListener listener){
        mListener = listener;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup viewStudent, int i) {
        View view = mInflater.inflate(mResourse, null);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder StudentViewHolder, int i) {
        final Student Student = mStudents.get(i);
        StudentViewHolder.set(Student);

        if (mListener != null){
            StudentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(Student);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;

        public StudentViewHolder( View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.fio);
        }

        public void set(Student student){
            mName.setText(student.toString());
        }
    }
}
