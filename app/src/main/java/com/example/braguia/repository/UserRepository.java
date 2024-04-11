package com.example.braguia.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.braguia.model.BraguiaDatabase;
import com.example.braguia.model.User;
import com.example.braguia.model.UserDAO;

import java.util.List;

public class UserRepository {

    private UserDAO userDAO;
    private LiveData<List<User>> users;

    public UserRepository(Application application){

        BraguiaDatabase db = BraguiaDatabase.getInstance(application);
        userDAO = db.userDAO();
        users = userDAO.getAll();
    }
    public LiveData<List<User>> getAllUsers(){
        return users;
    }

    public void insert(User user) {
        BraguiaDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    public class InsertAsyncTask extends AsyncTask<User,Void,Void> {

        private UserDAO userDAO;
        @Override
        protected Void doInBackground(User... users){
            userDAO.insert(users[0]);
            return null;
        }
    }

}
