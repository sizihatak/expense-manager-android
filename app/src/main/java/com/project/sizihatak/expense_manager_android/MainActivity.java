package com.project.sizihatak.expense_manager_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            getFragmentManager().beginTransaction()
                    .add(R.id.main_activity_container, new ListCostFragment())
                    .commit();
    }

}
