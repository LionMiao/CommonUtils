/*
 * ****************************************************************************
 * Copyright (C) 2005-2012 UCWEB Corporation. All rights reserved
 * Creation    : 2012-11-10
 * Author      : raorq@ucweb.com
 * History     : Creation, 2012-11-10, raorq, Create the file
 * ****************************************************************************
 */
package com.ehang.commonutils.io;

import android.database.Cursor;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;

/**
 * <b>维护IO操作相关的接口</b>
 * <p/>
 * <p>
 * <ol>
 * <li>从一个字节数组的2个字节中中读取一个short整型</li>
 * <li>把一个short整型数据写入一个字节数组的连续2个字节</li>
 * <li>从一个字节数组的2个字节中中读取一个int整型</li>
 * <li>把一个int整型数据写入一个字节数组的连续4个字节</i>
 * <li>从一个字节数组的连续8个字节中读出一个long整型值</li>
 * <li>把一个long整型数据写入一个字节数组的连续8个字节</li>
 * <li>从数据输入流中读取utf字符串对应的字节数组</li>
 * <li>把utf字符串对应的字节数组写入到数据输出流</li>
 * <li>从输入流中读取部份数据(字节数组)</li>
 * <li>从char数组读int数据</li>
 * <li>将int数据写入char数组</li>
 * <li>指字符串的utf字节系列写到字节数组中</li>
 * <li>关闭输入流</li>
 * <li>关闭输出流</li>
 * </ol>
 * </p>
 * <p/>
 */
public final class IOUtil {

    /**
     * <p>从一个字节数组的2个字节中中读取一个short整型</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-22）</li>
     * </ol>
     *
     * @param aSrcArray 源字节数组
     * @param aOffset   读取的偏移值
     * @return 读取的值
     */
    public static int readShort(byte[] aSrcArray, int aOffset) {
        return (aSrcArray[aOffset] & 0xff) << 8 | aSrcArray[aOffset + 1] & 0xff;
    }

    /**
     * <p>把一个short整型数据写入一个字节数组的连续2个字节</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-22）</li>
     * </ol>
     *
     * @param aDestArray 目标数组
     * @param aOffset    写入偏移值
     * @param aValue     写入的short整型值
     */
    public static void writeShort(byte[] aDestArray, int aOffset, short aValue) {
        aDestArray[aOffset] = (byte) ((aValue >> 8) & 0xFF);
        aDestArray[aOffset + 1] = (byte) ((aValue >> 0) & 0xFF);
    }

    /**
     * <p>从一个字节数组的4个字节中读取一个int整型</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-22）</li>
     * </ol>
     *
     * @param aSrcArray 源字节数组
     * @param aOffset   读取的偏移值
     * @return 读取的值
     */
    public static int readInt(byte[] aSrcArray, int aOffset) {
        return (int) (((aSrcArray[aOffset] & 0xff) << 24) + ((aSrcArray[aOffset + 1] & 0xff) << 16) + ((aSrcArray[aOffset + 2] & 0xff) << 8) + ((aSrcArray[aOffset + 3] & 0xff) << 0));
    }

    /**
     * <p>把一个int整型数据写入一个字节数组的连续4个字节</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-22）</li>
     * </ol>
     *
     * @param aDestArray 目标数组
     * @param aOffset    写入偏移值
     * @param aValue     写入的int整型值
     */
    public static void writeInt(byte[] aDestArray, int aOffset, int aValue) {
        aDestArray[aOffset] = (byte) ((aValue >> 24) & 0xFF);
        aDestArray[aOffset + 1] = (byte) ((aValue >> 16) & 0xFF);
        aDestArray[aOffset + 2] = (byte) ((aValue >> 8) & 0xFF);
        aDestArray[aOffset + 3] = (byte) ((aValue >> 0) & 0xFF);
    }

    /**
     * <p>从一个字节数组的连续8个字节中读出一个long整型值</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-22）</li>
     * </ol>
     *
     * @param aSrcArray 源字节数组
     * @param aOffset   读取的偏移值
     * @return 读取到的值
     */
    public static long readLong(byte[] aSrcArray, int aOffset) {
        return (((long) (aSrcArray[aOffset] & 255) << 56) +
                ((long) (aSrcArray[aOffset + 1] & 255) << 48) +
                ((long) (aSrcArray[aOffset + 2] & 255) << 40) +
                ((long) (aSrcArray[aOffset + 3] & 255) << 32) +
                ((long) (aSrcArray[aOffset + 4] & 255) << 24) +
                ((long) (aSrcArray[aOffset + 5] & 255) << 16) +
                ((long) (aSrcArray[aOffset + 6] & 255) << 8) +
                ((long) (aSrcArray[aOffset + 7] & 255) << 0));
    }

    /**
     * <p>把一个long整型数据写入一个字节数组的连续8个字节</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-23）</li>
     * </ol>
     *
     * @param aDestArray 目标数组
     * @param aOffset    写入偏移值
     * @param aValue     写入的long整型值
     */
    public static void writeLong(byte[] aDestArray, int aOffset, long aValue) {
        aDestArray[aOffset + 0] = (byte) (aValue >>> 56);
        aDestArray[aOffset + 1] = (byte) (aValue >>> 48);
        aDestArray[aOffset + 2] = (byte) (aValue >>> 40);
        aDestArray[aOffset + 3] = (byte) (aValue >>> 32);
        aDestArray[aOffset + 4] = (byte) (aValue >>> 24);
        aDestArray[aOffset + 5] = (byte) (aValue >>> 16);
        aDestArray[aOffset + 6] = (byte) (aValue >>> 8);
        aDestArray[aOffset + 7] = (byte) (aValue >>> 0);
    }

    /**
     * <p>从数据输入流中读取utf字符串对应的字节数组</p>
     * 首先读取一个short（即最前面的两个字节），然后再读取长度为该short值的byte数组
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-22）</li>
     * </ol>
     *
     * @param aInput 输入流
     * @return 读取到的值
     * @throws IOException
     */
    public static byte[] readUTFBytes(DataInput aInput) throws IOException {
        if (aInput == null) {
            return null;
        }
        int sUtfLength = aInput.readUnsignedShort();
        byte sByteArray[] = new byte[sUtfLength];
        aInput.readFully(sByteArray, 0, sUtfLength);
        return sByteArray;
    }

    /**
     * <p>把utf字符串对应的字节数组写入到数据输出流</p>
     * 先写入字节数组的长度，然后依次写入整个字节数组
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-23）</li>
     * </ol>
     *
     * @param aOutput   输出流
     * @param aSrcArray 源字节数组
     * @throws Exception
     */
    public static void writeUTFBytes(DataOutput aOutput, byte[] aSrcArray) throws Exception {
        if (aSrcArray != null) {
            aOutput.writeShort(aSrcArray.length);
            aOutput.write(aSrcArray, 0, aSrcArray.length);
        } else {
            //为什么要写入0，直接不写？
            aOutput.writeShort(0);
        }
    }

    /**
     * <p>从输入流中读取部份数据(字节数组)</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-22）</li>
     * </ol>
     *
     * @param aInput        输入流
     * @param aReadLength   读取的长度
     * @param aBufferLength 读数据时的缓冲长度
     * @return
     * @throws IOException
     */
    public static byte[] readBytes(InputStream aInput, int aReadLength, int aBufferLength) throws IOException {
        if (aInput == null || aReadLength <= 0) {
            return null;
        }

        byte[] sData = new byte[aReadLength];
        // mod by raorq 2010-4-19 : 不能灵活控制缓冲区长度
        // aBufferLength = Math.max(aBufferLength,2048);
        if (aBufferLength <= 0) {
            aBufferLength = 2048;
        }
        // mod by raorq 2010-4-19 end
        int sLength = 0;
        for (int i = 0; i < aReadLength; ) {
            if (aReadLength - i < aBufferLength) {
                sLength = aInput.read(sData, i, aReadLength - i);
            } else {
                sLength = aInput.read(sData, i, aBufferLength);
            }
            if (sLength > 0) {
                i += sLength;
            }
        }
        return sData;
    }

    /**
     * <p>从输入流中读取部份数据(字节数组)</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-22）</li>
     * </ol>
     *
     * @param aInput        输入流
     * @param aReadLength   读取的长度
     * @param aBufferLength 缓冲区字节长度
     * @return 读取到的值 输入流为null时返回null
     * @throws IOException
     * @throws OutOfMemoryError
     */
    public static byte[] readBytesEx(InputStream aInput, int aReadLength, int aBufferLength) throws IOException, OutOfMemoryError {
        if (aInput == null)
            return null;

        // mod by raorq 2010-4-19 : 不能灵活控制缓冲区长度，如果小于0则默认使用2k的缓冲区。
        // aBufferLength = Math.max(aBufferLength,2048);
        if (aBufferLength <= 0) {
            aBufferLength = 2048;
        }
        // mod by raorq 2010-4-19 end

        if (aReadLength <= 0) {
            // mod by raorq 2010-4-19: 如果已知道固定长度，则使用固定长度作为缓冲区，如果不知道则默认是使用2k的缓冲
            // sReadLength = aIs.available() + 2048;
            aReadLength = 1024; // sReadLength = Math.max(aIs.available(),
            // 2048); // 直接修改1024，因为三星E2652W会有兼容性问题。
            // mod by raorq 2010-4-19 end
            ByteArrayOutputStream sBos = new ByteArrayOutputStream(aReadLength);
            byte[] sBuffer = new byte[aBufferLength];
            int sPos = 0;
            // int sHasRead = 0; //del by raorq 2010-4-19 : 无用的代码
            while ((sPos = aInput.read(sBuffer, 0, aBufferLength)) != -1) {
                // sHasRead += sPos; //del by raorq 2010-4-19 : 无用的代码
                sBos.write(sBuffer, 0, sPos);
            }
            byte[] sBytesArray = sBos.toByteArray();
            sBos.close();
            sBos = null;
            return sBytesArray;

        } else {
            return IOUtil.readBytes(aInput, aReadLength, aBufferLength);
        }
    }

    /**
     * <p>从char数组读int数据</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by cairq on 2010-12-15）</li>
     * </ol>
     *
     * @param aSrcArray 源字符数组
     * @param aOffset   偏移值
     * @return 读取到的int值
     */
    public static int readInt(char[] aSrcArray, int aOffset) {
        return ((aSrcArray[aOffset] & 0xFFFF) << 16) + (aSrcArray[aOffset + 1] & 0xFFFF);
    }

    /**
     * <p>将int数据写入char数组</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by cairq on 2010-12-15）</li>
     * </ol>
     *
     * @param aSrcArray 目标字符数组
     * @param aOffset   偏移值
     * @param aValue    待写入的int值
     */
    public static void writeInt(char[] aSrcArray, int aOffset, int aValue) {
        aSrcArray[aOffset] = (char) (aValue >> 16 & 0xFFFF);
        aSrcArray[aOffset + 1] = (char) (aValue & 0xFFFF);
    }

    /**
     * <p>指字符串的utf字节系列写到字节数组中</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-23）</li>
     * </ol>
     *
     * @param aDestArray   目标数组
     * @param aOffset      写入的偏移值
     * @param aSrcUTFBytes 源数组
     */
    public static void writeUTFBytes(byte[] aDestArray, int aOffset, byte[] aSrcUTFBytes) {
        if (aSrcUTFBytes == null) {
            IOUtil.writeShort(aDestArray, aOffset, (short) 0);
        } else {
            IOUtil.writeShort(aDestArray, aOffset, (short) aSrcUTFBytes.length);
            System.arraycopy(aSrcUTFBytes, 0, aDestArray, aOffset + 2, aSrcUTFBytes.length);
        }
    }


    /**
     * 将inputStream的内容存储在byte数组中
     *
     * @param aInputStream 文件输入流
     * @return
     */
    public static byte[] readBytes(InputStream aInputStream) throws IOException {
        return readBytes(aInputStream, false);
    }

    /**
     * 将inputStream的内容存储在byte数组中，用于较大文件。
     * 默认buffer size为32K
     * 【注：该部分没弄清原作者为什么将buffer size设置为32K，而byte 数组初始大小只为2k】
     *
     * @param input 文件输入流
     * @return
     */
    public static byte[] readFullBytes(InputStream input) throws IOException {
        return readBytes(input, true);
    }

    /**
     * 将inputStream的内容存储在byte数组中
     * 将{@link #readFullBytes(InputStream)}和{@link #readBytes(InputStream)}相同的代码抽离出来
     *
     * @param is        文件输入流
     * @param isBigFile 是否是大文件，采取不同的buffer
     * @return
     */
    private static byte[] readBytes(InputStream is, boolean isBigFile) throws IOException {
        if (is == null) {
            return null;
        }
        int bufferSize;
        ByteArrayOutputStream baos;
        if (isBigFile) {
            //不知道原作者为什么将buffer size设置为32K，而byte 数组初始大小只为2k
            bufferSize = 32 * 1024;
            baos = new ByteArrayOutputStream(2048);
        } else {
            bufferSize = 4 * 1024;
            baos = new ByteArrayOutputStream();
        }
        byte[] buffer = new byte[bufferSize];
        try {
            int offset = 0;
            while ((offset = is.read(buffer, 0, bufferSize)) > 0) {
                baos.write(buffer, 0, offset);
            }
            return baos.toByteArray();
        } finally {
            safeClose(baos);
        }
    }

    /**
     * 关闭流，并捕获异常
     */
    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭Cursor，并捕获异常
     */
    public static void safeClose(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
