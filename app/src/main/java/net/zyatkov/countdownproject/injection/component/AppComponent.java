package net.zyatkov.countdownproject.injection.component;

/* Class connect AppModule and Application */

import android.content.Context;

import net.zyatkov.countdownproject.CountDownApplication;
import net.zyatkov.countdownproject.data.DataManager;
import net.zyatkov.countdownproject.data.db.DatabaseHelper;
import net.zyatkov.countdownproject.injection.module.AppModule;
import net.zyatkov.countdownproject.ui.adapter.EventsAdapter;
import net.zyatkov.countdownproject.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    Context context();
    DataManager dataManager();
    DatabaseHelper databaseHepler();

    void inject(CountDownApplication application);

    void inject(EventsAdapter eventsAdapter);
}
