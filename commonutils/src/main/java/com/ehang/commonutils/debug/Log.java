package com.ehang.commonutils.debug;

/**
 * UC 自定义log, 可以根据打包开关设置log 使用 文件log的时候需要注意, log 会自动保存到文件当中,但是
 */
public class Log {
    /**
     * Log级别定义
     */
    public final static int LOG_VERBOSE = 0; // verbose级别
    public final static int LOG_DEBUG = 1; // debug级别
    public final static int LOG_INFO = 2; // info 级别
    public final static int LOG_WARNING = 3; // warning 级别
    public final static int LOG_ERROR = 4; // error级别

    /**
     * Log 输出的方式
     */
    public final static int TYPE_LOG_TO_LOGCAT = 1;
    public final static int TYPE_LOG_TO_FILE = 2;


    private static int logType = TYPE_LOG_TO_LOGCAT;
    private static int logLevel = LOG_VERBOSE;

    private static byte[] mLock = new byte[0];
    private static DebugLogger mLogger;

    /**
     * 获取log输出位置
     */
    public static int getLogType() {
        return logType;
    }

    /**
     * 设置log输出的位置,0:关闭，1：输出到控制台，2：输出到文件，3：同时输出到文件和控制台
     */
    public static void setLogType(int logType) {
        Log.logType = logType;
        if (TYPE_LOG_TO_FILE == (TYPE_LOG_TO_FILE & getLogType())) {
            mLogger = new DebugLogger();
        }
    }

    /**
     * 设置log的输出级别
     *
     * @param logLevel {@link Log#LOG_VERBOSE}
     *                 {@link Log#LOG_DEBUG}
     *                 {@link Log#LOG_INFO}
     *                 {@link Log#LOG_WARNING}
     *                 {@link Log#LOG_ERROR}
     */
    public static void setLogLevel(int logLevel) {
        Log.logLevel = logLevel;
    }

    /**
     * 获取log输出级别
     */
    public static int getLogLevel() {
        return logLevel;
    }

    /**
     * 设置日志路径和日志文件名
     */
    public void setLogPathAndFileName(String path, String fileName) {
        if (null == mLogger) {
            mLogger = new DebugLogger();
        }
        mLogger.setLogPathAndFileName(path, fileName);
    }

    /**
     * 将Log写到文件当中
     */
    private synchronized static void writeToFile(String strLog) {
        if (null == mLogger) {
            mLogger = new DebugLogger();
        }
        mLogger.writeLog(strLog);
    }

    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg) {
        v(tag, msg, null);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (null == msg || getLogLevel() > LOG_VERBOSE) {
            return;
        }
        if (TYPE_LOG_TO_FILE == (getLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile((tag == null ? "CommonUtils" : tag) + "   " + msg + (tr == null ? "" : ("\n" + android.util.Log.getStackTraceString(tr))));
        }
        if (TYPE_LOG_TO_LOGCAT == (getLogType() & TYPE_LOG_TO_LOGCAT)) {
            android.util.Log.println(android.util.Log.VERBOSE, tag == null ? "CommonUtils" : tag, msg + (tr == null ? "" : ("\n" + android.util.Log.getStackTraceString(tr))));
        }
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (null == msg || getLogLevel() > LOG_DEBUG) {
            return;
        }
        if (TYPE_LOG_TO_FILE == (getLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile((tag == null ? "CommonUtils" : tag) + "   " + msg + (tr == null ? "" : ("\n" + android.util.Log.getStackTraceString(tr))));
        }
        if (TYPE_LOG_TO_LOGCAT == (getLogType() & TYPE_LOG_TO_LOGCAT)) {
            android.util.Log.println(android.util.Log.DEBUG, tag == null ? "CommonUtils" : tag, msg + (tr == null ? "" : ("\n" + android.util.Log.getStackTraceString(tr))));
        }
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        i(tag, msg, null);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (null == msg || getLogLevel() > LOG_INFO) {
            return;
        }
        if (TYPE_LOG_TO_FILE == (getLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile((tag == null ? "CommonUtils" : tag) + "   " + msg + (tr == null ? "" : ("\n" + android.util.Log.getStackTraceString(tr))));
        }
        if (TYPE_LOG_TO_LOGCAT == (getLogType() & TYPE_LOG_TO_LOGCAT)) {
            android.util.Log.println(android.util.Log.INFO, tag == null ? "CommonUtils" : tag, msg + (tr == null ? "" : ("\n" + android.util.Log.getStackTraceString(tr))));
        }
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (null == msg || getLogLevel() > LOG_WARNING) {
            return;
        }
        if (TYPE_LOG_TO_FILE == (getLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile((tag == null ? "CommonUtils" : tag) + "   " + msg + (tr == null ? "" : ("\n" + android.util.Log.getStackTraceString(tr))));
        }
        if (TYPE_LOG_TO_LOGCAT == (getLogType() & TYPE_LOG_TO_LOGCAT)) {
            android.util.Log.println(android.util.Log.WARN, tag == null ? "CommonUtils" : tag, msg + (tr == null ? "" : ("\n" + android.util.Log.getStackTraceString(tr))));
        }
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(Throwable tr) {
        e(null, android.util.Log.getStackTraceString(tr));
    }

    public static void e(String msg, Throwable tr) {
        e(null, msg, tr);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (null == msg || getLogLevel() > LOG_ERROR) {
            return;
        }
        if (TYPE_LOG_TO_FILE == (getLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile((tag == null ? "CommonUtils" : tag) + "   " + msg + (tr == null ? "" : ("\n" + android.util.Log.getStackTraceString(tr))));
        }
        if (TYPE_LOG_TO_LOGCAT == (getLogType() & TYPE_LOG_TO_LOGCAT)) {
            android.util.Log.println(android.util.Log.ERROR, tag == null ? "CommonUtils" : tag, msg + (tr == null ? "" : ("\n" + android.util.Log.getStackTraceString(tr))));
        }
    }

    public static void a(String msg) {
        a(null, msg);
    }

    public static void a(String tag, String msg) {
        if (null == msg) {
            return;
        }
        if (TYPE_LOG_TO_FILE == (getLogType() & TYPE_LOG_TO_FILE)) {
            writeToFile((tag == null ? "CommonUtils" : tag) + "   " + msg);
        }

        if (TYPE_LOG_TO_LOGCAT == (getLogType() & TYPE_LOG_TO_LOGCAT)) {
            android.util.Log.println(android.util.Log.ASSERT, tag == null ? "CommonUtils" : tag, msg);
        }
    }
}
