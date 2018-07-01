package com.itoo.ohue.journalapp.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {

    private JournalRepository mJournalRepository;
    private LiveData<List<JournalEntity>> allJournalEntities;

    public JournalViewModel(@NonNull Application application) {
        super(application);
        mJournalRepository = new JournalRepository(application);
        allJournalEntities = mJournalRepository.getAllJournalEntries();
    }

    public LiveData<List<JournalEntity>> getAllJournalEntities() {
        return allJournalEntities;
    }

    public void insert(JournalEntity journalEntity) {
         mJournalRepository.insert(journalEntity);
    }

    public void delete(int id) {
        mJournalRepository.delete(id);
    }

    public void update(String title, String body, int id) {
        mJournalRepository.update(title, body, id);
    }
}
