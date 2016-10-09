package com.example.naghmeh.randompics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchResultList extends AppCompatActivity {

    ArrayList<String> imagelst;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_list);

        imagelst = getIntent().getStringArrayListExtra("array");
        listView = (ListView) findViewById(R.id.listOfSearchedImg);
        listView.setAdapter(new ImageListAdapter(this, R.layout.list_item, imagelst));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String theUrl = (String) listView.getItemAtPosition(position);
                Intent imgIntent = new Intent(SearchResultList.this, ImageActivity.class);
                imgIntent.putExtra("image", theUrl);
                startActivity(imgIntent);
            }
        });

    }
}
