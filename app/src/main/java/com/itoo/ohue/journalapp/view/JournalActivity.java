package com.itoo.ohue.journalapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.itoo.ohue.journalapp.MainActivity;
import com.itoo.ohue.journalapp.R;
import com.itoo.ohue.journalapp.data.JournalAdapter;
import com.itoo.ohue.journalapp.data.JournalEntity;
import com.itoo.ohue.journalapp.data.JournalViewModel;

import java.util.List;

public class JournalActivity extends AppCompatActivity {
    private JournalAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private JournalViewModel journalViewModel;
    public static final int NEW_ENTRY_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JournalActivity.this, CreateJournalActivity.class);
                startActivityForResult(intent, NEW_ENTRY_ACTIVITY_REQUEST_CODE);

            }
        });

        mAdapter = new JournalAdapter(this);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);
        journalViewModel.getAllJournalEntities().observe(this, new Observer<List<JournalEntity>>() {
            @Override
            public void onChanged(@Nullable List<JournalEntity> journalEntities) {
                mAdapter.setJournalEntityLists(journalEntities);
            }
        });

        Intent openingOperation = getIntent();
        if (openingOperation.hasExtra(ViewJournalDetailsActivity.TAG)) {
            journalViewModel.delete(openingOperation.getIntExtra(ViewJournalDetailsActivity.TAG, 0));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ENTRY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            JournalEntity journalEntity = new JournalEntity();
            journalEntity.setTitle(data.getStringExtra(CreateJournalActivity.TITLE_KEY));
            journalEntity.setDescription(data.getStringExtra(CreateJournalActivity.BODY_KEY));
            if (data.hasExtra(ViewJournalDetailsActivity.TAG)) {
                journalViewModel.update(data.getStringExtra(CreateJournalActivity.TITLE_KEY),
                        data.getStringExtra(CreateJournalActivity.BODY_KEY),
                        data.getIntExtra(ViewJournalDetailsActivity.TAG, 0));
                journalViewModel.update(data.getStringExtra(CreateJournalActivity.TITLE_KEY),
                        data.getStringExtra(CreateJournalActivity.BODY_KEY),
                        data.getIntExtra(ViewJournalDetailsActivity.TAG, 0)
                        );
            }
            journalViewModel.insert(journalEntity);

        } else {
            Toast.makeText(this, "Journal Empty, Not saved", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.journal_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sign_out) {
            MainActivity.signOutNow();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
