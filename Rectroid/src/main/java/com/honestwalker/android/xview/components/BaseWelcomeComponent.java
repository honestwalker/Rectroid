package com.honestwalker.android.xview.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.honestwalker.android.xview.Component;
import com.honestwalker.android.xview.ComponentActivity;
import com.honestwalker.android.xview.R;
import com.honestwalker.androidutils.UIHandler;
import com.honestwalker.androidutils.ViewUtils.ViewSizeHelper;

/**
 * Created by lanzhe on 16-11-16.
 */
public abstract class BaseWelcomeComponent extends Component {

    private ImageView welcomeIV;

    private Bitmap welcomeBMP;

    public BaseWelcomeComponent(Context context) {
        super(context);
    }

    public BaseWelcomeComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWelcomeComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if(welcomeBMP != null && !welcomeBMP.isRecycled()) {
                welcomeIV.setImageBitmap(null);
                welcomeBMP.recycle();
            }
        } catch (Exception e){}
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initComponent() {

        welcomeIV = (ImageView) findViewById(R.id.imageview1);

    }

    private void setWithBitmap(Bitmap bitmap) {
        setImageSize(screenWidth(), screenWidth() * bitmap.getHeight() / bitmap.getWidth());
        welcomeIV.setImageBitmap(bitmap);
    }

    /**
     * 设置欢迎页面位图
     * @param bitmap 位图
     */
    public void setImageBitmap(Bitmap bitmap) {
        this.welcomeBMP = bitmap;
        setWithBitmap(this.welcomeBMP);
    }

    /**
     * 设置欢迎页面图片
     * @param res 资源id
     */
    public void setImageResource(int res) {
        this.welcomeBMP = BitmapFactory.decodeResource(getResources(), res);
        setWithBitmap(this.welcomeBMP);
    }

    public void setImageSize(int width, int height) {
        ViewSizeHelper.getInstance(getContext()).setSize(welcomeIV, width, height);
    }

    public WelcomeSleepAction getWelcomeSleepAction() {
        return new WelcomeSleepAction();
    }

    /**
     * 设定欢迎页面等待逻辑
     */
    public class WelcomeSleepAction {

        /**
         * 等待指定时间后跳转
         * @param time           指定等待时间
         * @param nextComponent  跳转到的组件页面(对象class)
         */
        public void waitTime(long time, final Class nextComponent) {
            UIHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ComponentActivity.startComponent(getActivity(), nextComponent);
                }
            }, time);
        }

        /**
         * 等待指定时间后跳转
         * @param time                   指定等待时间
         * @param nextComponentPackage   跳转到的组件页面(包名)
         */
        public void waitTime(long time, final String nextComponentPackage) {
            UIHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        Class componentClass = Class.forName(nextComponentPackage);
                        ComponentActivity.startComponent(getActivity(), componentClass);
                    } catch (Exception e) {}
                }
            }, time);
        }

        /**
         * 自定义跳转
         * @param runnable 跳转逻辑
         */
        public void execute(Runnable runnable) {
            runnable.run();
        }

    }

    @Override
    protected int contentViewLayout() {
        return R.layout.component_welcome;
    }

}
