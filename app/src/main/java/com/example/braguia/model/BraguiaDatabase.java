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

@Database(entities= {Trail.class, User.class}, version=1, exportSchema = false)
public abstract class BraguiaDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Braguia";

    public abstract UserDAO userDAO();

    public abstract TrailDAO trailDAO();

    private static volatile BraguiaDatabase INSTANCE = null;

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
            populateDbAsync(INSTANCE);
        }
    };

    public static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void populateDbAsync(BraguiaDatabase catDatabase) {
        executorService.execute(() -> {
            UserDAO userDAO = catDatabase.userDAO();
            TrailDAO trailDAO = catDatabase.trailDAO();

            userDAO.deleteAll();
            trailDAO.deleteAll();
        });
    }

//    static class PopulateDbAsyn extends AsyncTask<Void,Void,Void>{
//
//        private UserDAO userDAO;
//        private TrailDAO trailDAO;
//
//        public PopulateDbAsyn(BraguiaDatabase catDatabase){
//            userDAO = catDatabase.userDAO();
//            trailDAO = catDatabase.trailDAO();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids){
//            userDAO.deleteAll();
//            return null;
//        }
//    }
}
