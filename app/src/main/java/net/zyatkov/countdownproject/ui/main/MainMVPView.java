package net.zyatkov.countdownproject.ui.main;

import net.zyatkov.countdownproject.data.model.Event;
import net.zyatkov.countdownproject.ui.base.MVPView;

import java.util.List;

public interface MainMVPView extends MVPView {

    void showEvents(List<Event> eventList);

}
