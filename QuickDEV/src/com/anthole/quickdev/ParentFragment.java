package com.anthole.quickdev;

import java.lang.reflect.Field;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;


public abstract class ParentFragment extends QFragment {
	
	
	protected void addFragment(Fragment fragment, int containerID){
		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.add(containerID, fragment);
		transaction.commitAllowingStateLoss();
	}
	
	protected void hideFragment(Fragment fragment){
		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.hide(fragment);
		transaction.commitAllowingStateLoss();
		
	}
	
	protected void showFragment(Fragment fragment){
		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.show(fragment);
		transaction.commitAllowingStateLoss();
	}
	
	protected void removeFragment(Fragment fragment){
		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.remove(fragment);
		transaction.commitAllowingStateLoss();
	}

	protected void replaceFragment(Fragment fragment, int containerID) {

		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.setCustomAnimations(0, 0);
		transaction.replace(containerID, fragment);
		transaction.commitAllowingStateLoss();
	}

	protected void replaceFragment(Fragment fragment, int containerID, int animIn, int animOut) {
		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		if (animIn > 0 || animOut > 0) {
			transaction.setCustomAnimations(animIn, animOut);
		}
		transaction.replace(containerID, fragment);
		transaction.commitAllowingStateLoss();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			// 参数是固定写法
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
