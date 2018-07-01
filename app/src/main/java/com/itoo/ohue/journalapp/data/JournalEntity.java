package com.itoo.ohue.journalapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "journal_table"  )
public class JournalEntity {

    public static JournalEntity journalEntity;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "journal_title")
    private String title;

    @ColumnInfo(name = "journal_description")
    private String description;

    @ColumnInfo(name = "journal_date")
    private String dateCreated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
