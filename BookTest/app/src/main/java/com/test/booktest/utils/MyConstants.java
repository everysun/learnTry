package com.test.booktest.utils;

import android.os.Environment;

public class MyConstants {

    public static final String TEST_PATH = Environment.getExternalStorageDirectory().getPath()
            + "/test/testBook/";

    public static final String CACHE_FILE_PATH = TEST_PATH + "usercache";
    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVICE = 1;
}
