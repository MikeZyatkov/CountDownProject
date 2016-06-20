package net.zyatkov.countdownproject.ui.add;

import android.graphics.Bitmap;

import net.zyatkov.countdownproject.data.DataManager;
import net.zyatkov.countdownproject.data.model.Event;
import net.zyatkov.countdownproject.ui.base.BasePresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/* Define presenter for edit event operations */

public class AddEventPresenter extends BasePresenter<AddEventMVPView> {

    private Subscription mSubscription;
    private DataManager mDataManager;

    @Inject
    public AddEventPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(AddEventMVPView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void saveEvent(String title, String description, String date, String time, Bitmap icon) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("ru,Ru"));
        String dateTimeString = date + ' ' + time;
        Date eventDate = format.parse(dateTimeString);
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setDate(eventDate);
        event.setIcon(icon);
        checkViewAttached();
        mSubscription = mDataManager.setEvents(event)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Event>() {
                    @Override
                    public void onCompleted() {
                        getView().onEventSaveSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Event event) {

                    }
                });
    }
}
