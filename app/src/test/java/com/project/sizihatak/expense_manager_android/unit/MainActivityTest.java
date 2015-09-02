package com.project.sizihatak.expense_manager_android.unit;

import com.project.sizihatak.expense_manager_android.BuildConfig;
import com.project.sizihatak.expense_manager_android.CustomRobolectricRunner;
import com.project.sizihatak.expense_manager_android.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(CustomRobolectricRunner.class)
@Config(constants = BuildConfig.class, sdk=21)
public class MainActivityTest {

    MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void testActivityRun() throws Exception {
        assertNotNull(activity);
    }

}