package ru.nbsp.pushka.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

@SuppressWarnings("deprecation")
public class GUIUtils {

	public interface OnRevealAnimationListener {
		void onRevealStart();
		void onRevealStop();
	}

    public GUIUtils() {}

//	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
//	public void animateRevealHide(final Context ctx, final View view, int duration, @ColorRes final int color,
//										 final int finalRadius, final OnRevealAnimationListener listener) {
//		int cx = (view.getLeft() + view.getRight()) / 2;
//		int cy = (view.getTop() + view.getBottom()) / 2;
//		int initialRadius = view.getWidth();
//
//		Animator anim =
//			ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, finalRadius);
//		anim.addListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationStart(Animator animation) {
//				super.onAnimationStart(animation);
//                view.setBackgroundColor(ContextCompat.getColor(ctx, color));
//			}
//
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				super.onAnimationEnd(animation);
//				listener.onRevealHide();
//				view.setVisibility(View.INVISIBLE);
//			}
//		});
//		anim.setDuration(duration);
//		anim.start();
//	}

	public void animateRevealShow(final Context ctx, final View view, long duration, final int startRadius, final float finalRadius,
										 @ColorRes final int color, int x, int y, final OnRevealAnimationListener listener) {
		Animator anim =
			ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, finalRadius);
		anim.setDuration(duration);
		anim.setStartDelay(30);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
                listener.onRevealStart();
                view.setBackgroundColor(ContextCompat.getColor(ctx, color));
			}

			@Override
			public void onAnimationEnd(Animator animation) {
                listener.onRevealStop();
			}
		});
		anim.start();
	}
}