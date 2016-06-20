package net.zyatkov.countdownproject.injection.component;

import net.zyatkov.countdownproject.injection.ActivityScope;
import net.zyatkov.countdownproject.injection.module.ActivityModule;
import net.zyatkov.countdownproject.ui.adapter.EventTimer;
import net.zyatkov.countdownproject.ui.add.AddEventActivity;
import net.zyatkov.countdownproject.ui.info.InfoEventActivity;
import net.zyatkov.countdownproject.ui.main.MainActivity;

import dagger.Component;

/* Interface provide dependency to activity*/

@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
@ActivityScope
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(AddEventActivity addEventActivity);

    void inject(InfoEventActivity infoEventActivity);

}
