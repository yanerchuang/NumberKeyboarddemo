package com.ywj.numberkeyboarddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private NumKeyView mKeyView;
    private PasswordView passwordView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        mKeyView = (NumKeyView) findViewById(R.id.keyboardview);
        passwordView = (PasswordView) findViewById(R.id.passwordView);

        mKeyView.setOnKeyPressListener(new NumKeyView.OnKeyPressListener() {
            @Override
            public void onInertKey(String text) {
                editText.append(text);
                passwordView.appendPassword(text);
            }

            @Override
            public void onDeleteKey() {
                int length = editText.getText().length();
                if (length > 0) {
                    //删除最后一位
                    editText.getText().delete(length - 1, length);
                    passwordView.popupPassword();
                }
            }
        });
        passwordView.setInputListener(new PasswordView.PasswordInputListener() {
            @Override
            public void onPasswordChanged(String currentPassword) {
                if (!TextUtils.isEmpty(currentPassword) && currentPassword.length() == passwordView.getPasswordMaxLength()) {
                    Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
