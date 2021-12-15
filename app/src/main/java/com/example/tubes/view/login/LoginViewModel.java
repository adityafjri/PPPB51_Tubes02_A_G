package com.example.tubes.view.login;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.example.tubes.service.APIService;
import com.example.tubes.service.model.LoginRequest;

public class LoginViewModel extends AndroidViewModel {
    private APIService apiService;
    private Context context;
    public MutableLiveData<Boolean> isLogin;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
        this.isLogin = new MutableLiveData<>(false);
        apiService = new APIService(application);
    }

    public void succeedLogin(String token) {
        isLogin.setValue(true);
    }

    public void failedLogin(String error) {
        Toast.makeText(context, "Login Gagal", Toast.LENGTH_SHORT).show();
    }

    public void login(String username, String password) {
        apiService.postLoginTask(new LoginRequest(username, password), this);
    }
}

