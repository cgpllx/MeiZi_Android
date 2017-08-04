package com.meizitu.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cc.easyandroid.easyrecyclerview.core.progress.EasyProgressViewPager;

/**EasyProgressViewPager
 */
public class FixedViewPager extends EasyProgressViewPager {
    public FixedViewPager(Context context) {
        super(context);
    }

    public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (IllegalArgumentException ignored) {
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return false;
    }

}