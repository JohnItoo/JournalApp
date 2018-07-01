package com.itoo.ohue.journalapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.itoo.ohue.journalapp.R;

import org.w3c.dom.Text;

public class ViewJournalDetailsActivity extends AppCompatActivity {
    public static final String TOPIC = "topic";
    public static final String BODY = "body";
    public static final String DATE = "DATE";
    public static final String TAG = "tag";
    private TextView detailsTitle;
    private TextView detailsBody;
    private TextView detailsDate;
    private int itemId;
    private String title;
    private String date;
    private String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journal_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailsBody = (TextView) findViewById(R.id.details_body);
        detailsDate = (TextView) findViewById(R.id.details_date);
        detailsTitle = (TextView) findViewById(R.id.details_title);
        Intent openingIntent = getIntent();
        if (openingIntent.hasExtra(TOPIC)) {
            title = openingIntent.getStringExtra(BODY);
            date = openingIntent.getStringExtra(DATE);
            body = openingIntent.getStringExtra(TOPIC);
            itemId = openingIntent.getIntExtra(TAG, 0);

            detailsTitle.setText(title);
            detailsDate.setText(date);
            detailsBody.setText(body);

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_delete) {
            Intent journalDeleteItent = new Intent(this, JournalActivity.class);
            journalDeleteItent.putExtra(TAG, itemId);
            startActivity(journalDeleteItent);
            finish();
        } else if (item.getItemId() == R.id.action_edit) {
            Intent journalEditIntent = new Intent(this, CreateJournalActivity.class);
            journalEditIntent.putExtra(TAG, itemId);
            journalEditIntent.putExtra(TOPIC, title);
            journalEditIntent.putExtra(DATE, date);
            journalEditIntent.putExtra(BODY, body);
            startActivity(journalEditIntent);
            finish();

            }
        return super.onOptionsItemSelected(item);
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.journal_actions, menu);
            return true;
        }


    }

