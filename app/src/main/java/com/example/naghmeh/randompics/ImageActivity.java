package com.example.naghmeh.randompics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    DBHandler db;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        db = new DBHandler(this);

        url = getIntent().getStringExtra("image");

        ImageView iv = (ImageView) findViewById(R.id.imageView);

        Picasso.with(this)
                .load(url)
                .into(iv);
    }

    public void saveImage(View view) {

        db.addImage(new Image(1, url));
        Toast.makeText(ImageActivity.this, "Saved", Toast.LENGTH_SHORT).show();

    }
}
