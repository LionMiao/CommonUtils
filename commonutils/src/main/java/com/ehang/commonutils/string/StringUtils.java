/*
 * Copyright (C) 2005-2012 UCWEB Corporation. All rights reserved
 * Author      : miaozh@ucweb.com
 * History     : miaozh, Create the file
 */
package com.ehang.commonutils.string;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.ehang.commonutils.codec.CodecUtil;
import com.ehang.commonutils.io.IOUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <b>字符串处理工具集</b>
 * 字符串拼接，字符串裁剪，字符串解析，字符串规则判断，字符串和byte相互转化，字符串格式化
 * <p/>
 */
public final class StringUtils {
    private static final String TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss.FFF";

    /**
     * 判断数组是否为空，null或者长度为0都为空
     *
     * @param aTexts 待检查的数组
     * @return 是否为空
     */
    public static boolean isEmpty(String[] aTexts) {
        return aTexts == null || aTexts.length == 0;
    }


    /**
     * 将任意个字符串合并成一个字符串
     *
     * @param mText 字符串
     * @return 如果参数为null，返回null，否则返回拼接后的字符串
     */
    public static String merge(CharSequence... mText) {
        if (mText == null) {
            return null;
        }
        int length = mText.length;
        StringBuilder mStringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (mText[i] != null && mText[i].length() > 0 && !mText[i].toString().equals("null")) {
                mStringBuilder.append(mText[i]);
            }
        }
        return mStringBuilder.toString();
    }


    /**
     * <p>将字符串转换成整数</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-23）</li>
     * </ol>
     *
     * @param aValue   待处理的字符串
     * @param aDefault 默认返回值
     * @return 当待处理的字符串为null或者解析失败时返回默认值，否则返回正确的值
     */
    public static int parseInt(String aValue, int aDefault) {
        if (TextUtils.isEmpty(aValue)) {
            return aDefault;
        }
        boolean isHex = false;
        if (isHex = aValue.startsWith("0x")) {
            aValue = aValue.substring(2);
        }
        try {
            if (!isHex) {
                aDefault = Integer.parseInt(aValue);
            } else {
                aDefault = (int) Long.parseLong(aValue, 16);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return aDefault;
    }

    /**
     * 将字符串解析为整型数，失败时返回默认值0
     *
     * @see #parseInt(String, int)
     * TODO 此处default value为0，出现异常时也是返回0，业务代码会当成正常情况处理
     */
    public static int parseInt(String aValue) {
        return parseInt(aValue, 0);
    }

    /**
     * <p>将字符串解析成长整数</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-23）</li>
     * </ol>
     *
     * @param aValue   待解析的字符串
     * @param aDefault 默认值
     * @return 字符串为空或解析失败时返回默认值
     */
    public static long parseLong(String aValue, long aDefault) {
        if (TextUtils.isEmpty(aValue)) {
            return aDefault;
        }
        boolean isHex = false;
        if (isHex = aValue.startsWith("0x")) {
            aValue = aValue.substring(2);
        }
        try {
            if (!isHex) {
                aDefault = Long.parseLong(aValue);
            } else {
                aDefault = Long.parseLong(aValue, 16);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return aDefault;
    }

    /**
     * 将字符串解析为长整型数，失败时返回默认值0
     *
     * @see #parseLong(String, long)
     * TODO 此处default value为0，出现异常时也是返回0，业务代码会当成正常情况处理
     */
    public static long parseLong(String aValue) {
        return parseLong(aValue, 0);
    }

    /**
     * <p>将字符串解析成双精度浮点数</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-23）</li>
     * </ol>
     *
     * @param aValue   待解析的字符串
     * @param aDefault 默认值
     * @return 字符串为空或解析失败时返回默认值
     */
    public static double parseDouble(String aValue, double aDefault) {
        if (TextUtils.isEmpty(aValue)) {
            return aDefault;
        }
        try {
            aDefault = Double.parseDouble(aValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return aDefault;
    }

    /**
     * 将字符串解析为双精度浮点数，失败时返回默认值0
     *
     * @see #parseDouble(String, double)
     */
    public static double parseDouble(String aValue) {
        return parseDouble(aValue, 0);
    }

    /**
     * 按照给定的regex字符分割字符串，允许出现长度为0的字符串
     *
     * @see #split(String, String, boolean)
     */
    public static String[] split(String original, String regex) {
        return split(original, regex, true);
    }

    /**
     * 按照给定的aRegex字符分割字符串
     *
     * @param original 待分割的字符串
     * @param regex    分割符
     * @param canNull  是否可以有空字符串，如"a,,b",可以有空字符串时，得到结果{"a","","b"},否则为{"a","b"}
     * @return 源字符串为空时返回长度为0的String数组，分隔符为空时返回长度为1的String数组
     */
    public static String[] split(String original, String regex, boolean canNull) {
        if (TextUtils.isEmpty(original)) {
            return new String[0];
        }

        if (TextUtils.isEmpty(regex)) {
            return new String[]{original};
        }

        String[] sTarget = null;
        int sTargetLength = 0;
        int sLength = original.length();
        int sStartIndex = 0;
        int sEndIndex = 0;

        //扫描字符串，确定目标字符串数组的长度
        for (sEndIndex = original.indexOf(regex, 0); sEndIndex != -1 && sEndIndex < sLength;
             sEndIndex = original.indexOf(regex, sEndIndex)) {
            sTargetLength += (canNull || sStartIndex != sEndIndex) ? 1 : 0;
            sStartIndex = sEndIndex += sEndIndex >= 0 ? regex.length() : 0;
        }

        //如果最后一个标记的位置非字符串的结尾，则需要处理结束串
        sTargetLength += canNull || sStartIndex != sLength ? 1 : 0;

        //重置变量值，根据标记拆分字符串
        sTarget = new String[sTargetLength];
        int sIndex = 0;
        for (sIndex = 0, sEndIndex = original.indexOf(regex, 0), sStartIndex = 0;
             sEndIndex != -1 && sEndIndex < sLength;
             sEndIndex = original.indexOf(regex, sEndIndex)) {
            if (canNull || sStartIndex != sEndIndex) {
                sTarget[sIndex] = original.substring(sStartIndex, sEndIndex);
                ++sIndex;
            }
            sStartIndex = sEndIndex += sEndIndex >= 0 ? regex.length() : 0;
        }

        //取结束的子串
        if (canNull || sStartIndex != sLength) {
            sTarget[sTargetLength - 1] = original.substring(sStartIndex);
        }

        return sTarget;
    }

    /**
     * 当文件名的字符个数超出 MAX时，将会截取 splt字符前的一些字符，最终输出字符串的字符大小不大于MAX
     * 比如MAX为10， str = "12345678566.txt" 那么输出的为是 “123456.txt”
     *
     * @param str  输入的字符串
     * @param max  字符个数上限限制
     * @param splt 特定字符，比如"."
     * @return
     */
    public static String clipFileName(String str, int max, char splt) {
        if (TextUtils.isEmpty(str) || str.length() < max) {
            return str;
        }
        final int CHAR_NUMS = str.length();
        int spltIndex = str.lastIndexOf(splt);
        if (spltIndex == -1) {
            spltIndex = CHAR_NUMS;
        }
        final int needMinusNums = CHAR_NUMS - max; //中间需要截取的字符个数
        String strSuffix = str.substring(spltIndex, CHAR_NUMS); //后缀（包括"."）
        final int needMinusEndIndex = spltIndex - needMinusNums;
        String strPreffix = null;
        if (needMinusEndIndex < 0) { //如果后缀名太长则去除超过限制数的字符
            str = str.substring(0, max);
        } else {
            strPreffix = str.substring(0, needMinusEndIndex);
            str = strPreffix + strSuffix;
        }

        return str;
    }

    /**
     * 生成一个随机字符串，当前时间的16进制值 + 100000~1000000的一个随机值的十六进制表达式组成的字符串
     *
     * @return 随机字符串
     */
    public static String generateRandomString() {
        long currentTime = System.currentTimeMillis();
        String timeStr = Long.toHexString(currentTime);
        int randomNum = (int) (Math.random() * 1000000);
        String randomStr = Integer.toHexString(randomNum);
        return (timeStr + randomStr).toUpperCase();
    }

    /**
     * 比较两个字符串（大小写敏感）。
     * <p/>
     * <pre>
     * StringUtil.equals(null, null) = true
     * StringUtil.equals(null, "abc") = false
     * StringUtil.equals("abc", null) = false
     * StringUtil.equals("abc", "abc") = true
     * StringUtil.equals("abc", "ABC") = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     * @author chenzh@ucweb.com
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }

    /**
     * <p>字符串比较，不考虑大小写。如果两个字符串的长度相同，并且其中的相应字符都相等（忽略大小写），则认为这两个字符串是相等的</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-16）</li>
     * </ol>
     *
     * @param aOriginalStr 需要被对比的字符串
     * @param anotherStr   需要对比的字符串
     * @return 相等（忽略大小写），则返回 true；否则返回 false
     */
    public static boolean equalsIgnoreCase(String aOriginalStr, String anotherStr) {
        return aOriginalStr != null && anotherStr != null && aOriginalStr.length() == anotherStr.length()
                && aOriginalStr.toLowerCase().equalsIgnoreCase(anotherStr);
    }

    /**
     * <p>测试此字符串是否以指定的前缀开始,注:不区分大小写</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-16）</li>
     * </ol>
     *
     * @param aOriginalStr 测试的字符串
     * @param aPrefixStr   指定的前缀
     * @return 如果参数表示的字符序列是此字符串表示的字符序列的前缀，则返回 true；否则返回 false
     */
    public static boolean startsWithIgnoreCase(String aOriginalStr, String aPrefixStr) {
        return (aOriginalStr == null && aPrefixStr == null)
                || (aOriginalStr != null && aPrefixStr != null && aOriginalStr.toLowerCase().startsWith(aPrefixStr.toLowerCase()));
    }

    /**
     * <p>返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-16）</li>
     * </ol>
     *
     * @param aOriginalStr 计算的字符串
     * @param aStr         指定的子字符串
     * @param aFromIndex   指定的索引
     * @return 如果字符串参数作为一个子字符串在此对象中出现一次或多次, 则返回最后一个这种子字符串的第一个字符.如果它不作为一个子字符串出现, 则返回 -1
     */
    public static int indexOfIgnoreCase(String aOriginalStr, String aStr, int aFromIndex) {
        if (aOriginalStr == null || aStr == null) {
            return -1;
        }

        int aTextLength = aOriginalStr.length();
        int sOtherTextLength = aStr.length();
        int sMax = aTextLength - sOtherTextLength;
        if (aFromIndex >= aTextLength) {
            if (aTextLength == 0 && aFromIndex == 0 && sOtherTextLength == 0) {
                /* There is an empty string at index 0 in an empty string. */
                return 0;
            }
            /* Note: fromIndex might be near -1>>>1 */
            return -1;
        }
        if (aFromIndex < 0) {
            aFromIndex = 0;
        }
        if (sOtherTextLength == 0) {
            return aFromIndex;
        }

        char aFirst = Character.toLowerCase(aStr.charAt(0));
        int i = aFromIndex;

        startSearchForFirstChar:
        while (true) {

            /* Look for first character. */
            while (i <= sMax && Character.toLowerCase(aOriginalStr.charAt(i)) != aFirst) {
                i++;
            }
            if (i > sMax) {
                return -1;
            }

            /* Found first character, now look at the rest of v2 */
            int j = i + 1;
            int end = j + sOtherTextLength - 1;
            int k = 1;

            while (j < end) {
                char c1 = Character.toLowerCase(aOriginalStr.charAt(j++));
                char c2 = Character.toLowerCase(aStr.charAt(k++));
                if (c1 != c2) {
                    i++;
                    /* Look for str's first char again. */
                    continue startSearchForFirstChar;
                }
            }
            return i; /* Found whole string. */
        }
    }


    /**
     * 以sepertor链接字符串
     *
     * @param list      字符串链表
     * @param seperator 分隔符
     * @return 当链表为空，或者分隔符为null时返回长度为0的字符串
     */
    public static String join(List<String> list, String seperator) {
        if (null == list || list.isEmpty() || null == seperator) {
            return "";
        }

        int listSize = list.size();
        StringBuilder sb = new StringBuilder(listSize);
        if (listSize > 0) {
            sb.append(list.get(0));
            for (int i = 1; i < listSize; i++) {
                sb.append(seperator);
                sb.append(list.get(i));
            }
        }

        return sb.toString();
    }

    /**
     * 替换字符串中的 & nbsp 空格为半角空格
     */
    public static String replaceNBSPSpace(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.replace('\u00A0', '\u0020');
    }

    /**
     * <p>避免null字符串,注:当参数为null时,返回长度为零的字符串对象</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-16）</li>
     * </ol>
     *
     * @param text 待检查的字符串
     * @return 检查后的字符串
     */
    public static String toEmpty(String text) {
        return text == null ? "" : text;
    }

    /**
     * 将string转换为byte[],编码格式UTF-8
     *
     * @see #createStringByte(String, String)
     */
    public static final byte[] createUTFByte(String text) {
        return createStringByte(text, "UTF-8");
    }

    /**
     * 将string转换为byte[]，默认编码为UTF-8
     *
     * @param sText    字符串
     * @param sCharset 编码格式
     * @return 当string为null时返回null
     */
    public static final byte[] createStringByte(String sText, String sCharset) {
        if (sText == null)
            return null;
        try {
            //默认是发utf-8
            return sText.getBytes(sCharset = TextUtils.isEmpty(sCharset) ? "UTF-8" : sCharset);
        } catch (Exception e) {
            try {
                return sText.getBytes(sCharset.toLowerCase());
            } catch (Exception e1) {
            }

            return sText.getBytes();
        }
    }

    /**
     * 将byte转换为string，byte为utf-8格式
     *
     * @param body byte[]
     * @return utf8编码的字符串，body为空时返回""
     */
    public final static String utf8ByteToString(byte[] body) {
        if (body == null || body.length == 0) {
            return "";
        }
        return utf8ByteToString(body, 0, body.length);
    }

    /**
     * 将byte转换为string，byte为utf-8格式，可指定偏移量
     *
     * @param aData   byte[]
     * @param aOffset int
     * @return utf8编码的字符串，body为空时或offset越界时返回""
     */
    public final static String utf8ByteToString(byte[] aData, int aOffset) {
        if (aData == null || aData.length == 0 || aOffset < 0 || aOffset >= aData.length) {
            return "";
        }
        int utflen = IOUtil.readShort(aData, aOffset);
        return utf8ByteToString(aData, aOffset + 2, utflen);
    }

    /**
     * 将byte转换为string，byte为utf-8格式，可指定偏移量和长度
     *
     * @param aData   需要转换的byte数据
     * @param aOffset 从什么位置开始转换
     * @param aLength 转换数据的长度
     * @return
     */
    public final static String utf8ByteToString(byte[] aData, int aOffset, int aLength) {
        if (aData == null || aOffset < 0 || aLength <= 0)
            return "";

        //初始的size减小一倍，考虑到可能转的东西很多是中文，没必要生成那么多的空间:)
        StringBuffer sStringBuffer = new StringBuffer(aLength >> 1);
        utf8ByteToString(aData, aOffset, aLength, sStringBuffer);
        // The number of chars produced may be less than utflen,use new string(stringbuffer) can trim the useless blank chars 
        return new String(sStringBuffer);
    }

    /**
     * 将byte转换为string，byte为utf-8格式，可指定偏移量和长度以及buffer大小
     *
     * @param bytes       需要转换的数据
     * @param aReadOffset 从数据中的什么地方开始
     * @param aReadSize   转换多少长度
     * @param aBuffer     存放转换之后的字符
     * @return
     */
    public static final int utf8ByteToString(byte[] bytes, int aReadOffset, int aReadSize, StringBuffer aBuffer) {
        if (bytes == null || bytes.length == 0 || aBuffer == null || aReadOffset < 0 || aReadSize <= 0)
            return 0;

        int index = aReadOffset;
        aReadSize += aReadOffset;
        do {
            if (index >= aReadSize) {
                break;
            }
            int i;
            switch ((i = bytes[index] & 0xff) >> 4) {
                case 0: // '\0'
                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                case 4: // '\004'
                case 5: // '\005'
                case 6: // '\006'
                case 7: // '\007'
                    index++;
                /* ch[chIndex++] = (char) i; */
                    aBuffer.append((char) i);
                    break;

                case 12: // '\f'
                case 13: // '\r'
                    if ((index += 2) > aReadSize) {
                        return aBuffer.length();
                    }
                    byte byte0;
                    if (((byte0 = bytes[index - 1]) & 0xc0) != 128) {
                        return aBuffer.length();
                    }
                /* ch[chIndex++] = (char) ((i & 0x1f) << 6 | byte0 & 0x3f); */
                    aBuffer.append((char) ((i & 0x1f) << 6 | byte0 & 0x3f));
                    break;

                case 14: // '\016'
                    if ((index += 3) > aReadSize) {
                        return aBuffer.length();
                    }
                    byte byte1 = bytes[index - 2];
                    byte byte2 = bytes[index - 1];
                    if ((byte1 & 0xc0) != 128 || (byte2 & 0xc0) != 128) {
                        return aBuffer.length();
                    }
                /*
                 * ch[chIndex++] = (char) ((i & 0xf) << 12 | (byte1 & 0x3f) << 6
                 * | (byte2 & 0x3f) << 0);
                 */
                    aBuffer.append((char) ((i & 0xf) << 12 | (byte1 & 0x3f) << 6 | (byte2 & 0x3f) << 0));
                    break;

                case 8: // '\b'
                case 9: // '\t'
                case 10: // '\n'
                case 11: // '\013'
                default:
                    return aBuffer.length();
            }
        } while (true);
        return aBuffer.length();
    }

    /**
     * 解析boolean,默认值为false
     *
     * @see #parseBoolean(String, boolean)
     */
    public static boolean parseBoolean(String value) {
        return parseBoolean(value, false);
    }

    /**
     * 解析boolean，可指定默认值
     *
     * @param value    待解析的值
     * @param aDefault 默认值
     * @return 字符串为空时返回默认值
     */
    public static boolean parseBoolean(String value, boolean aDefault) {
        if (TextUtils.isEmpty(value)) {
            return aDefault;
        }

//        if ("1".endsWith(value) || "true".equalsIgnoreCase(value)) {
//            return true;
//        }
        //将endsWith改为equals，没能理解为什么要用endsWith,虽然结果一样，modify by miaozh
        if ("1".equals(value) || "true".equalsIgnoreCase(value)) {
            return true;
        }
        return false;
    }

    /**
     * 将字符串转换为字节数组
     *
     * @param value    待转换的字符串
     * @param encoding 字符编码，为空时采用系统默认编码
     * @return value为空或者转换失败时返回null
     */
    public static byte[] getStringBytes(String value, String encoding) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        if (TextUtils.isEmpty(encoding)) {
            return value.getBytes();
        }

        byte[] data = null;
        try {
            data = value.getBytes(encoding);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * 使用给定的字符串替换字符串中所有和匹配字符串相同的子字符串
     *
     * @see #replaceAll(String, String, String, boolean)
     */
    public static String replaceAll(String srcString, String matchingString, String replacement) {
        return replaceAll(srcString, matchingString, replacement, false);
    }

    /**
     * <p>使用给定的 replacement 替换此字符串所有匹配给定的匹配的子字符串</p>
     * <p/>
     * <b>修改历史</b>
     * <ol>
     * <li>创建（Added by luogw on 2011-11-16）</li>
     * </ol>
     *
     * @param srcString               源字符串
     * @param matchingString          匹配的字符串
     * @param replacement             替换的字符串
     * @param supportReplacementEmpty 替换的字符串是否可以为""或者null, null会转换成""
     * @return 返回替换操作后的字符串
     */
    public static String replaceAll(String srcString, String matchingString, String replacement, boolean supportReplacementEmpty) {
        boolean flag = TextUtils.isEmpty(replacement);
        if (supportReplacementEmpty) {
            if (replacement == null) {
                replacement = "";
            }
            flag = false;
        }
        if (TextUtils.isEmpty(srcString) || TextUtils.isEmpty(matchingString) || flag) {
            return null;
        }

        StringBuffer sResult = new StringBuffer();
        int sIndex = 0;
        int sMaxIndex = srcString.length() - 1;
        while ((sIndex = srcString.indexOf(matchingString)) != -1) {
            String sPreStr = srcString.substring(0, sIndex);
            sResult.append(sPreStr).append(replacement);
            srcString = (sIndex < sMaxIndex) ? srcString.substring(sIndex + matchingString.length()) : "";
        }
        sResult.append(srcString);

        return sResult.toString();
    }

    /**
     * 是否包含中文
     */
    public static boolean containsChinese(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否由数字，点，横杠组成
     *
     * @param str
     * @return 字符串为null或空时返回false
     */
    public static boolean isMakeUpWithNum(String str) {
        if (null == str) {
            return false;
        }
        try {
            Pattern p = Pattern.compile("^[\\d-\\.]{2,}$");
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Compare two version strings. the version string's format is like
     * "2.1.0.107".
     *
     * @return Return 0 if left and right are equal. Return a positive number if
     * left is higher than right. Return a negative number if left is
     * lower than right.
     */
    public static int compareVersion(String left, String right) {
        if ((left == null && right == null) || (left != null && left.length() == 0 && right != null && right.length() == 0)) {
            return 0;
        }

        if (TextUtils.isEmpty(right)) {
            return 1;
        }

        if (TextUtils.isEmpty(left)) {
            return -1;
        }

        String leftArray[] = split(left, ".");
        String rightArray[] = split(right, ".");

        int compareStep = Math.min(leftArray.length, rightArray.length);
        int leftInt;
        int rightInt;
        for (int i = 0; i < compareStep; i++) {
            leftInt = parseInt(leftArray[i]);
            rightInt = parseInt(rightArray[i]);
            if (leftInt == rightInt) {
                continue;
            }

            return (leftInt - rightInt);
        }

        // their prefix are the same,so the the longer one is higher.
        int compareResult = leftArray.length - rightArray.length;
        return compareResult;
    }

    /**
     * 去掉数组中的空元素
     *
     * @param list
     */
    public static void trime(List list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) {
                list.remove(i);
                i = i - 1;
            }
        }
    }

    public static String getStringAndReplaceFromXML(String source, String... args) {
        if (source != null) {
            for (int i = 0; i < args.length; i++) {
                source = source.replace("[spstr" + (i + 1) + "]", args[i] + "");
            }
        }
        return source;
    }

    public static String getStringAndReplaceFromXML(String source, int... args) {
        if (source != null) {
            for (int i = 0; i < args.length; i++) {
                source = source.replace("[spstr" + (i + 1) + "]", args[i] + "");
            }
        }
        return source;
    }

    /**
     * 判断起始字符串
     *
     * @return 当参数为null时返回false
     */
    public static boolean startWithIgnoreCase(String oriString, String startString) {
        if (oriString == null || startString == null) {
            return false;
        }

        int length = startString.length();
        if (length > oriString.length()) {
            return false;
        }
        if (startString.equalsIgnoreCase(oriString.substring(0, length))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 输入流
     * @return 当流为null或者读取异常时返回null
     */
    public static String inputStream2String(InputStream is) {
        if (is == null) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        IOUtil.safeClose(in);
        return buffer.toString();
    }

    /**
     * 截断超过指定长度的字符串，并在字符串中部补充省略号。返回经过截断处理的新字符串。
     * 如果输入字符串并没有超过指定长度则不作任何处理。
     * 当输入为null时返回null。
     *
     * @param oriString 需要截断的字符串
     * @param length    限定的长度
     * @return 经过阶段处理后的新字符串
     * @author kid
     */
    public static CharSequence truncateStringMiddle(final CharSequence oriString, int length) {
        if (oriString == null) {
            return null;
        }

        if (oriString.length() <= length) {
            return oriString;
        }

        int uch = 0;//从字符串头开始记录unicode字符个数
        int ach = 0;//从字符串头开始记录ascii字符个数
        int uct = 0;//从字符串尾开始记录unicode字符个数
        int act = 0;//从字符串尾开始记录ascii字符个数
        int l = 0;//记录已经遍历完的字符的长度
        int i = 0;//字符起始偏移值
        int j = oriString.length() - 1;//字符尾部偏移值
        while (i++ <= j--) {
            // from head
            if (CodecUtil.isAscii(oriString.charAt(i))) {
                ++ach;
                l += 1;
            } else {
                ++uch;
                l += 2;
            }
            if (i == j) {
                break;
            }
            // from tail
            if (CodecUtil.isAscii(oriString.charAt(j))) {
                ++act;
                l += 1;
            } else {
                ++uct;
                l += 2;
            }
            //l > length之前的算法逻辑有问题，当l等于length时不会返回还会再去两个字符，改为l >= length
            if (l >= length) {
                return oriString.subSequence(0, uch + ach - 3) + "..." + oriString.subSequence(oriString.length() - uct - act, oriString.length());
            }
        }

        return oriString;
    }

    /**
     * 裁取字符，注意会区分中英文彻底进行裁取
     *
     * @return text为空时直接返回
     */
    public static String subString(String text, int maxLength) {
        if (TextUtils.isEmpty(text) || text.length() < maxLength) {
            return text;
        }
        int textLength = text.length();
        int byteLength = 0;
        StringBuffer stringBuffer = new StringBuffer();
        String substring = null;
        //此处为什么是byteLength < maxLength - 1，这样截取的字符永远会少一位，改为 byteLength < maxLength miaozh 2015/8/13
        for (int i = 0; i < textLength && byteLength < maxLength; i++) {
            substring = text.substring(i, i + 1);
            if (substring.getBytes().length == 1) {
                byteLength++;
            } else {
                byteLength += 2;
            }
            stringBuffer.append(substring);
        }
        return stringBuffer.toString();
    }

    /**
     * 从src中裁出from与to之间的字符串
     * src = "abcd=ddd;", from="abcd=", to=";",return "ddd";
     * src = "abcd=ddd;", from="abcd="; to=""或者null, return "ddd;";
     * src = "abcd=ddd", from="abcd="; to=";", return "ddd";
     */
    public static String subString(String src, String from, String to) {
        if (TextUtils.isEmpty(src)) {
            return src;
        }
        if (null == from) {
            from = "";
        }
        int fromIndex = src.indexOf(from);
        if (fromIndex == -1) {
            return src;
        }
        fromIndex += from.length();
        if (TextUtils.isEmpty(to)) {
            return src.substring(fromIndex);
        }
        int toIndex = src.indexOf(to, fromIndex);
        if (toIndex == -1 || toIndex <= fromIndex) {
            return src.substring(fromIndex);
        }

        return src.substring(fromIndex, toIndex);
    }

    /**
     * 获取字符串长度， 中文会算2字符长度
     *
     * @return 字符串为空时返回0
     */
    public static int getBytesLength(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }

        int nLength = 0;
        try {
            nLength = str.getBytes("GBK").length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nLength;
    }


    /**
     * <p>判断对象是否是字符串实例</p>
     *
     * @param object 判断对象
     * @return true: 是字符串对象，false: 非字符串对象
     */
    public static boolean isStringObject(Object object) {
        return object != null && object instanceof String;
    }

    /**
     * 从XHTML2中取出body的内容
     *
     * @param xhtml2 html内容
     * @return body的内容，不包含<body></body>标签
     */
    public static String getBodyContentFromXHTML2(String xhtml2) {
        //对传入的参数做判空处理
        if (xhtml2 == null) {
            return null;
        }

        String bodyContent = null;
        String bodyStartTag = "<body>";
        String bodyEndTag = "</body>";

        int pos1 = xhtml2.indexOf(bodyStartTag);
        int pos2 = xhtml2.indexOf(bodyEndTag);

        if (pos1 > 0 && pos2 > 0 && pos2 > pos1) {
            bodyContent = new String(xhtml2.substring(pos1 + bodyStartTag.length(), pos2).trim());
        } else if (pos1 > 0 && pos2 == -1) {
            bodyContent = new String(xhtml2.substring(pos1 + bodyStartTag.length()).trim());
        } else if (pos1 == -1 && pos2 > 0) {
            bodyContent = new String(xhtml2.substring(0, pos2).trim());
        }

        return bodyContent;
    }

    /**
     * 去除字符串的前缀
     */
    public static String subString(String src, String preString) {
        String result = src;
        if (!TextUtils.isEmpty(src) && !TextUtils.isEmpty(preString) && src.length() > preString.length()
                && src.indexOf(preString) > -1) {
            result = src.substring(preString.length());
        }
        return result;
    }

    /**
     * 删除Unicode代理区字符
     * android java String 内部使用UTF-16编码,不能识别路径有这种字符的文件
     * 代理区代码:范围为 [0xD800, 0xDFFF] 用于表示UTF-16编码中Unicode编码大于等于0x10000范围的字符
     * 参考:http://zh.wikipedia.org/wiki/UTF-16
     */
    private static final char UNICODE_SURROGATE_START_CHAR = 0xD800;
    private static final char UNICODE_SURROGATE_END_CHAR = 0xDFFF;

    /**
     * 去除范围[0xD800, 0xDFFF]内的字符
     */
    public static String removeSurrogateChars(String string) {
        if (TextUtils.isEmpty(string)) {
            return string;
        }
        int length = string.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            if (c < UNICODE_SURROGATE_START_CHAR || c > UNICODE_SURROGATE_END_CHAR) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 是否包含[0xD800, 0xDFFF]代理区字符
     *
     * @return 字符串为空时返回false
     */
    public static boolean containsSurrogateChar(String string) {
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        int length = string.length();
        boolean hasSurrogateChar = false;
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            if (UNICODE_SURROGATE_START_CHAR <= c && c <= UNICODE_SURROGATE_END_CHAR) {
                hasSurrogateChar = true;
                break;
            }
        }
        return hasSurrogateChar;
    }

    /**
     * 是否包含特殊字符,其中包括以下字符
     * `~!@#$%^&*()+=|{}':;',\[\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？
     *
     * @return
     */
    public static boolean containsSpecialCharacters(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        return m.find();
    }

    /**
     * 将字符串中含有的引号等特殊字符转换成转义符
     */
    public static String escapeSequence(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);
            switch (c) {
                case '\'':
                    sb.append("\\\'");
                    break;
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 截剪字符串，从首字符开始截取length长度
     *
     * @param text   原字符串
     * @param length 裁剪后的长度
     * @return 字符串为空时返回其本身，否则返回裁剪后的字符串
     */
    public static String cutOff(String text, int length) {
        if (text != null && length > -1 && text.length() > length) {
            text = text.substring(0, length);
        }
        return text;
    }

    /**
     * 判断是否由ASCII码组成的字符串
     *
     * @param text
     * @return 全部是ascii字符返回true，否则返回false（null或""也返回false）
     */
    public static boolean isMadeUpOfAscii(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        int len = text.length();
        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            if (c < 0 || c > 127) {
                return false;
            }
        }
        return true;
    }

    /**
     * 转换字符串中非ascii码字符为utf-8编码的16进制数字格式
     *
     * @param value 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String urlEncodeKeepAscii(String value) {
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        int length = value.length();
        StringBuilder asciiString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = value.charAt(i);
            if (c < 0 || c > 127) {
                try {
                    String hexRaw = String.format("%x", new BigInteger(1, value.substring(i, i + 1).getBytes("UTF-8")));
                    char[] hexRawArr = hexRaw.toCharArray();
                    final String SEP = "%";
                    for (int j = 0; j < hexRawArr.length; j++) {
                        asciiString.append(SEP).append(hexRawArr[j]).append(hexRawArr[++j]);
                    }
                } catch (UnsupportedEncodingException e) {
                }
            } else {
                asciiString.append(c);
            }
        }
        return asciiString.toString();
    }

    /**
     * 下载文件大小标志位，大于100KB以MB为单位显示，否则以KB单位显示
     */
    private static final double SIZE_100KB_FOR_BYTE = 100000.0;
    public static final int TRAFFIC_FEED_RATE = 1024; // 流量单位进率
    public static final long PRICISION = 1; // 精度，因为只显示两位，精确到0.001即可。

    /**
     * 获得文件大小的字符串描述
     *
     * @param fileSize 传入文件大小,以Byte为单位
     * @return 若小于100K, 则返回"*.*K". 若大于则返回"*.**M"
     */
    public static String formatFileSize(double fileSize) {
        String strReturnVal = "";
        if (fileSize <= SIZE_100KB_FOR_BYTE) {
            DecimalFormat df = new DecimalFormat("#.#");
            strReturnVal = df.format(fileSize / 1024.0) + "K";
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            strReturnVal = df.format(fileSize / (1024.0 * 1024.0)) + "M";
        }

        return strReturnVal;
    }

    /**
     * 获得文件大小的字符串描述
     *
     * @param fileSize 传入文件大小,以Byte为单位
     * @return 若小于100K, 则返回"*.*K". 若大于则返回"*.**M"
     */
    public static String formatFileSize(long fileSize) {
        double tmpFileSize = fileSize * 1.0;
        return StringUtils.formatFileSize(tmpFileSize);
    }

    /**
     * 获取文件大小的字符串描述
     *
     * @param fileSize      文件大小，byte为单位
     * @param defaultString 默认值
     * @return 正常时返回实际的描述，否则返回默认值
     */
    public static String formatFileSize(long fileSize, String defaultString) {
        if (fileSize < 0) {
            return defaultString;
        }
        String returnValString = formatFileSize(fileSize);
        if (TextUtils.isEmpty(returnValString)) {
            return defaultString;
        } else {
            return returnValString;
        }
    }

    /**
     * 给数值加上单位，如果小于1MB，则去掉小数位，直接输出"nKB"，
     * 否则保留小数点后两位并加上对应的单位（MB\GB\TB）
     *
     * @param value 实际容量，以byte为单位
     * @return
     */
    public final static String formatFileSize(float value) {
        String strResult = "0KB";
        if (value - PRICISION > 0) {
            double dValue = (double) value / TRAFFIC_FEED_RATE;
            if (dValue < TRAFFIC_FEED_RATE) {
                strResult = converTextUnit(dValue + "KB");
            } else if ((dValue = dValue / TRAFFIC_FEED_RATE) < TRAFFIC_FEED_RATE) {
                strResult = converSizeFormatText(dValue + "MB");
            } else if ((dValue = dValue / TRAFFIC_FEED_RATE) < TRAFFIC_FEED_RATE) {
                strResult = converSizeFormatText(dValue + "GB");
            } else {
                dValue = dValue / TRAFFIC_FEED_RATE;
                strResult = converSizeFormatText(dValue + "TB");
            }
        }
        return strResult;
    }

    /**
     * long型时间转成格式化字符串
     *
     * @param time 大多数情况下应为{@link System#currentTimeMillis()}
     * @return {@link StringUtils#TIME_FORMAT_STRING}格式化以后的时间如2018-04-02 11:21:23:850
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatTime(long time) {
        return new SimpleDateFormat(TIME_FORMAT_STRING).format(time);
    }

    /**
     * 去除小数位
     *
     * @param text
     * @return
     */
    private final static String converTextUnit(String text) {
        int index = text.indexOf("."); // 对小数进行控制，没有采用Format的函数
        if (index > 0) {
            text = StringUtils.merge(text.substring(0, index), text.substring(text.length() - 2));
        }
        return text;
    }

    /**
     * 保留小数点后两位
     *
     * @param text
     * @return
     */
    private final static String converSizeFormatText(String text) {
        int index = text.indexOf("."); // 对小数进行控制，没有采用Format的函数
        int indexB = text.indexOf('B');// 防止小数位数不够2位

        if (index > 0 && (indexB - index > 4)) {
            text = StringUtils.merge(text.substring(0, index + 3), text.substring(text.length() - 2));
        }

        return text;
    }
}
