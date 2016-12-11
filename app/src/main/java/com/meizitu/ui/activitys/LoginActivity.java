package com.meizitu.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.meizitu.R;
import com.meizitu.internal.di.components.DaggerLoginComponent;
import com.meizitu.mvp.contract.LoginContract;
import com.meizitu.mvp.presenter.LoginPresenter;

import com.meizitu.pojo.ResponseInfo;


import javax.inject.Inject;

import cc.easyandroid.easyclean.domain.easywork.EasyWorkContract;

import cc.easyandroid.easyutils.EasyToast;


public class LoginActivity extends BaseActivity implements LoginContract.View {
    @Inject
    LoginPresenter presenter;


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        inject();
        presenter.attachView(this);
    }

    private void inject() {
        DaggerLoginComponent.builder().activityModule(getActivityModule()).applicationComponent(getApplicationComponent()).build().inject(this);
    }


    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isUserNameValid(username)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            presenter.doLogin(username, password);
        }
    }

    private boolean isUserNameValid(String username) {
        return username.length() > 4;
    }


    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    @Override
    public void onLoginStart(Object o) {
        EasyToast.showShort(getApplicationContext(), "onStart");
    }

    @Override
    public void onLoginSuccess(Object o, ResponseInfo responseInfo) {
        EasyToast.showShort(getApplicationContext(), "onSuccess responseInfo=" + responseInfo);
        if (responseInfo != null && responseInfo.isSuccess()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    public void onLoginError(Object o, Throwable t) {
        onError(o, t);
    }

}

