package net.zyatkov.countdownproject.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.zyatkov.countdownproject.data.model.Event;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.UUID;

public final class DB {

    public DB() { }

    //Define table Event and her columns
    public static abstract class EventTable {

        public static final String TABLE_NAME = "event";

        public static final String COLUMN_EVENT_UUID = "event_uuid";
        public static final String COLUMN_EVENT_TITLE = "event_title";
        public static final String COLUMN_EVENT_DESCRIPTION = "event_description";
        public static final String COLUMN_EVENT_DATE = "event_date";
        public static final String COLUMN_EVENT_ICON = "event_icon";

        //Define script for create table
        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_EVENT_UUID + " TEXT PRIMARY KEY, " +
                        COLUMN_EVENT_TITLE + " TEXT NOT NULL, " +
                        COLUMN_EVENT_DESCRIPTION + " TEXT, " +
                        COLUMN_EVENT_DATE + " LONG NOT NULL, " +
                        COLUMN_EVENT_ICON + " BLOB NOT NULL" +
                " ); ";

        //Define method for writes and updates table
        public static ContentValues toContentValues(Event event) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EVENT_UUID, event.getId().toString());
            values.put(COLUMN_EVENT_TITLE, event.getTitle());
            if (event.getDescription() != null) values.put(COLUMN_EVENT_DESCRIPTION, event.getDescription());
            values.put(COLUMN_EVENT_DATE, event.getDate().getTime());
            values.put(COLUMN_EVENT_ICON, getBytes(event.getIcon()));
            return values;
        }

        //Define method for read data
        public static Event parseCursor(Cursor cursor) {
            UUID eventId = UUID.fromString(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_UUID)));
            Event event = new Event(eventId);
            event.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_TITLE)));
            event.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EVENT_DESCRIPTION)));
            long eventDate = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_EVENT_DATE));
            event.setDate(new Date(eventDate));
            event.setIcon(getBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_EVENT_ICON))));
            return event;
        }

        //convert from bitmap to byte array
        public static byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }

        //convert from byte array to bitmap
        public static Bitmap getBitmap(byte[] data) {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }


    }
}
