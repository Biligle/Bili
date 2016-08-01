package com.shanshan.housekeeper.Help.defaultview;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.shanshan.housekeeper.Help.utils.CommonUtil;
import com.shanshan.housekeeper.R;

public class CustomProgressDialog extends ProgressDialog {
	public static TextView tv1;
	public static CustomProgressDialog dialog;
	public static String msg;

	private CustomProgressDialog(Context context) {
		super(context);
	}

	private CustomProgressDialog(Context context, int theme, String m) {
		super(context, theme);
		msg = m;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customprogressdialog);
		tv1 = (TextView) this.findViewById(R.id.textView1);
		if(!CommonUtil.isNull(msg)){
			tv1.setText(msg);
		}

	}

	public static CustomProgressDialog show(Context context, String msg) {
		if (dialog != null)
			dialog = null;
		dialog = new CustomProgressDialog(context, R.style.dialog, msg);

		return dialog;
	}

	public void closeDialog() {
		try{
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
				dialog = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}