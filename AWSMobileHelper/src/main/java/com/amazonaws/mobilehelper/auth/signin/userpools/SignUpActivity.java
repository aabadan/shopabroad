//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.16
//
package com.amazonaws.mobilehelper.auth.signin.userpools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amazonaws.mobilehelper.R;
import com.amazonaws.mobilehelper.auth.signin.CognitoUserPoolsSignInProvider;
import com.amazonaws.mobilehelper.util.ViewHelper;

import java.util.regex.Pattern;

/**
 * Activity to prompt for account sign up information.
 */
public class SignUpActivity extends Activity {
    /** Log tag. */
    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    /**
     * Retrieve input and return to caller.
     * @param view the Android View
     */
    public void signUp(final View view) {
        final String username = ViewHelper.getStringValue(this, R.id.signup_username);
        password = (EditText) findViewById(R.id.signup_password);
        final String givenName = ViewHelper.getStringValue(this, R.id.signup_given_name);
        email = (EditText) findViewById(R.id.signup_email);
        final String phone = ViewHelper.getStringValue(this, R.id.signup_phone);

        Log.d(LOG_TAG, "username = " + username);
        Log.d(LOG_TAG, "given_name = " + givenName);
        Log.d(LOG_TAG, "email = " + email);
        Log.d(LOG_TAG, "phone = " + phone);

        if(!validate()){
            return;
        }

        final Intent intent = new Intent();
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.USERNAME, username);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.PASSWORD, password.getText().toString());
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.GIVEN_NAME, givenName);
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.EMAIL_ADDRESS, email.getText().toString());
        intent.putExtra(CognitoUserPoolsSignInProvider.AttributeKeys.PHONE_NUMBER, phone);

        setResult(RESULT_OK, intent);

        finish();
    }

    public boolean validate() {
        boolean valid = true;

        if (email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        //Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        if (password.length() < 8) {
            password.requestFocus();
            password.setError("Password lenght must have alleast 8 character !!");
            valid = false;
        }
        /*if (!specialCharPatten.matcher(password.getText().toString()).find()) {
            password.requestFocus();
            password.setError("Password must have at least one special character !!");
            valid = false;
        }*/
        if (!UpperCasePatten.matcher(password.getText().toString()).find()) {
            password.requestFocus();
            password.setError("Password must have at least one uppercase character !!");
            valid = false;
        }
        if (!lowerCasePatten.matcher(password.getText().toString()).find()) {
            password.requestFocus();
            password.setError("Password must have at least one lowercase character !!");
            valid = false;
        }
        if (!digitCasePatten.matcher(password.getText().toString()).find()) {
            password.requestFocus();
            password.setError("Password must have at least one digit character !!");
            valid = false;
        }

        return valid;
    }
}