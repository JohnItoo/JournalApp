package com.itoo.ohue.journalapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {JournalEntity.class}, version = 1)
public abstract class JournalDatabase extends RoomDatabase {
    public abstract JournalDAO journalDAO();

    public static JournalDatabase journalDatabaseInstance;

    /**
     * Singleton Instance as
     * Creatimg multiple Instances of the Database is too expensive
     *
     * @param context
     * @return
     */
    public static synchronized JournalDatabase getDatabaseInstance(Context context) {
        if (journalDatabaseInstance == null) {
            journalDatabaseInstance = create(context);
        }
        return journalDatabaseInstance;
    }

    // Create the database
    static JournalDatabase create(Context context) {
        RoomDatabase.Builder<JournalDatabase> builder = Room.databaseBuilder(context.getApplicationContext(),
                JournalDatabase.class,
                "table_name");
        return builder.build();
    }
}
