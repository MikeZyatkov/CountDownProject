package net.zyatkov.countdownproject.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.zyatkov.countdownproject.CountDownApplication;
import net.zyatkov.countdownproject.injection.component.ActivityComponent;
import net.zyatkov.countdownproject.injection.component.DaggerActivityComponent;
import net.zyatkov.countdownproject.injection.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule())
                    .appComponent(CountDownApplication.getApp(this).getAppComponent())
                    .build();
        }
        return mActivityComponent;
    }
}
