package com.example.braguia.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.braguia.model.User;
import com.example.braguia.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepo;
    private final LiveData<List<User>> users;

    public UserViewModel(Application application) {
        super(application);
        userRepo = new UserRepository(application);
        users = userRepo.getAllUsers();
    }

    LiveData<List<User>> getAllUsers(){
        return users;
    }

    public void insert(User user){
        userRepo.insert(user);
    }
}
