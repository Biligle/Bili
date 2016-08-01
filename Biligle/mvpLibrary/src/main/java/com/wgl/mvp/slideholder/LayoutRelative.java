package com.wgl.mvp.slideholder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * 自定义布局
 */
public class LayoutRelative extends RelativeLayout {

	// 识别手势的类
	private GestureDetector mGestureDetector;

	private boolean bLockScrollX = false;

	private boolean bTouchIntercept = false;

	// 滚动监听
	private OnScrollListener mOnScrollListenerCallback = null;
	private int leftOrRight = 1;

	/**
	 * 构造函数
	 *
	 * @param context
	 */
	public LayoutRelative(Context context) {
		this(context, null);
	}

	/**
	 * 构造函数
	 *
	 * @param context
	 * @param attrs
	 */
	public LayoutRelative(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * 构造函数
	 *
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public LayoutRelative(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 实例化手势识别类
		mGestureDetector = new GestureDetector(new LayoutGestureListener());
	}

	/**
	 * 设置滚动监听接口
	 *
	 * @param l
	 */
	public void setOnScrollListener(OnScrollListener l) {
		mOnScrollListenerCallback = l;
	}

	/*
	 * Progress: 1. 重载dispatchTouchEvent，将消息传递给GestureDetector; 2. 重载手势中onDown 和
	 * onScroll两个函数; 3. 在onDown中，默认对水平滚动方向加锁; 4. 在onScroll中，判断e1与e2的水平方向与垂直方向距离:
	 * a. 如果垂直方向大，则表明是上下滚动，且返回false表明当前手势不用拦截； b.
	 * 如果水平方向大，则表明是左右滚动，且返回true表明当前手势需要拦截； 5.
	 * 重载onInterceptTouchEvent，如果手势返回为true，则onInterceptTouchEvent也返回true； 6.
	 * 如果要拦截手势消息，则需要重载onTouchEvent，或子视图中重载这个函数，来处理这条消息； 7.
	 * 如果自己处理，则对水平方向滚动去锁(表明当前用户想左右滚动)；
	 *
	 * ---------- ---------------------- ------>| onDown | | | | ---------- |
	 * dispatchTouchEvent | <---- ------ false: 上下滚动 | | | ------------ /
	 * ---------------------- ------>| onScroll | ---- | ------------ \ | ------
	 * true : 左右滚动 | intercept = true ---------------- |----------------------|
	 * onTouchEvent | | ---------------- ------------------------- | | |
	 * onInterceptTouchEvent | | | -------------------------
	 */

	/*
	 * 用来分发TouchEvent
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		bTouchIntercept = mGestureDetector.onTouchEvent(ev);

		if (MotionEvent.ACTION_UP == ev.getAction() && !bLockScrollX) {
			if (mOnScrollListenerCallback != null) {
				mOnScrollListenerCallback.doOnRelease();
			}
		}

		return super.dispatchTouchEvent(ev);
	}

	/*
	 * 用来拦截TouchEvent
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		super.onInterceptTouchEvent(ev);
		return bTouchIntercept;
	}

	// view.onTouchEvent
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		bLockScrollX = false;
		return super.onTouchEvent(event);
	}

	/**
	 * 自定义手势监听
	 */
	public class LayoutGestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			bLockScrollX = true;
			return super.onDown(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
								float distanceX, float distanceY) {
		/*	if (!bLockScrollX) {
				if (mOnScrollListenerCallback != null) {
					mOnScrollListenerCallback.doOnScroll(e1, e2, distanceX,
							distanceY);
				}
			}
*/
			try {
				if (Math.abs(e1.getY() - e2.getY()) > Math.abs(e1.getX()
                        - e2.getX())) {
                    return false;
                } else {
                    return true;
                }
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {
				if( e1.getX() - e2.getX()< -120){//右面的侧滑
                    mOnScrollListenerCallback.doOnScrollRight(e1, e2, velocityX,
                            velocityY);
                }else if(e1.getX() - e2.getX()>120){//左面的
                    mOnScrollListenerCallback.doOnScrollLeft(e1, e2, velocityX,
                            velocityY);
                }
			} catch (Exception e) {
				e.printStackTrace();
			}

			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}

	public interface OnScrollListener {

		void doOnScrollRight(MotionEvent e1, MotionEvent e2, float distanceX,
						float distanceY);//右边滑出

		void doOnScrollLeft(MotionEvent e1, MotionEvent e2, float distanceX,
							float distanceY);//左边滑出
		void doOnRelease();//UP之后的操作
	}
}
