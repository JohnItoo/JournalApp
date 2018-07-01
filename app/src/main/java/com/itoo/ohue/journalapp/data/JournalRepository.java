package com.itoo.ohue.journalapp.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class JournalRepository {

    private JournalDAO journalDAO;
    private LiveData<List<JournalEntity>> allJournalEntries;

    JournalRepository(Application application) {
        JournalDatabase database = JournalDatabase.getDatabaseInstance(application);
        journalDAO = database.journalDAO();
        allJournalEntries = journalDAO.getAllEntries();
    }

    LiveData<List<JournalEntity>> getAllJournalEntries() {
        return allJournalEntries;
    }

    public void insert (JournalEntity journalEntity) {
        new insertAsyncTask(journalDAO).execute(journalEntity);
    }

    public  void delete(int id) {
        new deleteAsyncTask(journalDAO, id).execute();
    }

    public void update(String title, String body, int id ) {
        new updateAsyncTask(journalDAO, title, body, id);
    }
    private static class insertAsyncTask extends AsyncTask<JournalEntity, Void, Void> {

        private JournalDAO mAsyncTaskJournalDAO;

        insertAsyncTask(JournalDAO journalDAO) {
            mAsyncTaskJournalDAO = journalDAO;
        }
        @Override
        protected Void doInBackground(JournalEntity... journalEntities) {
            mAsyncTaskJournalDAO.insert(journalEntities[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void , Void> {
        private JournalDAO deleteJournalDAO;
        private int deleteId;
        deleteAsyncTask(JournalDAO journalDAO, int id) {
            deleteJournalDAO = journalDAO;
            deleteId = id;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            deleteJournalDAO.deleteEntryById(deleteId);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Void, Void, Void> {
        private JournalDAO updateJournalDAO;
        private String title;
        private String body;
        private int id;

        updateAsyncTask(JournalDAO journalDAO, String title, String body, int id) {
            updateJournalDAO = journalDAO;
            this.title = title;
            this.body = body;
            this.id = id;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            updateJournalDAO.update(title, body, id);
            return null;
        }
    }
}
