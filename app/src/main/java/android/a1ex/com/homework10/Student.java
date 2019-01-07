package android.a1ex.com.homework10;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Student implements Parcelable {
    public static final String TABLE_NAME = "Students";
    public static final String COLUM_FIRST_NAME = "FirstName";
    public static final String COLUM_LAST_NAME = "LastName";
    public static final String COLUM_AGE = "Age";
    public static final String COLUM_ID = "_id";
    public static final String COLUM_ID_GROUP = "_idGroup";

    @SerializedName("id")
    public long id;
    @SerializedName("idGroup")
    public long idGroup;

    @SerializedName("FirstName")
    public String firstName;
    @SerializedName("LastName")
    public String lastName;
    @SerializedName("Age")
    public int age;

    public Student() {
        this.id = -1;
    }

    public Student(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
//        return "Student{" +
//                "id='" + id + '\'' +
//                "firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", age=" + age +
//                '}';

        return "" + firstName + " " + lastName + ", возраст " + age;
//        return String.format("%d %d, возраст %f", firstName, lastName, age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.idGroup);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeInt(this.age);
    }

    protected Student(Parcel in) {
        this.id = in.readLong();
        this.idGroup = in.readLong();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
