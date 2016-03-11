package ru.nbsp.pushka.presentation.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;

/**
 * Created by Dimorinny on 11.03.16.
 */
public class AnimatedStateRecyclerView extends StateRecyclerView {

    public interface AfterAnimationTask {
        void call();
    }

    private boolean mAnimationCalled = false;
    private boolean mAnimationStarted = false;
    private AfterAnimationTask mAfterAnimationTask;

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
    public void setLayoutAnimation(LayoutAnimationController controller) {
        super.setLayoutAnimation(controller);

        setLayoutAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.v("qwe", "start");
                mAnimationStarted = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.v("qwe", "end");
                mAnimationStarted = false;
                if (mAfterAnimationTask != null) {
                    mAfterAnimationTask.call();
                    mAfterAnimationTask = null;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    @Override
    public void scheduleLayoutAnimation() {
        if (!mAnimationCalled) {
            mAnimationCalled = true;
            super.scheduleLayoutAnimation();
        }
    }

    public void executeTaskAfterAnimation(AfterAnimationTask afterAnimationTask) {
        if (mAnimationStarted) {
            mAfterAnimationTask = afterAnimationTask;
        } else {
            afterAnimationTask.call();
        }
    }
}
