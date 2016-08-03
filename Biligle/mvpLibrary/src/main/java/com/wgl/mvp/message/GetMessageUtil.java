package com.wgl.mvp.message;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

/**
 * Created by wgl.
 */
    public class GetMessageUtil extends ContentObserver {

        private Cursor cursor = null;
        private Activity activity;
        private String phone;//截获的号码
    private IGetMessage iMessage;

        public GetMessageUtil(Activity activity, IGetMessage iMessage, Handler handler, String phone) {
            super(handler);
            this.activity = activity;
            this.iMessage = iMessage;
            this.phone = phone;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            // 读取收件箱中指定号码的短信(发件箱:"content://sms/outbox")
            if(null == phone || "".equals(phone)){
                cursor = activity.managedQuery(Uri.parse("content://sms/inbox"),
                        null, null,
                        null, "_id desc");
            }else{
                cursor = activity.managedQuery(Uri.parse("content://sms/inbox"),
                        new String[] { "_id", "address", "read", "body" }, "address=? and read=?",
                        new String[] {phone, "0" }, "_id desc");
            }
            // 按短信id排序，如果按date排序的话，修改手机时间后，读取的短信就不准了
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                if (cursor.moveToFirst()) {
                    String smsbody = cursor
                            .getString(cursor.getColumnIndex("body"));
                    iMessage.getMessage(smsbody);
//                    message = smsbody;
//                    Toast.makeText(activity,smsbody,Toast.LENGTH_SHORT).show();
//                if(codeCount == 6){
//                    regEx = "[0-9]{6}";
//                }else if(codeCount == 6){
//                    regEx = "[0-9]{4}";
//                }
//                List<String> list = new ArrayList<String>();
//                Pattern p = Pattern.compile(regEx);
//                Matcher m = p.matcher(smsbody.toString());
//                while (m.find()) {
//                    if (!"".equals(m.group())){
//                        //只取第一个
//                        list.add(m.group());
//                    }
//                    if(list.size()>0){
//                        editText.setText(list.get(0));
//                    }
//                }
                }
            }
        }


}
