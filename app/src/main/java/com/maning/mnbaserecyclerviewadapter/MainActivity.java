package com.maning.mnbaserecyclerviewadapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn01(View view){
        startActivity(new Intent(this, SingleTypeActivity.class));
    }

    public void btn02(View view){
        startActivity(new Intent(this, MuchTypeActivity.class));
    }


}
