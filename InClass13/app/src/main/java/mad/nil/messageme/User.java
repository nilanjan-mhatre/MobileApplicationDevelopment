package mad.nil.messageme;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class User implements Serializable, Cloneable {

    @SerializedName("user_id")
    String userId;

    @SerializedName("user_fname")
    String fullName;

    @SerializedName("user_email")
    String email;

    @SerializedName("user_password")
    String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String userId, String firstName, String lastName) {
        this.userId = userId;
        this.fullName = firstName + " " + lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
