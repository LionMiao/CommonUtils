/*
 * ****************************************************************************
 * Copyright (C) 2005-2015 UCWEB Corporation. All rights reserved
 * Creation    : 2015/7/1
 * Author      : miaozh@ucweb.com
 * History     : Creation, 2015/7/1, miaozh, Create the file
 * ****************************************************************************
 */
package com.ehang.commonutils.io;

import android.text.TextUtils;


import com.ehang.commonutils.string.StringUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>文件路径工具类</b>
 * <p/>
 * 拼接路径，截取目录名、文件名，获取文件名，获取文件后缀，去除文件后缀，判断文件名是否正确等
 *
 */
public final class FilePathUtils {
    /**
     * 由文件路径获取文件后缀,不包括'.'
     *
     * @param path 文件名或文件的完整路径
     * @return 有则返回后缀，文件名为null时返回null，无后缀时返回"";
     */
    public static String getFileSuffix(String path) {
        if (TextUtils.isEmpty(path)) {
            return path;
        }

        String suffix = "";
        int dotInx = path.lastIndexOf('.');
        if ((dotInx > -1) && (dotInx + 1 < path.length())) {
            suffix = path.substring(dotInx + 1);
        }
        return suffix;
    }

    /**
     * 根据文件的url获取文件后缀
     *
     * @param url 文件的url，可能携带参数
     * @return 有则返回后缀，无后缀返回"",路径为null时返回null;
     */
    public static String getFileSuffixFromUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }

        int query = url.lastIndexOf('?');
        if (query > 0) {
            url = url.substring(0, query);
        }
        int fileNamePos = url.lastIndexOf('/');
        String fileName = 0 <= fileNamePos ? url.substring(fileNamePos + 1) : url;
        return getFileSuffix(fileName);
    }

    /**
     * 获取不带后缀的文件名
     *
     * @param path 从文件路径中截取不带后缀的文件名
     * @return 不带后缀的文件名
     */
    public static String getName(String path) {
        if (TextUtils.isEmpty(path)) {
            return path;
        }

        String name = "";
        int startPos = path.lastIndexOf('/');
        path = (startPos > -1 && startPos + 1 < path.length()) ? path.substring(startPos + 1) : path;
        int dotInx = path.lastIndexOf('.');
        if ((dotInx > -1) && (dotInx < path.length())) {
            name = path.substring(0, dotInx);
        }
        return name;
    }

    /**
     * 去除文件后缀
     *
     * @param fileName 文件名，如test.txt
     * @return 返回不带后缀的文件名, 文件名为空时返回本身
     */
    public static String trimSuffix(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return fileName;
        }

        int index = fileName.lastIndexOf('.');
        if (index >= 0) {
            fileName = fileName.substring(0, index);
        }
        return fileName;
    }

    /**
     * 获取文件名，包含后缀，如:test.txt
     *
     * @param path 文件的完整路径
     * @return 如果文件路径为空或者不包含"/",返回其本身，该路径是一个目录，则返回""。
     */
    public static String getFileName(String path) {
        if (TextUtils.isEmpty(path) || !path.contains("/")) {
            return path;
        }

        int lastSlashIndex = path.lastIndexOf('/');
        return (lastSlashIndex + 1 <= path.length()) ? path.substring(lastSlashIndex + 1) : path;
    }

    /**
     * 获取文件夹路径，包含'/'
     *
     * @param path 文件的完整路径
     * @return 当路径为null时返回null, 路径不包含'/'时返回""
     */
    public static String getDirectoryPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return path;
        }
        if (!path.contains("/")) {
            return "";
        }
        int lastSlashIndex = path.lastIndexOf('/');
        return (lastSlashIndex + 1 <= path.length()) ? path.substring(0, lastSlashIndex + 1) : path;
    }

    /**
     * 返回最后一个路径分隔符的位置
     *
     * @param path 文件的完整路径
     * @return 存在返回对应的位置，不存在时返回'/'
     */
    public static int getLastSlashIndex(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }

        int unixSlash = path.lastIndexOf("/");
        int windowsSlash = path.lastIndexOf("\\");
        return Math.max(unixSlash, windowsSlash);
    }

    /**
     * 把file://开头的local uri字符串的file://前缀去掉
     *
     * @param uriStr file uri,如"file:///data/data/test/abc"
     * @return 去除file://剩下的部分，当路径为空时直接返回传入的值
     */
    public static String trimFileUriPrefix(String uriStr) {
        if (TextUtils.isEmpty(uriStr)) {
            return uriStr;
        }

        String preFix = "file://";
        if (uriStr.startsWith(preFix)) {
            uriStr = uriStr.substring(preFix.length());
        }
        return uriStr;
    }

    /**
     * 非法字符集，当文件名中包括以下任意字符时，表示该文件名不合法
     */
    private static final String[] mCs = new String[]{"/", "\\", "?", "*", ":", "<", ">", "|", "\""};

    /**
     * 判断文件名是否合法，
     * 不能包含"/", "\\", "?", "*", ":", "<", ">", "|", "\""中任意字符
     * 也不能包含[0xD800, 0xDFFF]代理区字符
     *
     * @param fileName 文件名
     * @return 文件名不为空且不包含特殊字符和代理区字符时返回true，否则返回false
     */
    public static boolean isFileNameCorrect(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        
        boolean res = true;
        fileName = fileName.trim();
        if (StringUtils.containsSurrogateChar(fileName)) {
            res = false;
        } else {
            // SpiraTeam ID: RQ004896 文件管理优化-修改文字提示
            // 目前该方法只用于文件管理逻辑中，当名称包含"|"时，文件会创建失败 ，添加“|”的过滤 zhoulj20100701
            // Mantis 0028620 keyi_100423 去掉|的限制
            for (String c : mCs) {
                if (fileName.contains(c)) {
                    res = false;
                }
            }
        }
        return res;
    }

    /**
     * 移除文件名中的非法字符{"/", "\\", "?", "*", ":", "<", ">", "|", "\""}
     * 和[0xD800, 0xDFFF]代理区字符
     *
     * @param fileName 文件名
     * @return 剔除不合法字符之后剩下的文件名，文件名为空时直接返回其自身
     */
    public static String fixFileName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return fileName;
        }

        for (String c : mCs) {
            fileName = fileName.replace(c, "");
        }
        if (StringUtils.containsSurrogateChar(fileName)) {
            fileName = StringUtils.removeSurrogateChars(fileName);
        }
        return fileName;
    }

    /**
     * 缩短文件名长度，文件名总长度（包括后缀）不得大于maxLen。
     * 当maxLen小于文件后缀的长度时，仅保留文件后缀
     *
     * @param fileName 文件名
     * @param maxLen   文件名的最大长度
     * @return 缩减后的文件名
     */
    public static String reduceFileName(String fileName, int maxLen) {
        if (null == fileName || maxLen <= 0 || fileName.length() < maxLen) {
            return fileName;
        }

        String suffix = getFileSuffix(fileName);
        // 没有后缀名，直接从文件名中截取
        if (TextUtils.isEmpty(suffix)) {
            return fileName.substring(0, maxLen);
        }

        int nameEndIndex = maxLen - suffix.length() - 1;
        if (nameEndIndex < 0) {
            nameEndIndex = 0;
        }
        return fileName.substring(0, nameEndIndex) + "." + suffix;
    }

    /**
     * 将目录和文件名拼接成完整的路径
     *
     * @return 如果path或者name有一个为null，则认为是非法数据，则返回null
     * 否则返回完整路径
     */
    public static String joinDirAndFileName(String directory, String fileName) {
        if (null == directory || null == fileName) {
            return null;
        }

        int prefixLength = directory.length();
        boolean haveSlash = (prefixLength > 0 && directory.charAt(prefixLength - 1) == File.separatorChar);
        if (!haveSlash) {
            haveSlash = (fileName.length() > 0 && fileName.charAt(0) == File.separatorChar);
        } else {
            if (fileName.length() > 0 && fileName.charAt(0) == File.separatorChar) {
                //如果两个都有“/”，需要删除一个
                directory = directory.substring(0, prefixLength - 1);
            }
        }
        return haveSlash ? (directory + fileName) : (directory + File.separatorChar + fileName);
    }

    /**
     * 把一个路径拆成目录及文件名两部分
     *
     * @return last component without "/".
     * 如果文件路径为null或者文件路径中不包含”/“，则返回null
     */
    public static String[] splitPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        int index = getLastSlashIndex(path);
        String[] nameArray = null;
        if (index >= 0) {
            nameArray = new String[2];
            nameArray[0] = path.substring(0, index);
            if (index + 1 <= path.length()) {
                nameArray[1] = path.substring(index + 1);
            } else {
                nameArray[1] = "";
            }
        }
        return nameArray;

    }

    /**
     * 提供重复文件名生成功能
     * 例如传入的文件名是aa,会返回aa(1)，传入aa(1)，会返回aa(2) 匹配文件名的结尾是否是 filename(index)
     * 这样的形式，匹配到的时候，取出index加上1，匹配不到直接在后面拼上字符串(1)
     *
     * @param name 文件名，不带后缀
     * @return 不重复的文件名，如aa(1)
     */
    public static String generateRepeatFileName(String name){
        if (null == name) {
            return null;
        }

        String result = "";
        try {
            if (name.endsWith(File.separator)) {
                name = name.substring(0, (name.length() - 1));
            }
            String reg = "\\((\\d+)\\)$";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(name);
            if (m.find()) {
                String a = m.group(1);
                int b = Integer.parseInt(a);
                result = name.replace(a, String.valueOf(++b));
            } else {
                result = StringUtils.merge(name, "(1)");
            }
        } catch (Exception e) {
            // 异常处理，后面强制拼上字符串"(1)"，避免可能引起死循环
            result = StringUtils.merge(result, "(1)");
            e.printStackTrace();
        }

        return result;
    }
}
