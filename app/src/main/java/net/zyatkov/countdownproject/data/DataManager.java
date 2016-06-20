package net.zyatkov.countdownproject.data;

import net.zyatkov.countdownproject.data.db.DatabaseHelper;
import net.zyatkov.countdownproject.data.model.Event;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

//Class manage data
public class DataManager {

    private final DatabaseHelper mDatabaseHelper;
    
    @Inject
    public DataManager(DatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    //Define method for receive data
    public Observable<List<Event>> getEvents() {
        return mDatabaseHelper.getEvents();
    }

    //Define method for set data
    public Observable<Event> setEvents(Collection<Event> newEvent) {
        return mDatabaseHelper.setEvents(newEvent);
    }

    public Observable<Event> setEvents(Event newEvent) {
        return mDatabaseHelper.setEvents(newEvent);
    }
}
