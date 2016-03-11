package ru.nbsp.pushka.presentation.core.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Dimorinny on 11.03.16.
 */
public class AnimatedStateRecyclerView extends StateRecyclerView {

    private boolean mAnimationCalled = false;

    public AnimatedStateRecyclerView(Context context) {
        super(context);
    }

    public AnimatedStateRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedStateRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void scheduleLayoutAnimation() {
        if (!mAnimationCalled) {
            mAnimationCalled = true;
            super.scheduleLayoutAnimation();
        }
    }
}
