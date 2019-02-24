package mad.nil.messageme;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class EmailObj implements Serializable, Comparable<EmailObj> {

    public static final String DATE_FORMAT = "dd-MM-yy, HH:mm a";
    public static final String UTC = "UTC";

    String message;

    String fromUserId;

    String fromUsername;

    String toUserId;

    String sentDate;

    Boolean read = false;

    Integer key;

    public EmailObj() {
    }

    public EmailObj(String message, String fromUserId, String fromUsername, String toUserId) {
        this.message = message;
        this.fromUserId = fromUserId;
        this.fromUsername = fromUsername;
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    @Exclude
    public Date getFormattedDate() {
        Date formattedDate = null;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone(UTC));

        try {
            formattedDate = format.parse(sentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public void setCurrentDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        format.setTimeZone(TimeZone.getTimeZone(UTC));
        sentDate = format.format(date);
    }

    @Override
    public int compareTo(@NonNull EmailObj email) {
        if(email != null && getSentDate() != null) {
            if (getSentDate().equals(email.getSentDate())
                    && getToUserId().equals(email.getToUserId())
                    && getFromUserId().equals(email.getFromUserId())) {
                return 0;
            }
            return email.getKey().compareTo(key);
        }
        return -1;
    }
}
