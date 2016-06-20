package net.zyatkov.countdownproject.injection.module;

import android.support.annotation.NonNull;

import net.zyatkov.countdownproject.data.DataManager;
import net.zyatkov.countdownproject.injection.ActivityScope;
import net.zyatkov.countdownproject.ui.base.Presenter;
import net.zyatkov.countdownproject.ui.add.AddEventPresenter;

import dagger.Module;
import dagger.Provides;

/* Class define methods for provide presenter dependencies */
@Module
public class ActivityModule {

    @Provides
    @NonNull
    @ActivityScope
    public Presenter provideEventEditPresenter(DataManager dataManager){
        return new AddEventPresenter(dataManager);
    }

}
