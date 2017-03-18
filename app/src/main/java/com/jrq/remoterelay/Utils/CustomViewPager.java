package com.jrq.remoterelay.Utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jrq on 2016-07-16.
 */
public class CustomViewPager extends ViewPager
{
    private static boolean enabled;
    private Context context;

    public CustomViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.enabled = false;
        this.context = context;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {

        if (this.enabled) {
            super.setOnClickListener(l);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }


    public void setPagingEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean getStatusEnabled() {
        return enabled;
    }
}

