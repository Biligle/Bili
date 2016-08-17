package com.shanshan.housekeeper.login;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.shanshan.housekeeper.R;
import com.wgl.mvp.circleImageView.CircleImageView;
import com.wgl.mvp.slideholder.LayoutRelative;
import com.wgl.mvp.slideholder.SlideHolder;
import com.wgl.mvp.view.AppDelegate;

/**View视图
 * Created by wgl;
 */
public class VLogin extends AppDelegate {

    public EditText etUser,etPassword;
    public Button btLogin,bt1,bt2,bt3,bt_1,bt_2,bt_3,bt_5,bt_7,bt_8;
    public CircleImageView imageView;
    public PopupWindow popupWindow;
    public View view;
    public TextView left,right;
    public SlideHolder slideHolder;
    public LayoutRelative layoutRelativeLeft,layoutRelativeRight;
    public Animation animToLeft;
    public Animation animToRight;
    public FrameLayout frame;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public int getBrotherLayoutId() {
        return R.layout.dialog_touxiang;
    }

    @Override
    public int getBrotherLayoutId2() {
        return 0;
    }

    @Override
    public void initWidget() {
        etUser = get(R.id.et_user);
        etPassword = get(R.id.et_password);
        btLogin = get(R.id.button1);
        imageView = get(R.id.tou);
        popupWindow = new PopupWindow(getBrotherView(),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view = getBrotherView();
        bt1 = getBrother(R.id.bt1_touxiang);
        bt2 = getBrother(R.id.bt2_touxiang);
        bt3 = getBrother(R.id.bt3_touxiang);
        left = get(R.id.left);
        right = get(R.id.right);
        slideHolder = get(R.id.slideHolder);
        layoutRelativeLeft = get(R.id.main_activity_slidingLeft);
        layoutRelativeRight = get(R.id.main_activity_slidingRight);
        layoutRelativeLeft.setAlpha(0.8f);
        layoutRelativeRight.setAlpha(0.8f);
        animToLeft = AnimationUtils.loadAnimation(getActivity(),R.anim.push_left_in);
        animToRight = AnimationUtils.loadAnimation(getActivity(),R.anim.push_left_out_1);
        frame = get(R.id.frame);
        bt_1 = get(R.id.bt1);
        bt_2 = get(R.id.bt2);
        bt_3 = get(R.id.bt3);
        bt_5 = get(R.id.bt5);
        bt_7 = get(R.id.bt7);
        bt_8 = get(R.id.bt8);
    }
}
