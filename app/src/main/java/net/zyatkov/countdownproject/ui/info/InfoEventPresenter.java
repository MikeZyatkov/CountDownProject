package net.zyatkov.countdownproject.ui.info;

import net.zyatkov.countdownproject.data.DataManager;
import net.zyatkov.countdownproject.ui.base.BasePresenter;

import javax.inject.Inject;

public class InfoEventPresenter extends BasePresenter<InfoEventMVPView> {

    private DataManager mDataManager;

    @Inject
    public InfoEventPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
}
