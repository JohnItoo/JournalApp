package com.itoo.ohue.journalapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itoo.ohue.journalapp.R;

public class CreateJournalActivity extends AppCompatActivity {
    private EditText journalTitle;
    private EditText journalBody;
    private Button saveJournalEntry;
    public static final String TITLE_KEY = "title_key";
    public static final String BODY_KEY = "body_key";
    private int tagId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_journal);

        journalTitle = (EditText) findViewById(R.id.journal_input_description);
        journalBody = (EditText) findViewById(R.id.journal_input_body);
        saveJournalEntry = (Button) findViewById(R.id.save_button);

        saveJournalEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnJournalIntent = new Intent();
                if(!TextUtils.isEmpty(journalTitle.getText()) && !TextUtils.isEmpty(journalBody.getText())) {
                    returnJournalIntent.putExtra(TITLE_KEY, journalTitle.getText().toString());
                    returnJournalIntent.putExtra(BODY_KEY, journalBody.getText().toString());
                    if(tagId != 0) {
                        returnJournalIntent.putExtra(ViewJournalDetailsActivity.TAG, 0);
                    }
                    setResult(RESULT_OK,returnJournalIntent);
                } else {
                    setResult(RESULT_CANCELED, returnJournalIntent);
                }
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent openingIntent = getIntent();
        if(openingIntent.hasExtra(ViewJournalDetailsActivity.TAG)) {
            tagId = openingIntent.getIntExtra(ViewJournalDetailsActivity.TAG, 0);
            journalTitle.setText(openingIntent.getStringExtra(ViewJournalDetailsActivity.TOPIC));
            journalBody.setText(openingIntent.getStringExtra(ViewJournalDetailsActivity.BODY));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);


    }
}
