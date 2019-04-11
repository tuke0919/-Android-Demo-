package com.yingke.annotationprocessordemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yingke.annotationprocessordemo.factory.MealFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MealFactory mealFactory = new MealFactory();
        mealFactory.create("Tiramisu");
    }
}
