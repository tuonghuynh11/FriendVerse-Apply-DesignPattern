package com.example.friendverse.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendverse.Adapter.ViewPagerAdapter;
import com.example.friendverse.COR.CheckEmailHandler;
import com.example.friendverse.COR.RegistrationChain;
import com.example.friendverse.COR.RegistrationContext;
import com.example.friendverse.DialogLoadingBar.LoadingDialog;
import com.example.friendverse.Login.LoginActivity;
import com.example.friendverse.MailService.GMailSender;
import com.example.friendverse.R;
import com.google.android.material.tabs.TabLayout;

public class SignupActivity extends AppCompatActivity {
    private ImageView imgView;
    private TextView tvLogin;
    private EditText emailET;
    private Button bttNext;
    private LoadingDialog loadingDialog = new LoadingDialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        imgView = findViewById(R.id.imageViewSignup);
        tvLogin = findViewById(R.id.tvLoginSignup);
        emailET = findViewById(R.id.emailEditText2);
        bttNext = findViewById(R.id.buttonNextEmail2);

        imgView.setImageResource(R.drawable.user_image);

        bttNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrationContext context = new RegistrationContext();
                RegistrationChain chain = new RegistrationChain(context);
                chain.addHandler(new CheckEmailHandler(SignupActivity.this, emailET, loadingDialog));

                chain.start();

            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}