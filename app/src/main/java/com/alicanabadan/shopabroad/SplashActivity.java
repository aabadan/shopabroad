package com.alicanabadan.shopabroad;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobilehelper.auth.DefaultSignInResultHandler;
import com.amazonaws.mobilehelper.auth.IdentityHandler;
import com.amazonaws.mobilehelper.auth.IdentityManager;
import com.amazonaws.mobilehelper.auth.IdentityProvider;
import com.amazonaws.mobilehelper.auth.StartupAuthErrorDetails;
import com.amazonaws.mobilehelper.auth.StartupAuthResult;
import com.amazonaws.mobilehelper.auth.StartupAuthResultHandler;

public class SplashActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SplashActivity";
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
/*
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);*/

        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());
        final IdentityManager identityManager =
                AWSMobileClient.defaultMobileClient().getIdentityManager();

        identityManager.doStartupAuth(this,
                new StartupAuthResultHandler() {
                    @Override
                    public void onComplete(final StartupAuthResult authResults) {
                        if (authResults.isUserSignedIn()) {
                            final IdentityProvider provider = identityManager.getCurrentIdentityProvider();
                            // If we were signed in previously with a provider indicate that to the user with a toast.
                            Toast.makeText(SplashActivity.this, String.format("Signed in with %s",
                                    provider.getDisplayName()), Toast.LENGTH_LONG).show();
                        } else {
                            // Either the user has never signed in with a provider before or refresh failed with a previously
                            // signed in provider.

                            // Optionally, you may want to check if refresh failed for the previously signed in provider.
                            final StartupAuthErrorDetails errors = authResults.getErrorDetails();
                            if (errors.didErrorOccurRefreshingProvider()) {
                                Log.w(LOG_TAG, String.format(
                                        "Credentials for Previously signed-in provider %s could not be refreshed.",
                                        errors.getErrorProvider().getDisplayName()), errors.getProviderErrorException());
                            }

                            doMandatorySignIn(identityManager);
                            return;
                        }

                        // Go to your main activity and finish your splash activity here
                        goMain(SplashActivity.this);
                    }
                }, SPLASH_TIME_OUT);

        AWSMobileClient.defaultMobileClient().getIdentityManager().getUserID(new IdentityHandler() {

            @Override
            public void onIdentityId(String identityId) {

            }

            @Override
            public void handleError(Exception exception) {

                // We failed to retrieve the user's identity. Set unknown user identifier
                // in text view. Perhaps there was no network access available.

                // ... add error handling logic here ...
            }
        });
    }

    private void doMandatorySignIn(final IdentityManager identityManager) {
        identityManager.signInOrSignUp(SplashActivity.this, new DefaultSignInResultHandler() {
            @Override
            public void onSuccess(Activity callingActivity, IdentityProvider provider) {

            }

            @Override
            public boolean onCancel(Activity callingActivity) {
                return false;
            }
            // ... implement interface methods ...
        });
        SplashActivity.this.finish();
    }

    /** Go to the main activity. */
    private void goMain(final Activity callingActivity) {
        callingActivity.startActivity(new Intent(callingActivity, SelectionBoardActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        callingActivity.finish();
    }
}
