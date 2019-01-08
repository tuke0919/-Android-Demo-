package com.tuke.surfuaceviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button mDraw;
    Button mWhite;
    Button mGray;
    Button mBlue;

    CustomSurfaceView01 customSurfaceView01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_01);
        initViews();
    }

    public void initViews(){
        mDraw = findViewById(R.id.button_draw);
        mWhite = findViewById(R.id.button_white);
        mGray = findViewById(R.id.button_gray);
        mBlue = findViewById(R.id.button_blue);

        mDraw.setOnClickListener(this);
        mWhite.setOnClickListener(this);
        mGray.setOnClickListener(this);
        mBlue.setOnClickListener(this);

        customSurfaceView01 =  findViewById(R.id.surfaceview01);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_draw :
                customSurfaceView01.draw();
                break;
            case R.id.button_white:
                customSurfaceView01.fillWhiteColor();
                break;
            case R.id.button_gray:
                customSurfaceView01.fillGrayColor();
                break;
            case R.id.button_blue:
                customSurfaceView01.fillBlueColor();
                break;
        }
    }
}
