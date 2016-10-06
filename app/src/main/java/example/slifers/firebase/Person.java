package example.slifers.firebase;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private String uid;
    String first;
    private String last;
    private String dob;
    private String zipcode;
    private String phone;


    public Person(String uid, String first, String last, String dob, String zipcode, String phone) {
        this.uid = uid;
        this.first = first;
        this.last = last;
        this.dob = dob;
        this.zipcode = zipcode;
        this.phone = phone;

    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.first);
        dest.writeString(this.last);
        dest.writeString(this.dob);
        dest.writeString(this.zipcode);
        dest.writeString(this.phone);

    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.uid = in.readString();
        this.first = in.readString();
        this.last = in.readString();
        this.dob = in.readString();
        this.zipcode = in.readString();
        this.phone = in.readString();

    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person Person = (Person) o;

        if (uid != null ? !uid.equals(Person.uid) : Person.uid != null) return false;
        if (first != null ? !first.equals(Person.first) : Person.first != null) return false;
        return last != null ? last.equals(Person.last) : Person.last == null;

    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (last != null ? last.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);

        return result;
    }
}
