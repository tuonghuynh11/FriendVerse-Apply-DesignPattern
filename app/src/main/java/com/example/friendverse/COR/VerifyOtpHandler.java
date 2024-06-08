package com.example.friendverse.COR;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.friendverse.DialogLoadingBar.LoadingDialog;
import com.example.friendverse.Login.SignupInfoActivity;

public class VerifyOtpHandler implements RegistrationHandler{
    private Context context;
    private EditText otpET;
    private LoadingDialog loadingDialog;

    public VerifyOtpHandler(Context context, EditText otpET, LoadingDialog loadingDialog) {
        this.context = context;
        this.otpET = otpET;
        this.loadingDialog = loadingDialog;
    }
    @Override
    public void handle(RegistrationContext regContext, RegistrationChain chain) {
        String enteredOtp = otpET.getText().toString();
        loadingDialog.showDialog();


        if (enteredOtp.equals(String.valueOf(regContext.getOtp()))) {
            regContext.setOtpVerified(true);
            Intent intent = new Intent(context, SignupInfoActivity.class);
            intent.putExtra("context", regContext);
            loadingDialog.hideDialog();
            context.startActivity(intent);
            chain.nextStep();
        } else {
            Toast.makeText(context, "OTP is not correct", Toast.LENGTH_SHORT).show();
            loadingDialog.hideDialog();
        }
    }
}
