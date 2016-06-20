package net.zyatkov.countdownproject.data.model;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.UUID;

/* Class describe entitity Event */

public class Event {

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private Bitmap mIcon;

    public Event() {
        mId = UUID.randomUUID();
    }

    public Event(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Bitmap getIcon() {
        return mIcon;
    }

    public void setIcon(Bitmap icon) {
        mIcon = icon;
    }
}
