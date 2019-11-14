package com.kq.platform.kq_common;

import android.util.Log;

import com.kq.platform.kq_common.utils.cipher.Base64;

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
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testBase64() throws Exception{
        String user = Base64.encode("123".getBytes());
        Log.i("asdf",user);
    }
}