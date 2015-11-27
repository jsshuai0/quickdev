package com.anthole.quickdev.ui.swipeback.app;

import com.anthole.quickdev.ui.swipeback.SwipeBackLayout;
/**
 * @author Yrom
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
    
    /**
     * 创建 swipe 后 需要做的操作
     */
    public abstract void afterInitSwipe();

}
