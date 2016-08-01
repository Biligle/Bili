package com.shanshan.housekeeper.Help.utils;

import android.content.Context;
import android.widget.Toast;

/**
 *  
 */
public class MyToastView {
	private static Toast mToast;
	/**
	 * @param text
	 * @param context
	 */
	public static void showToast(String text,Context context) {    
        if(mToast == null) {    
        	try{
                mToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);    
        	}catch(Exception e){
        		mToast=null;
        		return;
        	}
    
        } else {    
        	try{
        		   mToast.setText(text);      
			}catch(Exception e){
				
			}
         
            mToast.setDuration(Toast.LENGTH_SHORT);    
        }    
        mToast.show();    
    } 
	 
}
