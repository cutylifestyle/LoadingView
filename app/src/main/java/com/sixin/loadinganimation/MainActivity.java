package com.sixin.loadinganimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sixin.loadinganimation.view.LoadingView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadingView loadingView = (LoadingView) findViewById(R.id.loadingView);

    }

}
