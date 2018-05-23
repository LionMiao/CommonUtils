
package com.ehang.commonutils.debug;

import android.text.TextUtils;

import com.ehang.commonutils.codec.CodecUtil;
import com.ehang.commonutils.exception.ExceptionHandler;
import com.ehang.commonutils.io.FileUtils;
import com.ehang.commonutils.ui.TomApplication;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

/**
 * 保存字符串、byte数组、或者HashMap到文件中，自定义缓存的日志数量，待超过缓存条数时会自动写入文件。
 *
 * @author tom
 */
public class DebugLogger {
    /**
     * 默认的文件路径
     */
    @SuppressWarnings("ConstantConditions")
    private String mLogPath = TomApplication.getContext().getExternalFilesDir("log").getAbsolutePath() + "/";
    /**
     * 默认的文件名
     */
    private String mLogFileName = "log.txt";
    /**
     * 当前正在写入日志的文件
     */
    private File mLogFile;
    /**
     * 缓存{@link DebugLogger#mMaxLinesCount}条数量的log，一次性写入到文件
     */
    private ArrayList<String> mLogList;
    /**
     * 缓存多少条日志后再一次写入文件
     */
    private int mMaxLinesCount = 20;

    public DebugLogger() {
        mLogList = new ArrayList<>();
        mLogFileName = "log_" + new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date()) + ".txt";
    }

    /**
     * 设置日志路径和日志文件名
     */
    public void setLogPathAndFileName(String path, String fileName) {
        if (!TextUtils.isEmpty(path)) {
            mLogPath = path;
            if (mLogPath.charAt(path.length() - 1) != '/') {
                mLogPath = path + "/";
            }
        }
        if (!TextUtils.isEmpty(fileName)) {
            mLogFileName = fileName;
        }
    }

    /**
     * set log auto save condition
     *
     * @param maxLines : 1. if log line count equal or greater than maxLines,
     *                 when call writeLog method will cause the method flush to be called.
     *                 2. mostly, the value can be set equal or greater than 0, if the value
     *                 is equal or less than 0, call writeLog method will not cause the
     *                 method flush to be called.
     */
    public void setMaxLinesForFlush(int maxLines) {
        mMaxLinesCount = maxLines;
    }

    /**
     * 写日志，支持string、HashMap、byte[]
     */
    @SuppressWarnings("unchecked")
    public void writeLog(Object msg) {
        if (msg instanceof String) {
            writeLog((String) msg);
        } else if (msg instanceof HashMap) {
            writeLog((HashMap<String, String>) msg);
        } else if (msg instanceof byte[]) {
            writeLog((byte[]) msg);
        } else {
            writeLog("not supported type data:" + (null != msg ? msg : ""));
        }
    }

    /**
     * 写入字符串
     */
    public void writeLog(String msg) {
        if (null == msg) {
            return;
        }
        String prefix = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ", Locale.CHINA).format(System.currentTimeMillis());
        mLogList.add(prefix + msg);
        if (mMaxLinesCount > 0 && mLogList.size() >= mMaxLinesCount) {
            flush();
        }
    }

    /**
     * 写入byte数组
     */
    public void writeLog(byte[] data) {
        StringBuilder msg = new StringBuilder();
        msg.append("byte[] data, len=");
        if (null != data && data.length > 0) {
            msg.append(data.length).append(" bytes, content: ").append(CodecUtil.base64Encode2String(data));
        } else {
            msg.append("0 bytes, content: (empty)");
        }
        writeLog(msg.toString());
    }

    /**
     * write a map object to log
     *
     * @param map, Note: if this method is call in multi-thread, map must be thread-safe by caller ensure,
     *             so,  a way is you can pass a clone of map.
     */
    public void writeLog(HashMap<String, String> map) {

        StringBuilder msg = new StringBuilder();
        msg.append("map data, size=");
        if (null != map && !map.isEmpty()) {
            msg.append(map.size()).append(", content: ");
            for (Entry<String, String> entry : map.entrySet()) {
                msg.append(entry.getKey()).append(":").append(entry.getValue()).append(", ");
            }
        } else {
            msg.append("0, content: (empty)");
        }

        writeLog(msg.toString());
    }

    /**
     * 将缓存中的日志写入文件
     */
    public void flush() {
        if (null == mLogFile) {
            try {
                mLogFile = FileUtils.createNewFile(mLogPath + mLogFileName, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null == mLogFile) {
            return;
        }
        try {
            FileUtils.writeTextFile(mLogFile, mLogList, true);
            mLogList.clear();
        } catch (Throwable e) {
            ExceptionHandler.processFatalException(e);
        }
    }
}
