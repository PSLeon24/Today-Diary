package com.prosoft.todaydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String fileName;   //  fileName - 돌고 도는 선택된 날짜의 파일 이름
    CalendarView CalView;
    TextView tv_text, tv_text2, header_title;
    EditText dlgReset1, dlgReset2, hintPass, dlgName, contextDiary;
    View dialogView;
    Button newWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("오늘, 하루");

        // 오늘 날짜를 받게해주는 Calender 친구들
        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);
        // 첫 시작 시에는 오늘 날짜 일기 읽어주기
        //checkedDay(cYear, cMonth, cDay);

        CalView = (CalendarView) findViewById(R.id.CalView);
        tv_text = (TextView) findViewById(R.id.tv_text);
        tv_text2 = (TextView) findViewById(R.id.tv_text2);
        header_title = (TextView) findViewById(R.id.header_title);
        newWrite = (Button) findViewById(R.id.newWrite);
        contextDiary = (EditText) findViewById(R.id.contextDiary);

        // 새 일기 작성
        newWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newWrite.getText().toString().equals("수정하기")) {
                    try {
                        FileOutputStream outFs = openFileOutput(fileName,
                                Context.MODE_PRIVATE);
                        String str = contextDiary.getText().toString();
                        outFs.write(str.getBytes());
                        outFs.close();
                        Toast.makeText(getApplicationContext(), "일기 내용을 수정하였습니다.",
                                Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                    intent.putExtra("years", tv_text.getText().toString());
                    intent.putExtra("monthdays", tv_text2.getText().toString());
                    startActivity(intent);
                }
            }
        });

        // 이름 불러오기
        try {
            FileInputStream inFs = openFileInput("name.txt");
            byte[] user = new byte[inFs.available()];
            inFs.read(user);
            String name = new String(user);
            inFs.close();
            header_title.setText(name + " Secret Diary");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 달력 클릭 시
        CalView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                tv_text.setText(year + "년");
                tv_text2.setText((month + 1) + "월 " + day + "일");
                fileName = Integer.toString(year) + Integer.toString(month+1) + Integer.toString(day) + ".txt";
                //Toast.makeText(getApplicationContext(),fileName,Toast.LENGTH_SHORT).show();
                String str = readDiary(fileName);
                contextDiary.setText(str);
                //checkedDay(year, month, day);
            }
        });

    }

    // 일기 읽기
    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            contextDiary.setVisibility(View.VISIBLE);
            newWrite.setText("수정하기");
        } catch (IOException e) {
            contextDiary.setVisibility(View.INVISIBLE);
            newWrite.setText("새 일기 작성하기");
            e.printStackTrace();
        }
        return diaryStr;
    }

    // 일기 파일 읽기
    //private void checkedDay(int year, int month, int day) {
        // 받은 날짜로 날짜 보여주기
        //tv_text.setText(year + "년");
        //tv_text2.setText((month + 1) + "월 " + day + "일");
    //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.noteList:
                Intent intent = new Intent(getApplicationContext(), DiaryList.class);
                startActivity(intent);
                return true;

            case R.id.resetPin:
                dialogView = (View) View.inflate(MainActivity.this,
                        R.layout.reset_pin_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("PIN 재설정");
                dlg.setIcon(R.drawable.resetpng);
                dlg.setView(dialogView);
                dlg.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dlgReset1 = (EditText) dialogView.findViewById(R.id.dlgReset1);
                        dlgReset2 = (EditText) dialogView.findViewById(R.id.dlgReset2);
                        hintPass = (EditText) dialogView.findViewById(R.id.hintPass);
                        String reset1 = dlgReset1.getText().toString();
                        String reset2 = dlgReset2.getText().toString();
                        String hintPassword = hintPass.getText().toString();
                        if (reset1.equals(reset2)) {
                            try {
                                FileOutputStream outFs = openFileOutput("password.txt", Context.MODE_PRIVATE);
                                outFs.write(reset1.getBytes());
                                outFs.close();
                                FileOutputStream outFs2 = openFileOutput("hint.txt", Context.MODE_PRIVATE);
                                outFs2.write(hintPassword.getBytes());
                                outFs2.close();
                                Toast.makeText(getApplicationContext(), "핀 번호가 " + reset1 + "으로 변경되었습니다!", Toast.LENGTH_SHORT).show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "변경할 핀 번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
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
                return true;

            case R.id.resetName:
                dialogView = (View) View.inflate(MainActivity.this,
                        R.layout.set_user_name, null);
                AlertDialog.Builder dlg2 = new AlertDialog.Builder(MainActivity.this);
                dlg2.setTitle("사용자 이름 설정");
                dlg2.setIcon(R.drawable.resetpng);
                dlg2.setView(dialogView);
                dlg2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dlgName = (EditText) dialogView.findViewById(R.id.dlgName);
                        String userName = dlgName.getText().toString();
                        if (userName != null) {
                            try {
                                FileOutputStream outFs = openFileOutput("name.txt", Context.MODE_PRIVATE);
                                outFs.write(userName.getBytes());
                                outFs.close();
                                Toast.makeText(getApplicationContext(), userName + "님 반갑습니다!", Toast.LENGTH_SHORT).show();
                                header_title.setText(userName + "님의 Secret Diary");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "이름을 입력해주세요!", Toast.LENGTH_SHORT).show();
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
                return true;
        }
        return false;
    }
}