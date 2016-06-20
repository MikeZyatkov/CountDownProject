package net.zyatkov.countdownproject.data.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import net.zyatkov.countdownproject.data.model.Event;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

//Class for write\read objects in our database. Using sqlBrite
public class DatabaseHelper {

    private final BriteDatabase mDb;

    //Create sqlBrite instance and wrap sqLiteOpenHelper
    @Inject
    public DatabaseHelper(DBOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
    }

    //Define method for insert Events to database
    public Observable<Event> setEvents(final Collection<Event> newEvents) {
        return Observable.create((subscriber) -> {
           if (subscriber.isUnsubscribed()) return;
            BriteDatabase.Transaction transaction = mDb.newTransaction();
            try {
                for (Event event : newEvents) {
                    long result = mDb.insert(DB.EventTable.TABLE_NAME, DB.EventTable.toContentValues(event), SQLiteDatabase.CONFLICT_REPLACE);
                    if (result >= 0) subscriber.onNext(event);
                }
                transaction.markSuccessful();
                subscriber.onCompleted();
            }finally {
                transaction.end();
            }
        });
    }

    public Observable<Event> setEvents(Event newEvent) {
        return Observable.create(subscriber -> {
            if (subscriber.isUnsubscribed()) return;
            BriteDatabase.Transaction transaction = mDb.newTransaction();
            try {
                mDb.insert(DB.EventTable.TABLE_NAME, DB.EventTable.toContentValues(newEvent), SQLiteDatabase.CONFLICT_REPLACE);
                transaction.markSuccessful();
                subscriber.onCompleted();
            }finally {
                transaction.end();
            }
        });
    }

    //Define method for select Events from database
    public Observable<List<Event>> getEvents() {
        return mDb.createQuery(DB.EventTable.TABLE_NAME, "SELECT * FROM " + DB.EventTable.TABLE_NAME)
                .mapToList(cursor -> DB.EventTable.parseCursor(cursor));
    }


}
