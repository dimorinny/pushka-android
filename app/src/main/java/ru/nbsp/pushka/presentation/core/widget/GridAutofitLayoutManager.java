package ru.nbsp.pushka.presentation.core.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;

public class GridAutofitLayoutManager extends StaggeredGridLayoutManager {
    private int mColumnWidth;
    private boolean mColumnWidthChanged = true;
    private Context mContext;

    public GridAutofitLayoutManager(Context context, int columnWidth, int orientation) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(1, orientation);
        mContext = context;
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    public GridAutofitLayoutManager(Context context, int columnWidth) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        this(context, columnWidth, VERTICAL);
    }

    private int checkedColumnWidth(Context context, int columnWidth) {
        if (columnWidth <= 0) {
            /* Set default columnWidth value (48dp here). It is better to move this constant
            to static constant on top, but we need context to convert it to dp, so can't really
            do so. */
            columnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48,
                    context.getResources().getDisplayMetrics());
        }
        return columnWidth;
    }

    public void setColumnWidth(int newColumnWidth) {
        if (newColumnWidth > 0 && newColumnWidth != mColumnWidth) {
            mColumnWidth = newColumnWidth;
            mColumnWidthChanged = true;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mColumnWidthChanged && mColumnWidth > 0) {
            int totalSpace;
            if (getOrientation() == VERTICAL) {
                totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
            } else {
                totalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
            }
            final int spanCount = Math.max(1, totalSpace / mColumnWidth);
            new Handler(mContext.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    setSpanCount(spanCount);
                }
            });
            mColumnWidthChanged = false;
        }
        super.onLayoutChildren(recycler, state);
    }
}
