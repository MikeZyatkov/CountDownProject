package net.zyatkov.countdownproject.ui.add;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.zyatkov.countdownproject.R;
import net.zyatkov.countdownproject.ui.base.BaseActivity;
import net.zyatkov.countdownproject.utility.Utility;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;


public class AddEventActivity extends BaseActivity implements AddEventMVPView, DatePickerFragment.OnDateSelectedListener, TimePickerFragment.onTimeSelectedListener {

    private final static int REQUEST_CAMERA = 1;
    private final static int REQUEST_GALLERY = 2;

    private TextView dateTextView;
    private TextView timeTextView;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private ImageView iconImageView;
    private String userChoosenTask;

    @Inject AddEventPresenter mAddEventPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        getActivityComponent().inject(this);
        mAddEventPresenter.attachView(this);


        dateTextView = (TextView) findViewById(R.id.activity_add_event_tv_date);
        timeTextView = (TextView) findViewById(R.id.activity_add_event_tv_time);
        titleEditText = (EditText) findViewById(R.id.activity_add_event_et_title);
        descriptionEditText = (EditText) findViewById(R.id.activity_add_event_et_description);
        iconImageView = (ImageView) findViewById(R.id.activity_add_iv_icon);

        initToolbar();

        //Call date picker dialog
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("ru,RU"));
        dateTextView.setText(dateFormat.format(date));
        dateTextView.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getFragmentManager(), "datePicker");
        });

        //Call time picker dialog
        dateFormat = new SimpleDateFormat("HH:mm", new Locale("ru,Ru"));
        timeTextView.setText(dateFormat.format(date));
        timeTextView.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getFragmentManager(), "timePicker");
        });

        //Choose image for icon
        iconImageView.setOnClickListener(v -> selectImageSource());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                iconImageView.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_GALLERY) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iconImageView.setImageBitmap(bitmap);
            }
        }
    }

    //Initialized toolbar
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_add_event_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //Show Date in TextView
    @Override
    public void onDateSelected(String formattedDate) {
        dateTextView.setText(formattedDate);
    }

    //Show Time in TextView
    @Override
    public void onTimeSelected(String formattedTime) {
        timeTextView.setText(formattedTime);
    }

    //Show message if event save successfully
    @Override
    public void onEventSaveSuccess() {
        finish();
    }

    //Create toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_event_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Action when select item on toolbar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_add_event_toolbar_menu_save:
                iconImageView.buildDrawingCache();
                Bitmap bitmap = iconImageView.getDrawingCache();
                    try {
                        mAddEventPresenter.saveEvent(titleEditText.getText().toString(), descriptionEditText.getText().toString(), dateTextView.getText().toString(), timeTextView.getText().toString(), bitmap);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("camera"))
                        cameraIntent();
                    else if (userChoosenTask.equals("gallery"))
                        galleryIntent(getResources());
                } else {

                }
                break;
        }
    }

    //Choose image source
    private void selectImageSource() {
        Resources res = getResources();
        final CharSequence[] items =
                {res.getString(R.string.activity_add_event_alert_dialog_camera),
                        res.getString(R.string.activity_add_event_alert_dialog_gallery),
                        res.getString(R.string.activity_add_event_alert_dialog_cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.activity_add_event_alert_dialog_title);
        builder.setItems(items, (dialog, which) -> {
            boolean result = Utility.checkPermission(AddEventActivity.this);
            if (items[which].equals(res.getString(R.string.activity_add_event_alert_dialog_camera))) {
                userChoosenTask = "camera";
                if (result) {
                    cameraIntent();
                    }
            } else if (items[which].equals(res.getString(R.string.activity_add_event_alert_dialog_gallery))) {
                userChoosenTask = "gallery";
                if (result) {
                    galleryIntent(res);
                }
            } else if (items[which].equals(res.getString(R.string.activity_add_event_alert_dialog_cancel))) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    private void galleryIntent(Resources res) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, res.getString(R.string.activity_add_event_alert_dialog_chooser)), REQUEST_GALLERY);
    }
}
