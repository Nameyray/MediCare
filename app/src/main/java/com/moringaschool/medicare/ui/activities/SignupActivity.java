package com.moringaschool.medicare.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.moringaschool.medicare.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import util.UserApi;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.etSigninEmail)
    EditText email;
    @BindView(R.id.etSigninEmail)
    EditText firstName;
    @BindView(R.id.etLastName)
    EditText lastName;
    @BindView(R.id.etSigninPassword)
    EditText password;
    @BindView(R.id.btnCreateAccount)
    EditText createAccount;
    @BindView(R.id.btnImageGoogle)
    ImageButton googleSignIn;
    @BindView(R.id.btnImageFacebook)
    ImageButton facebookSignIn;
    @BindView(R.id.pbCircle)
    ProgressBar progressBar;

    // Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    // Firestore connection
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // bind current view
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();

         Check for current user before allocating new resources
        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();

            if (currentUser != null) {
                // user is already logged in
            } else {
                // no user yet ....
            }
        };

        String userEmail = email.getText().toString().trim();
        String userFirstName = firstName.getText().toString().trim();
        String userLastName = lastName.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch and record user details to firebase
                if (!TextUtils.isEmpty(email.getText().toString())
                        && !TextUtils.isEmpty(firstName.getText().toString())
                        && !TextUtils.isEmpty(lastName.getText().toString())
                        && !TextUtils.isEmpty(password.getText().toString())) {

                    String userEmail = email.getText().toString().trim();
                    String userFirstName = firstName.getText().toString().trim();
                    String userLastName = lastName.getText().toString().trim();
                    String userPassword = password.getText().toString().trim();

                    createUserEmailAccount(userEmail, userPassword, userFirstName, userLastName);
                } else {
//                    Toast.makeText(CreateAccountActivity.this,"Empty Fields Not Allowed",Toast.LENGTH_LONG)
//                            .show();
                }
            }
        });

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // provide a means to authenticate with google

            }
        });

        facebookSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // provide a means to authenticate with facebook
            }
        });
    }

    // firebase method for account creation
    private void createUserEmailAccount(String userEmail, String userPassword, String userFirstName, String userLastName) {
        // Validates if the function arguents are available
        if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword)
                && !TextUtils.isEmpty(userFirstName) && !TextUtils.isEmpty(userLastName)) {

            progressBar.setVisibility(View.INVISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // take user to Login  Activity
                            currentUser = firebaseAuth.getCurrentUser();
                            assert currentUser != null;
                            String currentUserId = currentUser.getUid();

                            // Create a user map so we can create a user in the user collection
                            Map<String, String> userObj = new HashMap<>();
                            userObj.put("userId", currentUserId);
                            userObj.put("userFirstName", userFirstName);

                            // Save to our firestore database
                            collectionReference.add(userObj)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            documentReference.get()
                                                    .addOnCompleteListener(task1 -> {
                                                        if (Objects.requireNonNull(task1.getResult()).exists()) {
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            String name = task1.getResult()
                                                                    .getString("userFirstName");

                                                            UserApi userApi = UserApi.getInstance();
                                                            userApi.setUserId(currentUserId);
                                                            userApi.setUserFirstName(name);


                                                            Intent intent = new Intent(SignupActivity.this,
                                                                    LoginActivity.class);
                                                            intent.putExtra("userFirstName", name);
                                                            intent.putExtra("userId", currentUserId);
                                                            startActivity(intent);

                                                        } else {
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(e -> {

                                    });
                        } else {
                            // something went wrong
                        }
                    })
                    .addOnFailureListener(e -> {

                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

}