package com.prosoft.todaydiary;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class DiaryList extends AppCompatActivity {
    ListView listView;
    ListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.diaryList);
        adapter = new ListItemAdapter();

        adapter.addItem(new ListItem("2022-11-13", "#FF22FF", "안녕하세요!"));
        adapter.addItem(new ListItem("2022-11-12", "#000000", "안녕하세요! 2"));
        adapter.addItem(new ListItem("2022-11-11", "#FFFFFF", "안녕하세요! 3"));
        listView.setAdapter(adapter);
    }
}
