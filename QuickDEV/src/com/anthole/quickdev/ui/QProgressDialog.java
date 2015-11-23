package com.anthole.quickdev.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.anthole.quickdev.R;
import com.anthole.quickdev.http.RequestHandle;

public class QProgressDialog extends ProgressDialog implements IProgressDialog{

	
	private View rootView;
	private TextView progressDialogContent;
	private ProgressWheel progressWheel;
	
	private RequestHandle requestHandle;
	
	public QProgressDialog(Context context) {
		super(context,R.style.LoadingDialog);
		rootView = LayoutInflater.from(context).inflate(R.layout.qprogress_dialog, null);
		initView();
		setCanceledOnTouchOutside(false);
		setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				if(QProgressDialog.this.requestHandle!=null){
					QProgressDialog.this.requestHandle.cancel(true);
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(rootView);
	}
	
	@Override
	public void setOnCancelListener(OnCancelListener listener) {
		super.setOnCancelListener(listener);
	}
	
	private void initView() {
		progressDialogContent = (TextView) rootView.findViewById(R.id.content);
		progressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
	}
	
	@Override
	public void show() {
		super.show();
	}
	
	@Override
	public void dismiss() {
		super.dismiss();
		progressWheel.stop();
	}

	@Override
	public void dismissDialog() {
		dismiss();
	}

	@Override
	public void setContent(RequestBean requestBean) {
		if(progressDialogContent!=null){
			progressDialogContent.setText(requestBean.getContent());
		}
		this.requestHandle = requestBean.getHandle();
		progressWheel.start();
	}

	@Override
	public void showDialog() {
		show();
	}

}

