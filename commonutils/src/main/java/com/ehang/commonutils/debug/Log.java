/**
 * ******************************************************************
 * Copyright (C) 2005-2014 UC Mobile Limited. All Rights Reserved
 * File        : Log.java
 * Description : API for sending log output. Instead of android.util.Log,
 * this provides on/off switch at runtime.
 * Creation    : 2014/10/13
 * Author      : hejy3@ucweb.com
 * History     :
 * Creation, 2014/10/13, Young, Create the file
 *******************************************************************/

package com.ehang.commonutils.debug;

import android.os.Environment;


import com.ehang.commonutils.config.ShellFeatureConfig;

import java.io.File;

/**
 * UC 自定义log, 可以根据打包开关设置log 使用 文件log的时候需要注意, log 会自动保存到文件当中,但是
 */
public class Log {

    /**
     * Log级别定义
     */
    final private static int LOG_VERBOSE = 0; // verbose级别
    final private static int LOG_DEBUG = 1; // debug级别
    final private static int LOG_INFO = 2; // info 级别
    final private static int LOG_WARNING = 3; // warning 级别
    final private static int LOG_ERROR = 4; // error级别

    /**
     * Log 输出的方式
     */
    final private static int TYPE_LOG_TO_LOGCAT = 1;
    final private static int TYPE_LOG_TO_FILE = 2;

    private static byte[] mLock = new byte[0];
    private static DebugLogger mLogger;

    /**
     * 获取log 的类型
     *
     * @return
     */
    public static int GetLogType() {
        return ShellFeatureConfig.ENABLE_JAVE_LOG_TYPE;
    }

    /**
     * 设置log 的类型
     *
     * @param logType
     */
    public static void SetLogtype(int logType) {
        ShellFeatureConfig.ENABLE_JAVE_LOG_TYPE = logType;
        init();
    }

    /**
     * 将Log写到文件当中
     *
     * @param strLog
     */
    private static void writeToFile(String strLog) {
        synchronized (mLock) {
            if (null == mLogger) {
                initFileLog("ucm");
            }
            mLogger.writeLog(strLog);
        }
    }

    /**
     * 初始化文件 log
     *
     * @param fileName 初始化的文件名
     * @remark 默认会 保存到 sdcard/UCDownloads/ucm.log 中
     */
    private static void initFileLog(String fileName) {

        mLogger = new DebugLogger();
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/UCDownloads/";
        File file = new File(filePath + fileName);
        int index = 0;
        while (file.exists()) {
            fileName = fileName + index;
            file = new File(filePath + fileName);
            index++;
        }
        mLogger.setLogPathAndFileName(filePath, fileName + ".log");
        mLogger.setMaxLinesForFlush(50);

    }

    /**
     * 如果需要输出到文件, 则需要初始化
     */
    public static void init() {
        if (TYPE_LOG_TO_FILE == (TYPE_LOG_TO_FILE & GetLogType())) {
            initFileLog("ucm");
        }
    }

    /**
     * 获取当前Log 级别
     *
     * @return
     */
    public static int GetLogLevel() {
        return ShellFeatureConfig.JAVA_LOG_LEVEL;
    }

    public static void SetLogLevel(int level) {
        ShellFeatureConfig.JAVA_LOG_LEVEL = level;
    }

    public static String getStackTraceString(Throwable tr) {
        return android.util.Log.getStackTraceString(tr);
    }

    public static int v(String tag, String msg) {
        int res = 0;

        if (null == tag || null == msg) {
            return 0;
        }
        if (GetLogLevel() > LOG_VERBOSE) {
            return 0;
        }

        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg);

        }

        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.VERBOSE, tag, msg);
        }
        return res;
    }

    public static int v(String tag, String msg, Throwable tr) {
        int res = 0;

        if (null == tag || null == msg) {
            return 0;
        }
        if (GetLogLevel() > LOG_VERBOSE) {
            return 0;
        }
        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg + "\n" + android.util.Log.getStackTraceString(tr));

        }

        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.VERBOSE, tag, msg + '\n' + getStackTraceString(tr));
        }
        return res;
    }

    public static int d(String tag, String msg) {

        int res = 0;
        if (null == tag || null == msg) {
            return 0;
        }
        if (GetLogLevel() > LOG_DEBUG) {
            return 0;
        }
        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg);

        }

        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.DEBUG, tag, msg);
        }
        return res;
    }

    public static int d(String tag, String msg, Throwable tr) {
        int res = 0;
        if (null == tag || null == msg) {
            return 0;
        }
        if (GetLogLevel() > LOG_DEBUG) {
            return 0;
        }
        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg + "\n" + android.util.Log.getStackTraceString(tr));

        }
        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.DEBUG, tag, msg + '\n' + getStackTraceString(tr));
        }
        return res;
    }

    public static int i(String tag, String msg) {
        int res = 0;
        if (null == tag || null == msg) {
            return 0;
        }
        if (GetLogLevel() > LOG_INFO) {
            return 0;
        }

        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg);

        }
        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.INFO, tag, msg);
        }
        return res;
    }

    public static int i(String tag, String msg, Throwable tr) {
        int res = 0;
        if (null == tag || null == msg) {
            return 0;
        }
        if (GetLogLevel() > LOG_INFO) {
            return 0;
        }
        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg + "\n" + android.util.Log.getStackTraceString(tr));

        }
        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.INFO, tag, msg + '\n' + getStackTraceString(tr));
        }
        return res;
    }

    public static int w(String tag, String msg) {
        int res = 0;
        if (null == tag || null == msg) {
            return 0;
        }
        if (GetLogLevel() > LOG_WARNING) {
            return 0;
        }
        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg);
        }
        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.WARN, tag, msg);
        }
        return res;
    }

    public static int w(String tag, Throwable tr) {
        int res = 0;
        if (null == tag) {
            return 0;
        }
        if (GetLogLevel() > LOG_WARNING) {
            return 0;
        }
        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag);
        }
        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.WARN, tag, '\n' + getStackTraceString(tr));
        }
        return res;
    }

    public static int w(String tag, String msg, Throwable tr) {
        int res = 0;
        if (null == tag || null == msg) {
            return 0;
        }
        if (GetLogLevel() > LOG_WARNING) {
            return 0;
        }
        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg + "\n" + android.util.Log.getStackTraceString(tr));
        }
        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.WARN, tag, msg + '\n' + getStackTraceString(tr));
        }
        return res;
    }

    public static int e(String tag, String msg) {
        int res = 0;
        if (null == tag || null == msg) {
            return 0;
        }
        if (GetLogLevel() > LOG_ERROR) {
            return 0;
        }
        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg);
        }

        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.ERROR, tag, msg);
        }
        return res;
    }

    public static int e(String tag, String msg, Throwable tr) {
        int res = 0;
        if (null == tag || null == msg) {
            return 0;
        }
        if (GetLogLevel() > LOG_ERROR) {
            return 0;
        }
        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg + '\n' + android.util.Log.getStackTraceString(tr));
        }
        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.ERROR, tag, msg + '\n' + getStackTraceString(tr));
        }
        return res;
    }

    public static int a(String tag, String msg) {
        int res = 0;
        if (null == tag || null == msg) {
            return 0;
        }

        if (TYPE_LOG_TO_FILE == (GetLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile(tag + "   " + msg);
        }

        if (TYPE_LOG_TO_LOGCAT == (GetLogType() & TYPE_LOG_TO_LOGCAT)) {
            res = android.util.Log.println(android.util.Log.ASSERT, tag, msg);
        }
        return res;
    }


}
