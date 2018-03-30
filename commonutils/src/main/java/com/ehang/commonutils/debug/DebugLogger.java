/**
*****************************************************************************
* Copyright (C) 2005-2014 UCWEB Corporation. All rights reserved
* File        : DebugLog.java
* Description : 调试日志工具类
*
* Creation    : 2014-07-03
* Author      : weijj@ucweb.com
* History     : Creation, 2014-07-03, weijunjie, Create the file
*****************************************************************************
**/

package com.ehang.commonutils.debug;

import android.text.TextUtils;


import com.ehang.commonutils.codec.CodecUtil;
import com.ehang.commonutils.exception.ExceptionHandler;
import com.ehang.commonutils.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class DebugLogger {

    private String mLogPath = "/mnt/sdcard/";

    private String mLogFileName = "debuglog.txt";

    private File mLogFile;

    private ArrayList<String> mLogList;


    private SimpleDateFormat mTimeFormat;

    private int mMaxLinesCount = 20;

    public DebugLogger() {
        mLogList = new ArrayList<String>();
        mTimeFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]<<<< ");
    }

    public void setLogPathAndFileName(String path, String fileName) {
        if(!TextUtils.isEmpty(path)) {
            mLogPath = path;
            if(mLogPath.charAt(path.length()-1) != '/'){
                mLogPath = path + "/";
            }
        }

        if(!TextUtils.isEmpty(fileName)) {
            mLogFileName = fileName;
        }
    }

    /**
     * set log auto save condition
     * @param maxLines : 1. if log line count equal or greater than maxLines,
     *                      when call writeLog method will cause the method flush to be called.
     *                   2. mostly, the value can be set equal or greater than 0, if the value
     *                      is equal or less than 0, call writeLog method will not cause the
     *                      method flush to be called.
     */
    public void setMaxLinesForFlush(int maxLines){
        mMaxLinesCount = maxLines;
    }

    @SuppressWarnings("unchecked")
	public void writeLog(Object msg) {
    	if( msg instanceof String) {
        	writeLog( (String)msg);
		}else if( msg instanceof HashMap) {
			writeLog( (HashMap<String, String>)msg );
		}else if( msg instanceof byte[]) {
			writeLog( (byte[])msg );
		}else {
			writeLog("not supported type data:" + (null != msg ? msg : ""));
		}
    }
    
    public void writeLog(String msg) {

        if(null == msg) {
            return;
        }

        String prefix = mTimeFormat.format(System.currentTimeMillis());
        mLogList.add(prefix+msg);

        if(mMaxLinesCount > 0 && mLogList.size() >= mMaxLinesCount){
            flush();
        }
    }

    public void writeLog(byte[] data) {

        StringBuilder msg = new StringBuilder();
        msg.append("byte[] data, len=");
        if( null != data && data.length > 0){
            msg.append( data.length + " bytes, content: ");
            msg.append(CodecUtil.base64Encode2String(data));
        }else{
            msg.append("0 bytes, content: (emtpy)");
        }

        writeLog(msg.toString());
    }

    /**
     * write a map object to log
     * @param map,
     *     Note: if this method is call in multi-thread, map must be thread-safe by caller ensure,
     *                so,  a way is you can pass a clone of map.
     */
    public void writeLog(HashMap<String, String> map) {

        StringBuilder msg = new StringBuilder();
        msg.append("map data, size=");
         if( null != map && !map.isEmpty()){
             msg.append(map.size() + ", content: ");
             for(Entry<String, String> entry : map.entrySet()){
                 msg.append("" + entry.getKey() + ":" + entry.getValue());
                 msg.append(", ");
             }
         }else{
             msg.append("0, content: (empty)");
         }
        
         writeLog(msg.toString());
    }

    public void flush() {

        if(null == mLogFile){
            try {
                mLogFile = FileUtils.createNewFile(mLogPath + mLogFileName, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(null == mLogFile){
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
