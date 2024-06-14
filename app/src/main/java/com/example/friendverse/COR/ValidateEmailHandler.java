package com.example.friendverse.COR;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.friendverse.DialogLoadingBar.LoadingDialog;
import com.example.friendverse.Login.EmailConfirmActivity;
import com.example.friendverse.MailService.GMailSender;

public class ValidateEmailHandler implements RegistrationHandler{
    private Context context;
    private EditText emailET;
    private LoadingDialog loadingDialog;

    public ValidateEmailHandler(Context context, EditText emailET, LoadingDialog loadingDialog) {
        this.context = context;
        this.emailET = emailET;
        this.loadingDialog = loadingDialog;
    }
    @Override
    public void handle(RegistrationContext regContext, RegistrationChain chain) {
        loadingDialog.showDialog();

        String emailSender = "friendverse123@gmail.com";
        String passEmail = "jqnzillisugvmied";
        String emailReceive = emailET.getText().toString();

        int rand_num = (int) Math.floor(Math.random() * (1000000 - 100000 + 1) + 100000);

        if (TextUtils.isEmpty(emailReceive)) {
            emailET.setError("Email can't be empty");
            emailET.requestFocus();
            loadingDialog.hideDialog();
        } else {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                GMailSender sender = new GMailSender(emailSender, passEmail);
                sender.sendMail("OTP to verify email", "This is OTP code for verification your email: " +
                        rand_num, emailSender, emailReceive);
                Toast.makeText(context, "OTP sent!", Toast.LENGTH_SHORT).show();

                regContext.setEmail(emailReceive);
                regContext.setOtp(rand_num);
                regContext.setEmailVerified(true);

                loadingDialog.hideDialog();

                Intent intent = new Intent(context, EmailConfirmActivity.class);
                intent.putExtra("context", regContext);
                context.startActivity(intent);

                chain.nextStep();
            } catch (Exception e) {
                Log.e("SendMail: ", e.getMessage(), e);
                loadingDialog.hideDialog();
            }
        }
    }
}
