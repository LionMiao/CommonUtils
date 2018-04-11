package com.ehang.commonutils.exception;

/**
 * 空参数异常，统一对空参数异常进行处理
 */
public class NullArgumentException extends IllegalArgumentException {

    private static final long serialVersionUID = 1074360235354917592L;

    /**
     * <p>空参数异常</p>
     *
     * @param argName 为空的参数
     */
    public NullArgumentException(String argName) {
        super((argName == null ? "Argument" : argName) + " must not be null.");
    }
}