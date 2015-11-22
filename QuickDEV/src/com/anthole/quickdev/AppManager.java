package com.anthole.quickdev;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.Fragment;


public class AppManager {

	private static Stack<Activity> activityStack = new Stack<Activity>();
	private static Stack<Fragment> fragmentStack = new Stack<Fragment>();
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}




	public boolean whetherExistActivity(Activity a) {
		if (activityStack.contains(a)) {
			return true;
		}
		return false;

	}

	public int getActivityCount() {
		return activityStack.size();
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		activityStack.add(activity);
	}
	
	public Activity getActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				return activity;
			}
		}
		return null;
	}

	/**
	 * 获取当前Activity（堆栈中压入的）
	 */
	public Activity currentActivity() {
		if(activityStack.size()>0){
			return activityStack.lastElement();
		}
		return null;
	}

	/**
	 * 结束当前Activity（堆栈中压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有的Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 安全退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			fragmentStack.clear();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
			android.os.Process.killProcess(android.os.Process.myPid());
		} catch (Exception e) {
		}
	}
	
	/**
	 * 添加一个fragment
	 * @param fragment
	 */
	public void addFragment(Fragment fragment){
		if (fragmentStack == null) {
			fragmentStack = new Stack<Fragment>();
		}
		fragmentStack.add(fragment);
	}
	
	/**
	 * 移除一个fragment
	 * @param fragment
	 */
	public void removeFragment(Fragment fragment){
		fragmentStack.remove(fragment);
	}
	
	/**
	 * 当前fragment
	 * @return
	 */
	public Fragment currentFragment(){
		if(fragmentStack.size()>0){
			return fragmentStack.lastElement();
		}
		return null;
	}
	
	public Fragment getFragment(Class<?> cls){
		for (Fragment fragment : fragmentStack) {
			if (fragment.getClass().equals(cls)) {
				return fragment;
			}
		}
		return null;
	}
}