package com.example.friendverse.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendverse.COR.RegistrationChain;
import com.example.friendverse.COR.RegistrationContext;
import com.example.friendverse.COR.VerifyOtpHandler;
import com.example.friendverse.DialogLoadingBar.LoadingDialog;
import com.example.friendverse.MailService.GMailSender;
import com.example.friendverse.R;

public class EmailConfirmActivity extends AppCompatActivity {
    private Button buttonNext;
    private TextView tvLogin;
    private TextView tvRequest;
    private EditText etCode;
    private LoadingDialog loadingDialog = new LoadingDialog(this);
    String email;
    int otp;
    private RegistrationContext regContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_confirm);

        Intent intent1 = getIntent();
        String email = intent1.getExtras().getString("Email");
        otp = intent1.getExtras().getInt("OTPCode");
        regContext = intent1.getParcelableExtra("context");
        buttonNext = findViewById(R.id.buttonNextEmailOTP);
        tvLogin = findViewById(R.id.tvLoginEmail);
        tvRequest = findViewById(R.id.textViewRequestEmail);
        etCode = findViewById(R.id.etverifyEmail);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegistrationChain chain = new RegistrationChain(regContext);
                chain.addHandler(new VerifyOtpHandler(EmailConfirmActivity.this, etCode, loadingDialog));

                chain.start();
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.showDialog();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                loadingDialog.hideDialog();
            }
        });
        tvRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.showDialog();
                String emailSender = "friendverse123@gmail.com";
                String passEmail = "jqnzillisugvmied";
                String emailReceive = regContext.getEmail();

                otp = (int)Math.floor(Math.random() * (1000000 - 100000 + 1) + 100000);
                regContext.setOtp(otp);


                try{
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                    StrictMode.setThreadPolicy(policy);


                    GMailSender sender = new GMailSender(emailSender, passEmail);
                    sender.sendMail("OTP to verify email", "This is OTP code for verification your email: " +
                            Integer.toString(otp), emailSender, emailReceive);
                    Toast.makeText(EmailConfirmActivity.this,"OTP sent!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("SendMail", e.getMessage(), e);
                }
                loadingDialog.hideDialog();
            }
        });
    }
}