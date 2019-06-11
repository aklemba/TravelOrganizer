package com.example.ekipaapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.ekipaapp.dao.EventDao;
import com.example.ekipaapp.entity.Event;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventDatabase extends RoomDatabase {

    private static volatile EventDatabase INSTANCE;

    // DAOs
    public abstract EventDao eventDao();

    public static EventDatabase getDatabase(Application application) {
        if (INSTANCE == null) {
            synchronized (EventDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(application, EventDatabase.class, "event_database")
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback callback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDBAsync(INSTANCE).execute();
                }
            };

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }


    private static class PopulateDBAsync extends AsyncTask<Void, Void, Void> {
        private final EventDao dao;

        public PopulateDBAsync(EventDatabase db) {
            this.dao = db.eventDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.insertEvent(new Event("myevent"));
            return null;
        }
    }
}
