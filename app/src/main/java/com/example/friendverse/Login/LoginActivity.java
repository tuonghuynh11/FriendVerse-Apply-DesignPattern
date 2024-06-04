package com.example.friendverse.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendverse.DialogLoadingBar.LoadingDialog;
import com.example.friendverse.Facade.AuthenticationFacade;
import com.example.friendverse.MainActivity;
import com.example.friendverse.Model.User;
import com.example.friendverse.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    private TextView tvforgetPass;
    private Button loginButton;
    private ImageButton loginGoogleButton;
    //private ImageButton loginFacebookButton;
    private ImageButton phoneButton;
    private EditText usernameText;
    private EditText passwordText;
    private LoadingDialog loadingDialog = new LoadingDialog(this);
    private AuthenticationFacade authFacade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvforgetPass = findViewById(R.id.forgetPasstv);
        loginButton = findViewById(R.id.button);
        usernameText =findViewById(R.id.editText);
        passwordText = findViewById(R.id.editText2);
        phoneButton = findViewById(R.id.phoneLoginButton);
        loginGoogleButton = findViewById(R.id.googleButton);

        authFacade = new AuthenticationFacade(this);

        mAuth = FirebaseAuth.getInstance();


        tvforgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.showDialog();
                openForgetPassword();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserWithEmail();

            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.showDialog();
                startActivity(new Intent(LoginActivity.this, SignupPhoneActivity.class));
                loadingDialog.hideDialog();
            }
        });

        loginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithGoogle();
            }
        });
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        // Check condition
        if (firebaseUser != null) {
            // When user already sign in redirect to profile activity
            startActivity(new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }

    public void openForgetPassword(){
        Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
        loadingDialog.hideDialog();
        startActivity(intent);
    }



    //------------------------Firebase-------------------------------------------------------------
    private FirebaseAuth mAuth;

    private void loginUserWithEmail() {
        String email = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        authFacade.loginWithEmail(email, password);

    }

    private void loginWithGoogle(){
        Intent intent = authFacade.getGoogleSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            authFacade.loginWithGoogle(data);

        }
    }

}
