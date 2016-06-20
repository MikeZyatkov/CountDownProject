package net.zyatkov.countdownproject;

import android.app.Application;
import android.content.Context;

import net.zyatkov.countdownproject.injection.component.AppComponent;
import net.zyatkov.countdownproject.injection.component.DaggerAppComponent;
import net.zyatkov.countdownproject.injection.module.AppModule;

/* Class initializes and stores dagger components */

public class CountDownApplication extends Application {

    private AppComponent mAppComponent;

    public static CountDownApplication getApp(Context context) {
        return (CountDownApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getAppComponent().inject(this);
    }

}
