package com.tuke.demo.widgets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CombinationImageView imageView = findViewById(R.id.combination_image_view);
        imageView.setImageUri(new int[]{R.drawable.login_default_icon, R.drawable.login_default_icon, R.mipmap.ic_launcher_round});
    }
}
