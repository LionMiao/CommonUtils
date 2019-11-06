package com.ehang.commonutils;

import com.ehang.commonutils.codec.CodecUtil;
import com.ehang.commonutils.debug.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        String origin = System.currentTimeMillis() + "EHang" + "EH12345678";
//        Log.d(origin);
        assertEquals(4, CodecUtil.MD5("1565666021EHangEH12345678".getBytes()));
    }
}