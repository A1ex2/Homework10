package android.a1ex.com.homework10;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataJson {
    @SerializedName("Groups")
    private ArrayList<Group> mGroups;

    @SerializedName("Students")
    private ArrayList<Student> mStudents;

    public DataJson(ArrayList<Group> groups, ArrayList<Student> students) {
       this.mGroups = groups;
        this.mStudents = students;
    }

    public ArrayList<Group> getGroups() {
        return mGroups;
    }

    public void setGroups(ArrayList<Group> groups) {
        mGroups = groups;
    }

    public ArrayList<Student> getStudents() {
        return mStudents;
    }

    public void setStudents(ArrayList<Student> students) {
        mStudents = students;
    }
}
