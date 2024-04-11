package com.example.braguia.model;

import android.content.Context;
import android.os.AsyncTask;
import android.telecom.Call;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities= {User.class}, version=1, exportSchema = false)

public abstract class BraguiaDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Braguia";
    public abstract UserDAO userDAO();
    private static volatile BraguiaDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static BraguiaDatabase getInstance(Context context){

        if (INSTANCE == null){
            synchronized (BraguiaDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),BraguiaDatabase.class,DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsyn(INSTANCE);
        }
    };

    static class PopulateDbAsyn extends AsyncTask<Void,Void,Void>{

        private UserDAO userDAO;

        public PopulateDbAsyn(BraguiaDatabase catDatabase){
            userDAO = catDatabase.userDAO();
        }

        @Override
        protected Void doInBackground(Void... voids){
            userDAO.deleteAll();
            return null;
        }
    }


}
