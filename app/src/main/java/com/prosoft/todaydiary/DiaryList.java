package com.prosoft.todaydiary;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.ArrayList;

public class DiaryList extends AppCompatActivity {
    ListView listView;
    ListItemAdapter adapter;

    ArrayList<String> diaryList;
    String selectedDiary;

    String diaryPath = Environment.getExternalStorageDirectory().getPath() + "/";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("일기 목록");
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MODE_PRIVATE);

        diaryList = new ArrayList<String>();

//        File[] listFiles = new File(diaryPath);
//        String fileName, extName;
//        for (File file : listFiles) {
//            fileName = file.getName();
//            extName = fileName.substring(fileName.length() - 3);
//            if (extName.equals((String) "txt"))
//                diaryList.add(fileName);
//        }

        listView = findViewById(R.id.diaryList);
        adapter = new ListItemAdapter();

        adapter.addItem(new ListItem("2022-11-13", "#FF22FF", "안녕하세요!"));
        adapter.addItem(new ListItem("2022-11-12", "#000000", "안녕하세요! 2"));
        adapter.addItem(new ListItem("2022-11-11", "#FFFFFF", "안녕하세요! 3"));
        listView.setAdapter(adapter);
    }
}
