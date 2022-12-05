package com.prosoft.todaydiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteActivity extends AppCompatActivity {

    TextView writeDay;
    EditText edtDiary;

    String fileName;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.active_write);
        setTitle("일기 작성");

        String year, monthday;
        String fnames = "";

        edtDiary = (EditText) findViewById(R.id.edtDiary);

        writeDay = (TextView) findViewById(R.id.writeDay);
        Intent intent = getIntent();
        year = intent.getStringExtra("years");
        monthday = intent.getStringExtra("monthdays");
        writeDay.setText(year + " " + monthday);
        fnames = writeDay.getText().toString();
        fnames = fnames.replaceAll("[^0-9]",""); // 한글 제외(정규표현식)
        fileName = fnames + ".txt";
        String str = readDiary(fileName);
        edtDiary.setText(str);
    }

    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[inFs.available()];
            inFs.read(txt);

            diaryStr = (new String(txt)).trim();
            inFs.close();
            if(diaryStr.equals("")) { // 일기가 없을 때
            } else { // 있을 때
                setTitle("일기 읽기 & 수정");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diaryStr;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveMenu:
                saveDiary(fileName);
                return true;
        }
        return false;
    }

    // 일기 저장하는 메소드
    private void saveDiary(String fileName) {

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(fileName, MODE_NO_LOCALIZED_COLLATORS); //MODE_WORLD_WRITEABLE
            String content = edtDiary.getText().toString();

            fos.write(content.getBytes());
            fos.flush();
            fos.close();

            Toast.makeText(getApplicationContext(), "일기를 저장했습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } catch (Exception e) { // Exception - 에러 종류 제일 상위 // FileNotFoundException , IOException
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류가 발생하였습니다!", Toast.LENGTH_SHORT).show();
        }
    }
}
