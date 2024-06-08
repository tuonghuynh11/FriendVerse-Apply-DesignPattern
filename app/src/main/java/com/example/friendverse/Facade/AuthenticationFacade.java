package com.example.friendverse.Facade;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.friendverse.DialogLoadingBar.LoadingDialog;
import com.example.friendverse.MainActivity;
import com.example.friendverse.Model.User;
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
import java.util.Timer;
import java.util.TimerTask;

public class AuthenticationFacade {
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private GoogleSignInClient googleSignInClient;
    private List<String> banUsersId;
    private LoadingDialog loadingDialog;
    private Activity activity;

    public AuthenticationFacade(Activity activity) {
        this.activity = activity;
        this.mAuth = FirebaseAuth.getInstance();
        this.loadingDialog = new LoadingDialog(activity);
        this.banUsersId = new ArrayList<>();
        initGoogleSignIn();
        initBanList();
    }
    private void initGoogleSignIn() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("636293367764-fra1gmq1uu62kh0pdt8phqqvv2srcotm.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(activity, signInOptions);
    }
    private void initBanList() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Ban").child("Users");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                banUsersId.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    banUsersId.add(snapshot1.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void loginWithEmail(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(activity, "Email can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(activity, "Password can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.showDialog();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                handleLoginResult(task);
            }
        });
    }
    public void loginWithGoogle(Intent data) {
        Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
        if (signInAccountTask.isSuccessful()) {
            try {
                GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                    mAuth.signInWithCredential(authCredential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            handleLoginResult(task);
                        }
                    });
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleLoginResult(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if (firebaseUser != null && isUserBanned(firebaseUser.getUid())) {
                Toast.makeText(activity, "This account is banned", Toast.LENGTH_SHORT).show();
                logout();
                loadingDialog.hideDialog();
                return;
            }
            checkUserExistence(firebaseUser);
        } else {
            handleLoginError(task.getException());
            loadingDialog.hideDialog();
        }
    }

    private boolean isUserBanned(String uid) {
        return banUsersId.contains(uid);
    }

    private void handleLoginError(Exception exception) {
        try {
            if (exception != null) throw exception;
        } catch (FirebaseAuthInvalidUserException invalidEmail) {
            Toast.makeText(activity, "Invalid email", Toast.LENGTH_SHORT).show();
        } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
            Toast.makeText(activity, "Wrong password", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(activity, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserExistence(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    updateUserActivityStatus(userId, 1);
                    navigateToMainActivity();
                } else {
                    createUserProfile(firebaseUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                handleDatabaseError(error);
            }
        });
    }

    private void createUserProfile(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("id", userId);
        userMap.put("username", userId);
        userMap.put("fullname", firebaseUser.getDisplayName());
        userMap.put("bio", "");
        userMap.put("imageurl", "default");
        userMap.put("email", firebaseUser.getEmail());
        userMap.put("website", "");
        userMap.put("phonenumber", "");
        userMap.put(User.ACTIVITYKEY, "1");

        reference.setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                initTokenCall();
                notifyFirstTimeSignIn();
            } else {
                handleDatabaseError(DatabaseError.fromException(task.getException()));
            }
        });
    }
    private void notifyFirstTimeSignIn() {
        Toast.makeText(activity, "This is your first time signing in. Please restart the app!", Toast.LENGTH_SHORT).show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                activity.finishAffinity();
                System.exit(0);
            }
        }, 4000);
    }

    private void updateUserActivityStatus(String uid, int status) {
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reference.child(User.ACTIVITYKEY).setValue(status);
    }

    private void navigateToMainActivity() {
//        loadingDialog.hideDialog();
        Toast.makeText(activity, "Login success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finishAffinity();
    }

    public void logout() {
        mAuth.signOut();
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Handle logout if necessary
            }
        });
    }

    private void initTokenCall() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String deviceToken = task.getResult();
                reference.child(User.TOKENKEY).setValue(deviceToken);
            }
        });
    }

    public Intent getGoogleSignInIntent() {
        return googleSignInClient.getSignInIntent();
    }
    private void handleDatabaseError(DatabaseError exception) {
        if (exception != null) {
            Toast.makeText(activity, "Database error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
