package com.prosoft.todaydiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    //String pass;

    EditText pinNumber;
    TextView textInform;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("오늘, 하루");

        pinNumber = (EditText) findViewById(R.id.pinNumber);
        textInform = (TextView) findViewById(R.id.textInform);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // 최초 실행 여부를 판단 ->>>
        SharedPreferences pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
        boolean checkFirst = pref.getBoolean("checkFirst", false);

        if(!checkFirst) {
            // 앱 최초 실행시 비밀번호가 0000인 password.txt 파일 생성
           try {
                FileOutputStream outFs = openFileOutput("password.txt", Context.MODE_PRIVATE);
                String pw = "0000";
                outFs.write(pw.getBytes());
                outFs.close();
            } catch (FileNotFoundException e) {
               e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("checkFirst",true);
            editor.apply();
            finish();
        }

        // Login 구현

        // btnLogin 버튼 처리
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // password 파일 읽어서 비밀번호 pass 변수에 저장
                try {
                    FileInputStream inFs = openFileInput("password.txt");
                    byte[] txt = new byte[inFs.available()];
                    inFs.read(txt);
                    String pass = new String(txt);
                    inFs.close();
                    if (pinNumber.getText().toString().equals(pass)){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다!",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
