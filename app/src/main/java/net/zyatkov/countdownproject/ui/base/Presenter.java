package net.zyatkov.countdownproject.ui.base;

/* Presenter Interface (MVP architecture) */

public interface Presenter<V extends MVPView> {

    void attachView(V mvpView);

    void detachView();

}
