package com.quickblox.sample.chat.ui.activity;

import android.os.Bundle;

import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.sample.chat.R;
import com.quickblox.sample.chat.utils.Consts;
import com.quickblox.sample.chat.utils.chat.ChatHelper;
import com.quickblox.sample.core.ui.activity.CoreSplashActivity;
import com.quickblox.sample.core.utils.ErrorUtils;
import com.quickblox.users.model.QBUser;

import java.util.List;

public class SplashActivity extends CoreSplashActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isChatInitializedJustNow = ChatHelper.initIfNeed(this);
        // If QBChatService is already running that means that
        // we already have created session from the last app launch
        // so we just proceeding to the next activity
        if (!isChatInitializedJustNow) {
            proceedToTheNextActivityWithDelay();
            return;
        }

        // Login to the REST API
        QBUser user = new QBUser(Consts.QB_USER_LOGIN, Consts.QB_USER_PASSWORD);
        ChatHelper.getInstance().login(user, new QBEntityCallbackImpl<String>() {
            @Override
            public void onSuccess() {
                proceedToTheNextActivity();
            }

            @Override
            public void onError(List<String> errors) {
                ErrorUtils.showErrorDialog(SplashActivity.this, R.string.splash_chat_login_error, errors);
                finish();
            }
        });
    }

    @Override
    protected String getAppName() {
        return getString(R.string.splash_app_title);
    }

    @Override
    protected void proceedToTheNextActivity() {
        DialogsActivity.start(this);
        finish();
    }
}