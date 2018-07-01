package com.itoo.ohue.journalapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface JournalDAO {

    // Select all from Task table and order by "complete by" date
    @Query("SELECT * from journal_table ORDER BY journal_date ASC " )
    LiveData<List<JournalEntity>> getAllEntries();
    // Select one task from Task table by id
    @Query("SELECT * FROM journal_table WHERE id=:id")
    LiveData<JournalEntity> getEntryById(int id);

    @Query("DELETE FROM journal_table")
    void deleteAll();

    @Query("DELETE FROM journal_table WHERE id=:id")
    void deleteEntryById(int id);

    @Query("UPDATE journal_table  SET journal_title= :title, journal_description= :description  WHERE id = :id")
    void update(String title, String description, int id );

    @Insert
    void insert(JournalEntity journalEntity);



}
