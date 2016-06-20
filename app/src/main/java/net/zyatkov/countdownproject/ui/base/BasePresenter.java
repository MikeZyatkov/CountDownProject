package net.zyatkov.countdownproject.ui.base;

/* Class define base behavior of Presenter */

public class BasePresenter<T extends MVPView> implements Presenter<T> {

    private T mMVPView;

    @Override
    public void attachView(T mvpView) {
        mMVPView = mvpView;
    }

    @Override
    public void detachView() {
        mMVPView = null;
    }

    public T getView(){
        return mMVPView;
    }

    public boolean isViewAttached(){
        return mMVPView != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MVPViewNotAttachedException();
    }


    public static class MVPViewNotAttachedException extends RuntimeException {
        public MVPViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
