package com.ywj.numberkeyboarddemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import java.util.List;

public class NumKeyView extends KeyboardView implements KeyboardView.OnKeyboardActionListener {
    //删除按键背景图片
    private Drawable mDeleteDrawable;


    public NumKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public NumKeyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NumKeyView);
        mDeleteDrawable = ta.getDrawable(R.styleable.NumKeyView_deleteDrawable);
        ta.recycle();
        //获取xml中的按键布局
        Keyboard keyboard = new Keyboard(context, R.xml.numkeyview);
        setKeyboard(keyboard);
        setEnabled(true);
        setPreviewEnabled(false);
        setOnKeyboardActionListener(this);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {

            if (key.codes[0] == Keyboard.KEYCODE_DELETE) {
                //绘制按键图片
                drawkeyDelete(key, canvas);
            }
        }
    }

    private void drawkeyDelete(Keyboard.Key key, Canvas canvas) {
        int intrinsicWidth = mDeleteDrawable.getIntrinsicWidth();
        int intrinsicHeight = mDeleteDrawable.getIntrinsicHeight();
        int drawWidth = key.width;
        int drawHeight = key.height;
        if (drawWidth < intrinsicWidth) {
            drawHeight = drawWidth * intrinsicHeight / intrinsicWidth;
        }
        drawWidth = drawWidth / 3;
        drawHeight = drawHeight / 3;
        int widthInterval = (key.width - drawWidth) / 2;
        int heightInterval = (key.height - drawHeight) / 2;

        mDeleteDrawable.setBounds(key.x + widthInterval, key.y + heightInterval, key.x + widthInterval + drawWidth, key.y + heightInterval + drawHeight);
        mDeleteDrawable.draw(canvas);
    }

    //回调接口
    public interface OnKeyPressListener {
        void onInertKey(String text);

        void onDeleteKey();
    }

    private OnKeyPressListener mOnkeyPressListener;

    public void setOnKeyPressListener(OnKeyPressListener li) {
        mOnkeyPressListener = li;
    }

    @Override
    public void onKey(int i, int[] ints) {
        if (mOnkeyPressListener != null) {
            if (i == Keyboard.KEYCODE_DELETE) {
                //删除数据回调
                mOnkeyPressListener.onDeleteKey();
            } else {
                //添加数据回调
                mOnkeyPressListener.onInertKey(Character.toString((char) i));
            }
        }

    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }


    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeUp() {
    }
}
