package com.huachuang.palmtouchfinancial.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "18511838501:111111"
    };

    public static final String TAG = LoginActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private UserLoginTask authTask = null;

    @ViewInject(R.id.login_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.login_phone_number)
    private EditText phoneNumberView;

    @ViewInject(R.id.login_password)
    private EditText passwordView;

    @ViewInject(R.id.login_progress)
    private View progressView;

    @ViewInject(R.id.login_form)
    private View loginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
    }

    @Event(value = R.id.login_password,
            type = TextView.OnEditorActionListener.class)
    private boolean onLoginPasswordEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_NULL || actionId == EditorInfo.IME_ACTION_SEARCH) {
            attemptLogin();
            return true;
        }
        return false;
    }

    @Event(value = R.id.sign_in_button)
    private void onSignInButtonClicked(View view) {
        //attemptLogin();
        MainActivity.actionStart(this);
        /*IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();*/
    }

    @Event(value = R.id.register_link)
    private void onRegisterLinkClicked(View view) {
        RegisterActivity.actionStart(this);
    }

    private void attemptLogin() {
        if (authTask != null) {
            return;
        }

        // Reset errors.
        phoneNumberView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String phoneNumber = phoneNumberView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberView.setError(getString(R.string.error_field_required));
            focusView = phoneNumberView;
            cancel = true;
        } else if (!isPhoneNumberValid(phoneNumber)) {
            phoneNumberView.setError(getString(R.string.error_invalid_phone_number));
            focusView = phoneNumberView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            authTask = new UserLoginTask(phoneNumber, password);
            authTask.execute((Void) null);
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String phoneNumber;
        private final String password;

        UserLoginTask(String phoneNumber, String password) {
            this.phoneNumber = phoneNumber;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(phoneNumber)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(password);
                }
            }

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            showProgress(false);

            if (success) {
                MainActivity.actionStart(LoginActivity.this);
            } else {
                passwordView.setError(getString(R.string.error_incorrect_password));
                passwordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            showProgress(false);
        }
    }
}

