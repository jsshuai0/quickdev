
package com.anthole.quickdev.ui.swipeback.app;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;

import com.anthole.quickdev.ui.swipeback.SwipeBackLayout;

public abstract class SwipeBackPreferenceActivity extends PreferenceActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
        afterInitSwipe();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }
    
    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }
    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
