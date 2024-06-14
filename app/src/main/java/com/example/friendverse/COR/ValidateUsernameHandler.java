package com.example.friendverse.COR;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.friendverse.DialogLoadingBar.LoadingDialog;
import com.example.friendverse.Login.LoginActivity;
import com.example.friendverse.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ValidateUsernameHandler implements RegistrationHandler{
    private Context context;
    private LoadingDialog loadingDialog;
    private EditText etUsername;
    private String uid;

    public ValidateUsernameHandler(Context context, LoadingDialog loadingDialog, EditText etUsername, String uid) {
        this.context = context;
        this.loadingDialog = loadingDialog;
        this.etUsername = etUsername;
        this.uid = uid;
    }

    @Override
    public void handle(RegistrationContext regContext, RegistrationChain chain) {
        String username = etUsername.getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadingDialog.showDialog();
                boolean usernameExists = false;
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);
                    if (user != null && !uid.equals(user.getId()) && username.equals(user.getUsername())) {
                        usernameExists = true;
                        break;
                    }
                }
                if (usernameExists) {
                    etUsername.setError("Username has been used");
                    etUsername.requestFocus();
                    loadingDialog.hideDialog();
                } else {
                    reference.child(uid).child(User.USERNAMEKEY).setValue(username);
                    Toast.makeText(context, "Username changed successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    loadingDialog.hideDialog();
                    chain.nextStep();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.hideDialog();
            }
        });
    }
}
