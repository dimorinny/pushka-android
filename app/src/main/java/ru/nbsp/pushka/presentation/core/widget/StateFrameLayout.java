package ru.nbsp.pushka.presentation.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Dimorinny on 02.05.16.
 */
public class StateFrameLayout extends FrameLayout {

    private View mEmptyView;
    private View mErrorView;
    private View mProgressView;
    private View mNormalView;
    private ru.nbsp.pushka.presentation.core.state.State mCurrentState = ru.nbsp.pushka.presentation.core.state.State.STATE_NORMAL;

    public StateFrameLayout(Context context) {
        this(context, null);
    }

    public StateFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    private View getCurrentView() {
        switch (mCurrentState) {
            case STATE_EMPTY:
                return mEmptyView;
            case STATE_ERROR:
                return mErrorView;
            case STATE_PROGRESS:
                return mProgressView;
            case STATE_NORMAL:
                return mNormalView;
            default:
                return null;
        }
    }

    private void setCurrentViewVisibility(int visibilityState) {
        if (getCurrentView() != null) {
            getCurrentView().setVisibility(visibilityState);
        }
    }

    private void updateState(ru.nbsp.pushka.presentation.core.state.State newState) {
        setCurrentViewVisibility(GONE);
        mCurrentState = newState;
        setCurrentViewVisibility(VISIBLE);
    }

    public void setState(ru.nbsp.pushka.presentation.core.state.State newState) {
        if (newState != mCurrentState) {
            updateState(newState);
        }
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setErrorView(View errorView) {
        mErrorView = errorView;
    }

    public void setProgressView(View progressView) {
        mProgressView = progressView;
    }

    public void setNormalView(View normalView) {
        mNormalView = normalView;
    }
}
