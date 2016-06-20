package net.zyatkov.countdownproject.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import net.zyatkov.countdownproject.R;
import net.zyatkov.countdownproject.data.model.Event;
import net.zyatkov.countdownproject.ui.adapter.DividerItemDecoration;
import net.zyatkov.countdownproject.ui.adapter.EventsAdapter;
import net.zyatkov.countdownproject.ui.add.AddEventActivity;
import net.zyatkov.countdownproject.ui.base.BaseActivity;
import net.zyatkov.countdownproject.ui.info.InfoEventActivity;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMVPView, EventsAdapter.OnItemClickedListener {

    @Inject Context mContext;
    @Inject MainPresenter mPresenter;

    EventsAdapter mEventsAdapter;

    private static final String TAG = "MainActivity";
    public static final String EVENT_UUID = "event_uuid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.getEvents();
        mEventsAdapter = new EventsAdapter(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);

        initToolbar();
        initFAB();
        initRecyclerView(recyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();

    }

    //Initialized toolbar
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    //Initialized FloatingActionBar
    private void initFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_main_fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddEventActivity.class);
            startActivity(intent);
        });
    }

    //Initialized RecyclerView
    private void initRecyclerView(RecyclerView recyclerView){
        recyclerView.setAdapter(mEventsAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.divider)));

    }

    //Show events in view
    @Override
    public void showEvents(List<Event> eventList) {
        mEventsAdapter.setEvents(eventList);
        mEventsAdapter.notifyDataSetChanged();
    }

    //Show Event Activity
    @Override
    public void onItemClicked(UUID eventUUID) {
        Intent intent = new Intent(this, InfoEventActivity.class);
        intent.putExtra(EVENT_UUID, eventUUID);
        startActivity(intent);
    }
}
