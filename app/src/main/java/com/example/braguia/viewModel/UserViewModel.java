package com.example.braguia.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.braguia.model.Objects.User;
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

    public LiveData<List<User>> getAllUsers(){
        return users;
    }

    public void login(String username, String password){

        userRepo.loginAPI(username,password);
    }

    public void logout(){

        userRepo.logoutAPI();
    }

    public LiveData<Boolean> getLoginStatus(){
        return userRepo.getLoginStatus();
    }
    public LiveData<User> getUser(){
        return userRepo.getUser();
    }

    public LiveData<Boolean> getLogoutStatus(){return userRepo.getLogoutStatus();}

    public String getCookieExpire(){return userRepo.getCookieExpire();}



    public void insert(User user){
        userRepo.insert(user);
    }
}
