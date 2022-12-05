package com.prosoft.todaydiary;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class DiaryList extends AppCompatActivity {
    ListView listView;
    ListItemAdapter adapter;

    ArrayList<String> diaryList;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("일기 목록");
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MODE_PRIVATE);

        diaryList = new ArrayList<String>();
        listView = findViewById(R.id.diaryList);
        adapter = new ListItemAdapter();

        File fp = getFilesDir();
        if(fp.exists() == false){
            return;
        }
        File[] files = fp.listFiles();
        String fileName, extName;
        String fYear, fMonth, fDay;
        for(int i = 0; i < files.length; i++) {
            if (files[i].getName().equals("password.txt") ||  files[i].getName().equals("hint.txt") || files[i].getName().equals("name.txt")) {
                continue;
            } else {
                if (!files[i].isHidden() && files[i].isFile()) {
                    fileName = files[i].getName();
                    extName = fileName;
                    fileName = fileName.replaceAll(".txt", "");
                    if(fileName.length()==7){
                        fYear = fileName.substring(0,4);
                        fMonth = fileName.substring(4,6);
                        fDay = fileName.substring(6,7);
                    } else {
                        fYear = fileName.substring(0,4);
                        fMonth = fileName.substring(4,6);
                        fDay = fileName.substring(6,8);
                    }

                    try{
                        FileInputStream inFs = openFileInput(extName);
                        byte[] txt = new byte[60];
                        inFs.read(txt);
                        String str = new String(txt);
                        adapter.addItem(new ListItem(fYear + "년 " + fMonth + "월 " + fDay + "일", str));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListItem selectedItem = (ListItem) adapter.getItem(position);
                String datas = selectedItem.getName();
                Toast.makeText(getApplicationContext(), datas, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                if(datas.length()==13) {
                    intent.putExtra("years", datas.substring(0, 5));
                    intent.putExtra("monthdays", datas.substring(6, 13));
                    startActivity(intent);
                } else {
                    intent.putExtra("years", datas.substring(0, 5));
                    intent.putExtra("monthdays", datas.substring(6, 12));
                    startActivity(intent);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView parent, View v, int position, long id){
                ListItem selectedItem = (ListItem) adapter.getItem(position);
                String datas = selectedItem.getName();
                String datasName = datas.substring(0,4) + datas.substring(6,8) + datas.substring(10,datas.length()-1) + ".txt";
                AlertDialog.Builder builder = new AlertDialog.Builder(DiaryList.this);
                builder.setTitle("일기 삭제").setMessage("정말로 일기를 삭제하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "일기를 삭제하였습니다!", Toast.LENGTH_SHORT).show();
                        deleteFile(datasName);
                        diaryList.remove(position);
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소하셨습니다!", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }
}
