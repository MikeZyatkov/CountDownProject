package net.zyatkov.countdownproject.ui.main;

import net.zyatkov.countdownproject.data.DataManager;
import net.zyatkov.countdownproject.data.model.Event;
import net.zyatkov.countdownproject.ui.adapter.EventsAdapter;
import net.zyatkov.countdownproject.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainMVPView> {

    private DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMVPView mvpView) {
        super.attachView(mvpView);
    }

    public void getEvents(){
       mSubscription = mDataManager.getEvents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Event>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Event> eventList) {
                        getView().showEvents(eventList);
                    }
                });
    }

    public void onResume(){
        getEvents();
    }




}
