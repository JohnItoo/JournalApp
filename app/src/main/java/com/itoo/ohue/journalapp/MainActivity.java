package com.itoo.ohue.journalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.itoo.ohue.journalapp.view.JournalActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.gg_sign_in_button)
    SignInButton ggSignInButton;

    GoogleSignInOptions gso;
    static GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        googleSetup();
        ggSignInButton.setOnClickListener(this);


    }

    @OnClick(R.id.app_sign_in_btn)
    public void enter() {
        Intent intent = new Intent(MainActivity.this, JournalActivity.class);
        startActivity(intent);
    }

    private void googleSetup() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //account null if no previously logged in user
        if (account != null)
            progress(account);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gg_sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                progress(account);
            } else {
                Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show();
            }
            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
            Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show();
        }
    }

    private void progress(GoogleSignInAccount account) {
        saveName(account.getDisplayName(), account.getFamilyName());
        Intent intent = new Intent(MainActivity.this, JournalActivity.class);
        startActivity(intent);
    }

    public static void signOutNow() {
        signOut();
    }

    private static void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                System.exit(0);
            }
        });

    }

    public void saveName(String firstName, String lastName) {
        SharedPreferences.Editor editor = getSharedPreferences("myPrefs", Context.MODE_PRIVATE).edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.apply();
    }
}
