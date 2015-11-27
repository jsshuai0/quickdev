package com.anthole.quickdev.invoke;

import com.anthole.quickdev.commonUtils.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 用于  更改状态栏的背景
 * @author Administrator
 *
 */
public class SystemBarTintInvoke {
	
//	public abstract class SystemBarTintActivity extends BaseActivity {
//		
//		View rootView;
//		
//		@Override
//		protected void onCreate(Bundle savedInstanceState) {
//			setTheme(R.style.FullBleedTheme);
//			super.onCreate(savedInstanceState);
//		}
//		
//		@Override
//		public void setContentView(int layoutResID) {
//			super.setContentView(layoutResID);
//			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//				setTranslucentStatus(true);
//				SystemBarTintManager tintManager = new SystemBarTintManager(this);
//				tintManager.setStatusBarTintEnabled(true);
//				tintManager.setStatusBarTintResource(R.color.statusbar_bg);
//			}
//			rootView = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0); 
//			if(rootView!=null){
//				rootView.setFitsSystemWindows(true);
//			}
//		}
//
//		
//		@TargetApi(19) 
//		private void setTranslucentStatus(boolean on) {
//			Window win = getWindow();
//			WindowManager.LayoutParams winParams = win.getAttributes();
//			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//			if (on) {
//				winParams.flags |= bits;
//			} else {
//				winParams.flags &= ~bits;
//			}
//			win.setAttributes(winParams);
//		}
//
//
//	}
	
	public static void apply(Activity activity, int res){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(activity,true);
			SystemBarTintManager tintManager = new SystemBarTintManager(activity);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(res);
		}
		View rootView = ((ViewGroup)(activity.findViewById(android.R.id.content))).getChildAt(0); 
		if(rootView!=null){
			rootView.setFitsSystemWindows(true);
		}
	}
	
	@TargetApi(19) 
	private static void setTranslucentStatus(Activity activity,boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

}
