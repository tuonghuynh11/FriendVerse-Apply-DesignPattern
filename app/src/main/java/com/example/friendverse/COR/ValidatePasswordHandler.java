package com.example.friendverse.COR;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.friendverse.DialogLoadingBar.LoadingDialog;
import com.example.friendverse.Login.SignupActivity;
import com.example.friendverse.Login.SignupFinishActivity;
import com.example.friendverse.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Objects;

public class ValidatePasswordHandler implements RegistrationHandler{
    private Context context;
    private EditText etName, etPassword, etRePassword;
    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;
    private DatabaseReference reference;

    public ValidatePasswordHandler(Context context, EditText etName, EditText etPassword, EditText etRePassword,
                                   FirebaseAuth mAuth, LoadingDialog loadingDialog) {
        this.context = context;
        this.etName = etName;
        this.etPassword = etPassword;
        this.etRePassword = etRePassword;
        this.mAuth = mAuth;
        this.loadingDialog = loadingDialog;
    }

    @Override
    public void handle(RegistrationContext regContext, RegistrationChain chain) {
        loadingDialog.showDialog();

        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        String rePassword = etRePassword.getText().toString();
        String email = regContext.getEmail();

        if (password.isEmpty()) {
            etPassword.setError("Password can't be empty");
            etPassword.requestFocus();
            loadingDialog.hideDialog();
            return;
        }
        if (rePassword.isEmpty()) {
            etRePassword.setError("Re-Password can't be empty");
            etRePassword.requestFocus();
            loadingDialog.hideDialog();
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(context, "Password must be at least 8 characters!", Toast.LENGTH_SHORT).show();
            loadingDialog.hideDialog();
            return;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(context, "Password and Re-Password must be the same!", Toast.LENGTH_SHORT).show();
            loadingDialog.hideDialog();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userid = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", userid);
                    hashMap.put("fullname", name);
                    hashMap.put("bio", "");
                    hashMap.put("imageurl", "default");
                    hashMap.put("email", email);
                    hashMap.put("website", "");
                    hashMap.put("phonenumber", "");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            initTokenCall();
                            regContext.setUserRegistered(true);
                            Intent intent = new Intent(context, SignupFinishActivity.class);
                            intent.putExtra("UID", userid);
                            loadingDialog.hideDialog();
                            context.startActivity(intent);
                            chain.nextStep();
                        }
                    });

                } else {
                    handleError(task);
                }
            }
        });
        loadingDialog.hideDialog();

    }

    private void handleError(Task<AuthResult> task) {
        loadingDialog.showDialog();

        try {
            throw Objects.requireNonNull(task.getException());
        } catch (FirebaseAuthWeakPasswordException weakPassword) {
            Toast.makeText(context, "Weak password", Toast.LENGTH_SHORT).show();
        } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
            Toast.makeText(context, "Malformed email", Toast.LENGTH_SHORT).show();
        } catch (FirebaseAuthUserCollisionException existEmail) {
            Toast.makeText(context, "Email already exists", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, SignupActivity.class));
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            loadingDialog.hideDialog();
        }
    }
    private void initTokenCall() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String deviceToken = task.getResult();
                    reference.child(User.TOKENKEY).setValue(deviceToken);
                }
            }
        });
    }
}
