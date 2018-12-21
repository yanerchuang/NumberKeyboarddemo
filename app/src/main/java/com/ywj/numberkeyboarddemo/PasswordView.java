package com.ywj.numberkeyboarddemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * 密码输入框
 * author: ywj
 * created on: 2018/12/16 16:38
 */
public class PasswordView extends View {

    //密码框宽度
    private int borderWidth = 5;
    //密码框分割线宽度
    private int lineWidth = 1;
    //密码框圆圈点的宽度
    private int passwordCircleWidth = 15;
    //密码最大长度
    private int passwordMaxLength = 6;


    private String password = "";
    private int height;
    private int width;

    private PasswordInputListener inputListener;


    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        //画中间的分割线
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(lineWidth);
        int horizontalDistance = width / passwordMaxLength;
        for (int i = 1; i < passwordMaxLength; i++) {
            int x = i * horizontalDistance;
            canvas.drawLine(x, 0, x, height, paint);
        }

        //画矩形
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(borderWidth);
        Rect rect = new Rect(0, 0, width, height);
        canvas.drawRect(rect, paint);

        if (!TextUtils.isEmpty(password)) {
            paint.setStyle(Paint.Style.FILL);
            for (int i = 0; i < password.length(); i++) {
                int x = (int) (horizontalDistance * (i + 0.5));
                canvas.drawCircle(x, height / 2, passwordCircleWidth, paint);
            }
        }


        canvas.save();
    }

    public void appendPassword(String password) {
        String tempPassword = this.password + password;
        if (TextUtils.isEmpty(tempPassword) || tempPassword.length() > passwordMaxLength) {
            return;
        }
        tempPassword = null;
        this.password += password;
        callListener();
        invalidate();
    }

    public void popupPassword() {
        int length = this.password.length();
        if (length <= 0) {
            return;
        }
        this.password = this.password.substring(0, length - 1);
        callListener();
        invalidate();
    }

    public void setPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return;
        }
        if (password.length() > passwordMaxLength) {
            return;
        }
        this.password = password;
        callListener();
        invalidate();
    }

    private void callListener() {
        if (this.inputListener != null) {
            inputListener.onPasswordChanged(password);
        }
    }

    public String getPassword() {
        return password;
    }

    public int getPasswordMaxLength() {
        return passwordMaxLength;
    }

    public void setInputListener(PasswordInputListener inputListener) {
        this.inputListener = inputListener;
    }

    public interface PasswordInputListener {
        void onPasswordChanged(String currentPassword);
    }
}
