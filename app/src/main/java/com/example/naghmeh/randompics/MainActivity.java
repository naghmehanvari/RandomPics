package com.example.naghmeh.randompics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    final Context context = this;
    private String userinputResult;
    private String[] listOfImageUrl = new String[10];
    private ArrayList<String> listOfImgUrlBySearch= new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        randomImage();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== getResources().getIdentifier("action_newPics","id" ,getPackageName()))
            getRandomSet();
        if (item.getItemId()== getResources().getIdentifier("action_search","id" ,getPackageName()))
            search();
        if (item.getItemId()== getResources().getIdentifier("action_saved","id" ,getPackageName()))
            getSavedImages();
        return super.onOptionsItemSelected(item);
    }

    private void getSavedImages() {
        Intent showSaved = new Intent(this, SavedImageList.class);
        startActivity(showSaved);
    }

    private void handleDialog(){
        userinputResult = "";
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_query, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.query);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                userinputResult = userInput.getText().toString();
                                dialog.dismiss();
                                jsonForSearch();
                            }
                        });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();


    }
    private void search() {

        handleDialog();

    }

    public void jsonForSearch()
    {
        Ion.with(this)
                .load("https://api.unsplash.com/search/photos/?client_id=68e328541b5e15a796d0105cea5cb09faa9121d67bfbd42d5cf46af52624c612&query="+userinputResult)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        processSearch(result);
                    }
                });

    }
    public void processSearch(String result)
    {

        try {
            listOfImgUrlBySearch.clear();
            JSONObject json = new JSONObject(result);
            Log.i("randomImgUrl",json.toString());
            JSONArray jsonArray = json.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonn = jsonArray.getJSONObject(i);
                JSONObject urls = jsonn.getJSONObject("urls");
                String randomImgUrl = urls.getString("regular");

                listOfImgUrlBySearch.add(randomImgUrl);
            }
            Intent searchResult = new Intent(this, SearchResultList.class);
            searchResult.putStringArrayListExtra("array", listOfImgUrlBySearch);
            startActivity(searchResult);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getRandomSet() {
        this.recreate();
    }

    public void randomImage()
    {
        Ion.with(this)
                .load("https://api.unsplash.com/photos/random/?client_id=68e328541b5e15a796d0105cea5cb09faa9121d67bfbd42d5cf46af52624c612&count=10")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        processData(result);
                    }
                });
    }

    public void processData(String result)
    {
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject json = jsonArray.getJSONObject(i);
                JSONObject urls = json.getJSONObject("urls");
                String randomImgUrl = urls.getString("regular");
                listOfImageUrl[i] = randomImgUrl;
            }
            setImageButtons();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setImageButtons()
    {
        for(int i = 0; i < listOfImageUrl.length;i++)
        {
            String imgName = "img" + (i+1);
            int id = getResources().getIdentifier(imgName, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(id);
            iv.setTag(listOfImageUrl[i]);
            Picasso.with(this)
                    .load(listOfImageUrl[i])
                    .fit()
                    .into(iv);
        }
    }

    public void showDetail(View view) {
        Intent imgIntent = new Intent(this, ImageActivity.class);
        ImageView iv = (ImageView) view;
        imgIntent.putExtra("image", (String) iv.getTag());
        startActivity(imgIntent);

    }
}
