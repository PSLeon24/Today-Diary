package com.prosoft.todaydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    CalendarView CalView;
    TextView tv_text;
    EditText dlgReset1, dlgReset2;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("오늘, 하루");

        CalView = (CalendarView) findViewById(R.id.CalView);
        tv_text = (TextView) findViewById(R.id.tv_text);

        CalView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                tv_text.setText(year + "년 " + (month + 1) + "월 " + day + "일");
            }
        });

    }

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
                        String reset1 = dlgReset1.getText().toString();
                        String reset2 = dlgReset2.getText().toString();
                        if (reset1.equals(reset2)) {
                            try {
                                FileOutputStream outFs = openFileOutput("password.txt", Context.MODE_PRIVATE);
                                outFs.write(reset1.getBytes());
                                outFs.close();
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
        }
        return false;
    }
}