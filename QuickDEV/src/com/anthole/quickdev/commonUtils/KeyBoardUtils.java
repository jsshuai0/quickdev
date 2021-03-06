package com.anthole.quickdev.commonUtils;

import com.anthole.quickdev.QActivity;

import android.app.Activity;
import android.content.Context;  
import android.view.View;
import android.view.inputmethod.InputMethodManager;  
import android.widget.EditText;  
  
/** 
 * 打开或关闭软键盘 
 *  
 * @author zhy 
 *  
 */  
public class KeyBoardUtils  
{  
    /** 
     * 打卡软键盘 
     *  
     * @param mEditText 
     *            输入框 
     * @param mContext 
     *            上下文 
     */  
    public static void openKeybord(EditText mEditText, Context mContext)  
    {  
        InputMethodManager imm = (InputMethodManager) mContext  
                .getSystemService(Context.INPUT_METHOD_SERVICE);  
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);  
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,  
                InputMethodManager.HIDE_IMPLICIT_ONLY);  
    }  
  
    /** 
     * 关闭软键盘 
     *  
     * @param mEditText 
     *            输入框 
     * @param mContext 
     *            上下文 
     */  
    public static void closeKeybord(EditText mEditText, Context mContext)  
    {  
        InputMethodManager imm = (InputMethodManager) mContext  
                .getSystemService(Context.INPUT_METHOD_SERVICE);  
  
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);  
    }  
    
    /**
     * 关闭软键盘
     * @param context
     */
    public static void hideSoftInput(Context context){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		View currentFocus;
		if(context instanceof Activity){
			currentFocus = ((Activity)context).getCurrentFocus();
			if(currentFocus!=null){
				imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0); 
			}
		}
	}
    
}  