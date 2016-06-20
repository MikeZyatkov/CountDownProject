package net.zyatkov.countdownproject.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import net.zyatkov.countdownproject.R;
import net.zyatkov.countdownproject.ui.base.BaseActivity;
import net.zyatkov.countdownproject.ui.main.MainActivity;

import java.util.UUID;

import javax.inject.Inject;

public class InfoEventActivity extends BaseActivity implements InfoEventMVPView {

    @Inject InfoEventPresenter mInfoEventPresenter;
    UUID eventUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_event);
        getActivityComponent().inject(this);
        mInfoEventPresenter.attachView(this);
        initToolbar();
        Intent intent = getIntent();
        eventUUID = (UUID) intent.getSerializableExtra(MainActivity.EVENT_UUID);
    }

    //Initialization toolbar
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_info_event_toolbar);
        setSupportActionBar(toolbar);
    }
}
