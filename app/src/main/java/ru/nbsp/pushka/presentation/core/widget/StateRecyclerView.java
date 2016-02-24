package ru.nbsp.pushka.presentation.core.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class StateRecyclerView extends RecyclerView {

    public enum State {
        STATE_NORMAL,
        STATE_PROGRESS,
        STATE_EMPTY,
        STATE_ERROR
    }

    private View mEmptyView;
    private View mErrorView;
    private View mProgressView;
    private State mCurrentState = State.STATE_NORMAL;

    public StateRecyclerView(Context context) {
        super(context);
    }

    public StateRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StateRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private View getCurrentView() {
        switch (mCurrentState) {
            case STATE_EMPTY:
                return mEmptyView;
            case STATE_ERROR:
                return mErrorView;
            case STATE_PROGRESS:
                return mProgressView;
            default:
                return null;
        }
    }

    private void setCurrentViewVisibility(int visibilityState) {
        if (getCurrentView() != null) {
            getCurrentView().setVisibility(visibilityState);
        }
    }

    private void updateState(State newState) {
        setCurrentViewVisibility(GONE);
        mCurrentState = newState;
        setCurrentViewVisibility(VISIBLE);
    }

    public void setState(State newState) {
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
}
