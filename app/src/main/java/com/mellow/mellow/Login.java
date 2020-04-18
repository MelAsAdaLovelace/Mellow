package com.mellow.mellow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import org.w3c.dom.Text;

import java.net.Inet4Address;

public class Login extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    Button registerBtn, signInBtn, forgetBtn;
    SignInButton googleBtn;
    ImageView hareImg;
    TextView mellowText, signInText;
    TextInputLayout email, password;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference mReference;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        signInBtn = findViewById(R.id.loginLoginBtn);
        registerBtn = findViewById(R.id.loginRegisterBtn);
        forgetBtn = findViewById(R.id.loginForgetBtn);
        googleBtn = (SignInButton) findViewById(R.id.loginGoogleBtn);

        hareImg = findViewById(R.id.loginHare);
        mellowText = findViewById(R.id.loginTextWelcome);
        signInText = findViewById(R.id.loginSignText);
        email = findViewById(R.id.loginLayoutEmail);
        password = findViewById(R.id.loginLayoutPassword);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG,user.getEmail() );
                }else{
                    Log.d(TAG, "Signed out");
                }
            }
        };

        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(email.getEditText().getText().toString().length() > 0){
                    String mail = email.getEditText().getText().toString();
                    mAuth.sendPasswordResetEmail(mail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                makeToastText(v, "Email sent");
                            }
                        }
                    });
                }else{
                    email.setError("Please provide your e-mail.");
                }
            }
        });


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String mail = "m";
                String pass = "m";
                if(email.getEditText().getText().toString().length() > 0){
                    mail =  email.getEditText().getText().toString();
                }
                if(password.getEditText().getText().toString().length()> 0){
                    pass = password.getEditText().getText().toString();
                }


                mAuth.signInWithEmailAndPassword(mail, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent profileIntent = new Intent(Login.this, UserProfile.class);
                                    Pair[] pairs = new Pair[5];
                                    pairs[0] = new Pair<View, String>(hareImg, "logo_image");
                                    pairs[1] = new Pair<View, String>(mellowText, "logo_text");
                                    pairs[2] = new Pair<View, String>(signInText, "logo_descr");
                                    pairs[3] = new Pair<View, String>(password, "trans_password");
                                    pairs[4] = new Pair<View, String>(signInBtn, "trans_go_btn");
                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                                    startActivity(profileIntent, options.toBundle());
                                } else {
                                    // If sign in fails, display a message to the user.
                                    email.setError("Email and password do not match");
                                }
                            }
                        });

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(hareImg, "logo_image");
                pairs[1] = new Pair<View, String>(mellowText, "logo_text");
                pairs[2] = new Pair<View, String>(signInText, "logo_descr");
                pairs[3] = new Pair<View, String>(email, "trans_email");
                pairs[4] = new Pair<View, String>(password, "trans_password");
                pairs[5] = new Pair<View, String>(signInBtn, "trans_go_btn");
                pairs[6] = new Pair<View, String>(registerBtn, "trans_reglog_btn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });


    }

    private void signIn() {
        Intent googleIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(googleIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount acc = task.getResult(ApiException.class);
            FirebaseGoogleAuth(acc);
        }catch (ApiException e){
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(Login.this, UserProfile.class));

                }else{

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    private void makeToastText(View view, String text){
        Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
