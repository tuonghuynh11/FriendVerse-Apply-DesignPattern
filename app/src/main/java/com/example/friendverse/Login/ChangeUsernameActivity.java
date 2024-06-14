package com.example.friendverse.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.friendverse.COR.ValidateUsernameHandler;
import com.example.friendverse.COR.RegistrationChain;
import com.example.friendverse.COR.RegistrationContext;
import com.example.friendverse.DialogLoadingBar.LoadingDialog;
import com.example.friendverse.R;

public class ChangeUsernameActivity extends AppCompatActivity {
    private TextView tvGoback;
    private String getUID;
    private Button exploreBtt;
    private EditText etUsername;
    private LoadingDialog loadingDialog = new LoadingDialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        Intent intent1 = getIntent();
        getUID = intent1.getExtras().getString("UID1");


        tvGoback = findViewById(R.id.textViewGoback);
        exploreBtt = findViewById(R.id.buttonExplore);
        etUsername = findViewById(R.id.editTextUsername);

        tvGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.showDialog();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                loadingDialog.hideDialog();
                finishAffinity();
            }
        });
        exploreBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrationContext regContext = new RegistrationContext();
                regContext.setUid(getUID);

                RegistrationChain chain = new RegistrationChain(regContext);
                chain.addHandler(new ValidateUsernameHandler(ChangeUsernameActivity.this, loadingDialog, etUsername, getUID));

                chain.start();

            }
        });
    }
}