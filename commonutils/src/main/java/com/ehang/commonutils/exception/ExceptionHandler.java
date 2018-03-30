/**
 * ******************************************************************
 * Copyright (C) 2005-2014 UC Mobile Limited. All Rights Reserved
 * File        : ExceptionHandler.java
 * Description : Providing visible messages to DEBUG users when an
 * exception occurs, via a Toast for normal exceptions
 * and a Dialogue for the fatal ones.
 * Creation    : 2014/08/08
 * Author      : liaofei@ucweb.com
 * History     :
 * Creation, 2014/08/08, liaofei, Create the file
 * ******************************************************************
 **/
package com.ehang.commonutils.exception;


import android.util.Log;

/**
 * 异常处理，直接打印异常消息，直接会根据类型来判断是打印消息，还是显示dialog等
 *
 * @author neofane
 * @since 9.9.3
 */
final public class ExceptionHandler {

    /**
     * Process a silent exception by just printing the stack trace.
     *
     * @param t the exception object
     */
    public static final void processSilentException(final Throwable t) {
        Log.i("gzm_ExceptionHandler", "processSilentException: ", t);
    }

    /**
     * Process a harmless exception by showing a Toast.
     *
     * @param t the exception object
     */
    public static final void processHarmlessException(final Throwable t) {
        t.printStackTrace();
    }


    /**
     * Process an fatal exception by showing a Dialogue.
     *
     * @param t the exception object
     */
    public static final void processFatalException(final Throwable t) {
        t.printStackTrace();
    }

}


