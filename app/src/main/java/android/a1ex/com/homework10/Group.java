package android.a1ex.com.homework10;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Group implements Parcelable {
    public static final String TABLE_NAME = "Groups";
    public static final String COLUM_NAME = "NameGroup";
    public static final String COLUM_ID = "_id";

    @SerializedName("id")
    public long id;

    @SerializedName("Name")
    public String name;

    public Group() {
        this.id = -1;
    }

    public Group(String name) {
        this.name = name;
        this.id = -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
    }

    protected Group(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
