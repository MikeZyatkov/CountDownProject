package net.zyatkov.countdownproject.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.zyatkov.countdownproject.CountDownApplication;
import net.zyatkov.countdownproject.R;
import net.zyatkov.countdownproject.data.DataManager;
import net.zyatkov.countdownproject.data.model.Event;
import net.zyatkov.countdownproject.ui.main.MainMVPView;
import net.zyatkov.countdownproject.ui.main.MainPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.inject.Inject;

import rx.Observable;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    public interface OnItemClickedListener {
        void onItemClicked(UUID eventUUID);
    }

    private List<Event> mEventList;
    private final static String TAG = "EventsAdapter";
    private OnItemClickedListener mListener;
    private Resources mResources;


    public EventsAdapter(OnItemClickedListener listener) {
        mEventList = new ArrayList<>();
        mListener = listener;
    }

    public void setEvents(List<Event> eventList){
        mEventList = eventList;
    }

    //Create new Views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        mResources = context.getResources();

        View eventView = inflater.inflate(R.layout.item_event, parent, false);
        final ViewHolder viewHolder = new ViewHolder(eventView);
        viewHolder.itemView.setOnClickListener(v -> {
            final int position = viewHolder.getAdapterPosition();
            final UUID eventUUID = mEventList.get(position).getId();
            mListener.onItemClicked(eventUUID);
        });

        return viewHolder;
    }

    //Replace the content of view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = mEventList.get(position);

        Date eventDate = event.getDate();
        Date currentDate = new Date();
        String goneOrLeft;
        if (currentDate.getTime() < eventDate.getTime())
            goneOrLeft = mResources.getString(R.string.item_event_tv_left);
        else
            goneOrLeft = mResources.getString(R.string.item_event_tv_gone);

        TextView titleTextView = holder.titleTextView;
        titleTextView.setText(event.getTitle());

        TextView dateTimeTextView = holder.dateTimeTextView;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("ru,RU"));
        String date = dateFormat.format(eventDate);
        dateTimeTextView.setText(date);

        TextView timerTextView = holder.timerTextView;
        EventTimer timer = new EventTimer(timerTextView, eventDate);
        timer.start();

        ImageView iconImageView = holder.iconImageView;
        iconImageView.setImageBitmap(event.getIcon());

        TextView goneOrLeftTextView = holder.goneOrLeftTextView;
        goneOrLeftTextView.setText(goneOrLeft);

    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView dateTimeTextView;
        public TextView timerTextView;
        public TextView goneOrLeftTextView;
        public ImageView iconImageView;


        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.item_event_tv_title);
            dateTimeTextView = (TextView) itemView.findViewById(R.id.item_event_tv_date_time);
            timerTextView = (TextView) itemView.findViewById(R.id.item_event_tv_timer);
            iconImageView = (ImageView) itemView.findViewById(R.id.item_event_iv_icon);
            goneOrLeftTextView = (TextView) itemView.findViewById(R.id.item_event_tv_gone_or_left);
        }


    }

}
