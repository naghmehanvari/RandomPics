package com.example.naghmeh.randompics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SavedImageList extends AppCompatActivity {
    DBHandler db;
    List<String> imgList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_image_list);
        db = new DBHandler(this);
        imgList = (ArrayList) db.getAllImages();

        setListAdapter();
    }

    private void setListAdapter() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                imgList);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
