package com.ehang.commonutils;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.ehang.commonutils.io.FileUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.ehang.commonutils.test", appContext.getPackageName());
    }

    @Test
    public void testProperties() {
        FileUtils.parseProperties(new File(InstrumentationRegistry.getTargetContext().getFilesDir(), "abc"));
    }
}
