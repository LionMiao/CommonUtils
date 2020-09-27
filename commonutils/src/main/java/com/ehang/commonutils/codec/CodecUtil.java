/**
 * ****************************************************************************
 * Copyright (C) 2005-2014 UCWEB Corporation. All rights reserved File :
 * 2014-6-9
 * <p/>
 * Description : EndecodeUtil.java
 * <p/>
 * Creation : 2014-6-9 Author : aowj@ucweb.com History : Creation, 2014-6-9,
 * aowj, Create the file
 * ****************************************************************************
 */
package com.ehang.commonutils.codec;

import android.util.Base64;


import com.ehang.commonutils.exception.ExceptionHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

public class CodecUtil {

    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    protected static final char hexDigits[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 是否是gb2312编码
     *
     * @param data
     * @return
     */
    public static boolean detectGb2312(byte[] data) {
        if (null == data || 0 == data.length) {
            return false;
        }

        int indexStart = 0;
        int indexEnd = data.length - 1;

        while (true) {
            int srcCharHigh = 0xFF & data[indexStart];
            int srcCharLow;

            if (srcCharHigh < 0x80) { // 0x00~0x7F ASCII码
                if (indexStart >= indexEnd) {
                    break;
                }

                indexStart++;
            } else if (srcCharHigh < 0xA1) { // 0x80~0xA0 空码
                return false;
            } else if (srcCharHigh < 0xAA) { // 0xA1~0xA9 字符部分
                if (indexStart >= indexEnd) {
                    return false;
                }

                srcCharLow = 0xFF & data[indexStart + 1];

                // 低字节范围 0xA1~0xFE
                if (srcCharLow < 0xA1 || srcCharLow > 0xFE) {
                    return false;
                }

                if (indexStart >= indexEnd - 1) {
                    break;
                }

                indexStart += 2;
            } else if (srcCharHigh < 0xB0) { // 0xAA~0xAF 空码
                return false;
            } else if (srcCharHigh < 0xF8) { // 0xB0~0xF7 GB2312常见汉字表
                if (indexStart >= indexEnd) {
                    return false;
                }

                srcCharLow = 0xFF & data[indexStart + 1];

                // 低字节范围 0xA1~0xFE
                if (srcCharLow < 0xA1 || srcCharLow > 0xFE) {
                    return false;
                }

                if (indexStart >= indexEnd - 1) {
                    break;
                }

                indexStart += 2;
            } else { // 0xF8~0xFF 空码
                return false;
            }
        }

        return true;
    }

    // copy from TextUtils.cpp's detectUTFImpl()
    public static boolean detectUtf8(byte[] data) {
        if (null == data || 0 == data.length) {
            return false;
        }

        int checkLen = 0;
        int seqLen = 0;
        int index = 0;
        int oldIndex = 0;
        int checkChar = 0;
        int srcChar = 0;

        while (true) {
            srcChar = 0xFF & data[index];

            if ((srcChar & 0x80) == 0) {
                seqLen = 1;
            } else if ((srcChar & 0xC0) != 0xC0) {
                seqLen = 0;
            } else if ((srcChar & 0xE0) == 0xC0) {
                seqLen = 2;
            } else if ((srcChar & 0xF0) == 0xE0) {
                seqLen = 3;
            } else if ((srcChar & 0xF8) == 0xF0) {
                seqLen = 4;
            } else if ((srcChar & 0xFC) == 0xF8) {
                seqLen = 5;
            } else if ((srcChar & 0xFE) == 0xFC) {
                seqLen = 6;
            }

            if (0 == seqLen) {
                return false;
            }

            checkLen = seqLen;
            oldIndex = index;
            checkChar = 0;

            // 检查UTF格式
            index += seqLen;

            if (index > data.length) {
                return false;
            }

            // 6字节
            if (checkLen == 6) {
                checkChar = 0xFF & data[oldIndex + 5];
                if (checkChar < 0x80 || checkChar > 0xBF) {
                    return false;
                }

                checkLen--;
            }

            // 5字节
            if (checkLen == 5) {
                checkChar = 0xFF & data[oldIndex + 4];
                if (checkChar < 0x80 || checkChar > 0xBF) {
                    return false;
                }

                checkLen--;
            }

            // 4字节
            if (checkLen == 4) {
                checkChar = 0xFF & data[oldIndex + 3];
                if (checkChar < 0x80 || checkChar > 0xBF) {
                    return false;
                }

                checkLen--;
            }

            // 3字节
            if (checkLen == 3) {
                checkChar = 0xFF & data[oldIndex + 2];
                if (checkChar < 0x80 || checkChar > 0xBF) {
                    return false;
                }

                checkLen--;
            }

            // 2字节
            if (checkLen == 2) {
                checkChar = 0xFF & data[oldIndex + 1];
                if (checkChar > 0xBF) {
                    return false;
                }

                switch (srcChar) {
                    // // no fall-through in this inner switch
                    // case 0xE0: if (checkChar < 0xA0) return false;
                    // case 0xED: if (checkChar > 0x9F) return false;
                    // case 0xF0: if (checkChar < 0x90) return false;
                    // case 0xF4: if (checkChar > 0x8F) return false;
                    default:
                        if (checkChar < 0x80)
                            return false;
                }

                checkLen--;
            }

            // 1字节
            if (checkLen == 1) {
                if (srcChar >= 0x80 && srcChar < 0xC2) {
                    return false;
                }
            }

            // if (srcChar > 0xF4)
            // return false;

            if (index == data.length)
                return true;
        }
    }

    /**
     * Check a string is chinese character.
     *
     * @param chineseStr Checking string.
     * @return If string is chinese character return true, else return false.
     */
    public static final boolean isChineseCharacter(String chineseStr) {
        char[] charArray = chineseStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            // 是否是Unicode编码,除了"?"这个字符.这个字符要另外处理
            if ((charArray[i] >= '\u0000' && charArray[i] < '\uFFFD')
                    || ((charArray[i] > '\uFFFD' && charArray[i] < '\uFFFF'))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public static byte[] zipData(byte[] data) {
        if (null == data || 0 == data.length) {
            return null;
        }

        byte[] result = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gos = new GZIPOutputStream(baos);
            gos.write(data);

            gos.finish();
            gos.flush();
            gos.close();

            baos.flush();
            baos.close();


            result = baos.toByteArray();
        } catch (Throwable e) {
            ExceptionHandler.processFatalException(e);
        }

        return result;
    }

    public static byte[] unZipData(byte[] data) {
        if (null == data || 0 == data.length) {
            return null;
        }

        byte[] result = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            GZIPInputStream gis = new GZIPInputStream(bais);

            byte[] tmpBuf = new byte[4096];
            int readlen = 0;
            while ((readlen = gis.read(tmpBuf)) != -1) {
                baos.write(tmpBuf, 0, readlen);
            }

            gis.close();
            result = baos.toByteArray();

            bais.close();
            baos.close();

        } catch (Exception e) {
            ExceptionHandler.processFatalException(e);
        }

        return result;
    }


    /**
     * 使用算法解压数据，不涉及zip文件格式
     * 目前用于云同步
     *
     * @param data
     * @return
     */
    public static byte[] inflateData(byte[] data) {
        if (null == data || 0 == data.length) {
            return null;
        }

        byte[] result = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            InflaterInputStream gis = new InflaterInputStream(bais);

            byte[] tmpBuf = new byte[4096];
            int readlen = 0;
            while ((readlen = gis.read(tmpBuf)) != -1) {
                baos.write(tmpBuf, 0, readlen);
            }

            gis.close();
            result = baos.toByteArray();

            bais.close();
            baos.close();

            return result;

        } catch (Exception e) {
            ExceptionHandler.processSilentException(e);
        }

        return null;
    }

    /**
     * 使用算法压缩数据，不涉及zip文件格式，
     * 目前用于云同步
     *
     * @param data
     * @return
     */
    public static byte[] deflateData(byte[] data) {
        if (data == null || 0 == data.length) {
            return null;
        }

        byte[] result = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DeflaterOutputStream zip = new DeflaterOutputStream(bos);
            zip.write(data);
            zip.close();
            result = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ExceptionHandler.processSilentException(ex);
        }
        return result;
    }

    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        // 由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        return value;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        // 由高位到低位
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static String md5(byte[] bytes) {
        try {
            if (bytes == null) {
                return null;
            }
            MessageDigest algorithm = MessageDigest.getInstance("md5");
            algorithm.reset();
            algorithm.update(bytes);
            return toHexString(algorithm.digest(), "").toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            ExceptionHandler.processFatalException(e);
        }

        return null;
    }

    public static String md5(File file) {
        try {
            if (file == null) {
                return null;
            }
            MessageDigest algorithm = MessageDigest.getInstance("md5");
            algorithm.reset();
            try {
                byte[] bytes = new byte[10240];
                InputStream inputStream = new FileInputStream(file);
                int length;
                while ((length = inputStream.read(bytes)) != -1) {
                    algorithm.update(bytes, 0, length);
                }
                inputStream.close();
                return toHexString(algorithm.digest(), "").toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (NoSuchAlgorithmException e) {
            ExceptionHandler.processFatalException(e);
        }
        return null;
    }

    public static String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            char c0 = hexDigits[(b & 0xf0) >> 4];
            char c1 = hexDigits[b & 0xf];
            hexString.append(c0);
            hexString.append(c1).append(separator);
        }

        return hexString.toString();
    }

    public static byte[] base64Decode(byte[] base64Array) {
        return Base64.decode(base64Array, Base64.DEFAULT);
    }

    public static byte[] base64Decode(String str) {
        return Base64.decode(str, Base64.DEFAULT);
    }

    public static byte[] base64Decode(String str, int flag) {
        return Base64.decode(str, flag);
    }

    public static byte[] base64Decode(byte[] data, int flag) {
        return Base64.decode(data, flag);
    }

    public static String base64Encode2String(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static String base64Encode2String(byte[] bytes, int flag) {
        return Base64.encodeToString(bytes, flag);
    }

    public static byte[] base64Encode(byte[] bytes) {
        return Base64.encode(bytes, Base64.DEFAULT);
    }

    public static byte[] base64Encode(byte[] bytes, int flag) {
        return Base64.encode(bytes, flag);
    }


    /**
     * 判断一个字符是否为ascii码
     *
     * @param c
     * @return
     */
    public static boolean isAscii(char c) {
        return c / 0x80 == 0;
    }

}
