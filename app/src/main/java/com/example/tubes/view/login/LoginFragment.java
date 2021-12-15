package com.example.tubes.view.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.tubes.R;
import com.example.tubes.view.addedit.AddEditFragment;
import com.example.tubes.view.home.HomeFragment;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (!username.isEmpty() || !password.isEmpty()) {
                loginViewModel.login(username, password);
            } else {
                Toast.makeText(getActivity(), "Please fill form to login", Toast.LENGTH_SHORT).show();
            }
        });
        loginViewModel.isLogin.observe(requireActivity(), isLogin -> {
            if (isLogin) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new HomeFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
    }
}
