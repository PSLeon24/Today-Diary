package com.prosoft.todaydiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    EditText pinNumber, dlgName, dlgHint;
    TextView textInform;
    Button btnLogin, btnFindPin;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("오늘, 하루");

        pinNumber = (EditText) findViewById(R.id.pinNumber);
        textInform = (TextView) findViewById(R.id.textInform);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnFindPin = (Button) findViewById(R.id.btnFindPin);
        final View[] dialogView = new View[1];

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
                dialogView[0] = (View) View.inflate(LoginActivity.this,
                       R.layout.set_user_name, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(LoginActivity.this);
                dlg.setTitle("사용자 이름 설정");
                dlg.setIcon(R.drawable.resetpng);
                dlg.setView(dialogView[0]);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dlgName = (EditText) dialogView[0].findViewById(R.id.dlgName);
                       String userName = dlgName.getText().toString();
                       if (userName != null) {
                           try {
                               FileOutputStream outFs = openFileOutput("name.txt", Context.MODE_PRIVATE);
                               outFs.write(userName.getBytes());
                               outFs.close();
                               Toast.makeText(getApplicationContext(), userName + "님 반갑습니다!", Toast.LENGTH_SHORT).show();
                           } catch (FileNotFoundException e) {
                               e.printStackTrace();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       } else {
                           Toast.makeText(getApplicationContext(), "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
                           dlg.show();
                       }
                   }
               });
               dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       Toast.makeText(getApplicationContext(), "취소했습니다.",
                               Toast.LENGTH_SHORT).show();
                   }
               });
               dlg.show();
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

        // PIN 찾기 구현
        btnFindPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // 힌트 값 불러오기
                    FileInputStream inFs = openFileInput("hint.txt");
                    byte[] hints = new byte[inFs.available()];
                    inFs.read(hints);
                    String passHint = new String(hints);
                    inFs.close();

                    dialogView[0] = (View) View.inflate(LoginActivity.this,
                            R.layout.find_pin_dialog, null);
                    AlertDialog.Builder dlg2 = new AlertDialog.Builder(LoginActivity.this);
                    dlg2.setTitle("PIN 찾기");
                    dlg2.setIcon(R.drawable.resetpng);
                    dlg2.setView(dialogView[0]);
                    dlg2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dlgHint = (EditText) dialogView[0].findViewById(R.id.hintPass);
                            String userName = dlgHint.getText().toString();
                            if (dlgHint != null) {
                                if (dlgHint.getText().toString().equals(passHint)) {
                                    try {
                                        FileInputStream inFs = openFileInput("password.txt");
                                        byte[] passwords = new byte[inFs.available()];
                                        inFs.read(passwords);
                                        String passValue = new String(passwords);
                                        inFs.close();
                                        Toast.makeText(getApplicationContext(), passValue + " 입니다!", Toast.LENGTH_LONG).show();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "비밀번호 힌트가 옳지 않습니다!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "비밀번호 힌트를 입력해주세요!", Toast.LENGTH_SHORT).show();
                                dlg2.show();
                            }
                        }
                    });
                    dlg2.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "취소했습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    dlg2.show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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
