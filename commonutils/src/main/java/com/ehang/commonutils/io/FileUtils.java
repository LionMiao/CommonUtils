/*
 * ****************************************************************************
 * Copyright (C) 2005-2012 UCWEB Corporation. All rights reserved
 * File        : 2012-7-2
 * <p/>
 * Description : TrafficData.java
 * <p/>
 * Creation    : 2012-11-13
 * Author      : raorq@ucweb.com
 * History     : Creation, 2012-11-13, raorq, Create the file
 * ****************************************************************************
 */
package com.ehang.commonutils.io;

import android.content.Context;
import android.content.res.AssetManager;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.ehang.commonutils.ReflectionHelper;
import com.ehang.commonutils.exception.NullArgumentException;
import com.ehang.commonutils.string.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <b>文件相关的所有操作</b>
 * <p/>
 * 计算文件或文件夹大小，读文件，写文件，拷贝文件，删除文件，判断文件是否存在，创建文件，设置文件文件权限
 */
public final class FileUtils {

    /**
     * write mode, only for RandomAccessFile
     */
    public static final byte WRITE_POS_CURRENT_POS = 0;
    /**
     * 从文件开始处写入
     */
    public static final byte WRITE_POS_BEGIN = 1;
    /**
     * 在文件结束处写入 追加
     */
    public static final byte WRITE_POS_END = 2;
    /**
     * 在文件指定位置写入
     */
    public static final byte WRITE_POS_SPECIFIED = 3;
    public final static String EXT_BAK = ".bak";

    /**
     * 计算文件夹大小
     *
     * @param item  文件或文件夹
     * @param stack 递归传值栈，用于临时记录文件夹大小
     * @return 文件或文件夹的总大小
     */
    public static long calcFileSize(File item, long stack) {
        if (item == null || !item.exists()) {
            return stack;
        }
        if (item.isDirectory()) {
            long temp = 0;
            File[] fileList = item.listFiles();
            for (File subitem : fileList) {
                temp += calcFileSize(subitem, 0);
            }
            return stack + temp;
        } else {
            return stack + item.length();
        }
    }

    /**
     * Reads a text file.
     *
     * @param path 文件的绝对路径
     * @return the lines of the text file
     * @throws FileNotFoundException when the file was not found
     * @throws IOException           when file could not be read.
     */
    public static String[] readTextFile(String path) throws IOException {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return readTextFile(new File(path));
    }

    /**
     * Reads a text file.
     *
     * @param file the text file
     * @return the lines of the text file
     * @throws FileNotFoundException when the file was not found
     * @throws IOException           when file could not be read.
     */
    public static String[] readTextFile(File file) throws IOException {
        return readTextFile(file, null);
    }

    /**
     * Reads a text file.
     *
     * @param file     the text file
     * @param encoding the encoding of the textfile
     * @return the lines of the text file
     * @throws FileNotFoundException when the file was not found
     * @throws IOException           when file could not be read.
     */
    public static String[] readTextFile(File file, String encoding) throws IOException {
        if (file == null || !file.exists()) {
            return null;
        }
        return readTextFile(new FileInputStream(file), encoding);
    }

    /**
     * Reads the text from the given input stream in the default encoding.
     *
     * @param in the input stream
     * @return the text contained in the stream
     * @throws IOException when stream could not be read.
     */
    public static String[] readTextFile(InputStream in) throws IOException {
        return readTextFile(in, null);
    }

    /**
     * Reads the text from the given input stream in the default encoding.
     *
     * @param in       the input stream
     * @param encoding the encoding of the textfile
     * @return the text contained in the stream
     * @throws IOException when stream could not be read.
     */
    public static String[] readTextFile(InputStream in, String encoding) throws IOException {
        if (in == null) {
            return null;
        }
        ArrayList<String> lines = new ArrayList<String>();
        BufferedReader bufferedIn;
        if (encoding != null) {
            bufferedIn = new BufferedReader(new InputStreamReader(in, encoding));
        } else {
            bufferedIn = new BufferedReader(new InputStreamReader(in));
        }
        String line;
        while ((line = bufferedIn.readLine()) != null) {
            lines.add(line);
        }
        bufferedIn.close();
        in.close();
        return lines.toArray(new String[lines.size()]);
    }

    /**
     * @see #writeTextFile(File, String[], boolean)
     */
    public static void writeTextFile(File file, Collection<String> lines) throws IOException {
        writeTextFile(file, lines, false);
    }

    /**
     * @see #writeTextFile(File, String[], boolean)
     */
    public static void writeTextFile(File file, Collection<String> lines, boolean append) throws IOException {
        if (lines == null) {
            return;
        }
        writeTextFile(file, lines.toArray(new String[lines.size()]), append);
    }

    /**
     * Writes (or creates) a text file.
     *
     * @param file  待写入的文件，如果该文件不存在，则会新建（包括目录）
     * @param lines the text lines of the file
     * @throws IOException           when there is an input/output error during the saving
     * @throws NullArgumentException 文件为null时
     */
    public static void writeTextFile(File file, String[] lines, boolean isAppend) throws IOException {
        if (file == null) {
            throw new NullArgumentException("file");
        }
        if (lines == null) {
            return;
        }
        File parentDir = file.getParentFile();
        if ((parentDir != null) && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(file, isAppend));
            for (int i = 0; i < lines.length; i++) {
                out.println(lines[i]);
            }
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * Writes the given textlines into the specified file.
     *
     * @param file     the file to which the text should be written
     * @param lines    the text lines of the file
     * @param encoding the encoding, e.g. "UTF8", null when the default encoding should be used
     * @throws IOException           when there is an input/output error during the saving
     * @throws NullArgumentException 文件为null时
     */
    public static void writeTextFile(File file, String[] lines, String encoding) throws IOException {
        if (file == null) {
            throw new NullArgumentException("file");
        }
        if (lines == null) {
            return;
        }
        File parentDir = file.getParentFile();
        if ((parentDir != null) && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        PrintWriter out = null;
        try {
            if (encoding != null) {
                out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
            } else {
                out = new PrintWriter(new FileWriter(file));
            }
            for (int i = 0; i < lines.length; i++) {
                out.println(lines[i]);
            }
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }


    /**
     * Copies the given files to the specified target directory.
     *
     * @param files     The files which should be copied, when an array element is null, it will be ignored.
     * @param targetDir The directory to which the given files should be copied to.
     * @return 拷贝成功返回true，否则返回false，注意当参数为null时也返回false，注意判断返回值
     * @throws IOException when there is an error while copying the file.
     */
    public static boolean copy(File[] files, File targetDir) throws IOException {
        return copy(files, targetDir, false);
    }

    /**
     * 拷贝文件到目标文件夹，当目标文件夹中没有相同文件时直接拷贝，当目标文件夹有相同文件并且允许覆盖时直接覆盖（不管目标文件是否比原文件新）
     *
     * @param files     The files which should be copied, when an array element is null, it will be ignored.
     * @param targetDir The directory to which the given files should be copied to.
     * @param overwrite true when existing target files should be overwritten even when they are newer
     * @return 拷贝成功返回true，否则返回false，注意当参数为null时也返回false，注意判断返回值
     * @throws IOException when there is an error while copying the file.
     */
    public static boolean copy(File[] files, File targetDir, boolean overwrite) throws IOException {
        if (files == null || files.length == 0 || targetDir == null) {
            return false;
        }
        String targetPath = targetDir.getAbsolutePath() + File.separatorChar;
        byte[] buffer = new byte[64 * 1024];
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file != null) {
                File targetFile = new File(targetPath + file.getName());
                if (!targetFile.exists() || overwrite) {
                    copy(file, targetFile, buffer);
                }
            }
        }
        return true;
    }

    /**
     * Copies a file.
     *
     * @param source The file which should be copied
     * @param target The file or directory to which the source-file should be copied to.
     * @throws FileNotFoundException when the source file was not found
     * @throws IOException           when there is an error while copying the file.
     */
    public static boolean copy(File source, File target) throws IOException {
        return copy(source, target, new byte[64 * 1024]);
    }

    /**
     * Copies a file.
     *
     * @param source The file which should be copied
     * @param target The file or directory to which the source-file should be copied to.
     * @param buffer A buffer used for the copying.
     * @return 当源文件为null或不存在或者目标文件为null时返回false，否则返回true
     * @throws FileNotFoundException when the source file was not found
     * @throws IOException           when there is an error while copying the file.
     */
    private static boolean copy(File source, File target, byte[] buffer) throws IOException {
        if (source == null || !source.exists() || target == null) {
            return false;
        }
        InputStream in = new FileInputStream(source);
        // create parent directory of target-file if necessary:
        File parent = target.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (target.isDirectory()) {
            target = new File(target, source.getName());
        }
        OutputStream out = new FileOutputStream(target);
        int read;
        try {
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } finally {
            try {
                in.close();
            } finally {
                out.close();
            }
        }
        return true;
    }

    /**
     * @see #writePropertiesFile(File, char, Map)
     */
    public static void writePropertiesFile(File file, Map<String, String> properties) throws IOException {
        writePropertiesFile(file, '=', properties);
    }

    /**
     * Writes the properties which are defined in the given HashMap into a textfile.
     * The notation in the file will be [name]=[value]\n for each defined property.
     *
     * @param file       the file which should be created or overwritten
     * @param delimiter  the character that separates a property-name from a property-value.
     * @param properties the properties which should be written.
     * @throws IOException           when there is an input/output error during the saving
     * @throws NullArgumentException 文件为null时
     */
    public static void writePropertiesFile(File file, char delimiter, Map<String, String> properties)
            throws IOException {
        if (null == file) {
            throw new NullArgumentException("file");
        }
        if (null == properties) {
            return;
        }
        Object[] keys = properties.keySet().toArray();
        Arrays.sort(keys);
        String[] lines = new String[keys.length];
        for (int i = 0; i < lines.length; i++) {
            Object value = properties.get(keys[i]);
            lines[i] = keys[i].toString() + delimiter + value.toString();
        }
        writeTextFile(file, lines, false);
    }

    /**
     * @see #copyDirectoryContents(File, File, boolean)
     */
    public static void copyDirectoryContents(File directory, String targetDirName, boolean update) throws IOException {
        if (TextUtils.isEmpty(targetDirName)) {
            return;
        }
        copyDirectoryContents(directory, new File(targetDirName), update);
    }

    /**
     * Copies the contents of a directory to the specified target directory.
     * 当update参数为false时，直接拷贝；
     * 当update参数为true时，仅当源文件比目标文件新时才拷贝
     *
     * @param directory the directory containing files
     * @param targetDir the directory to which the files should be copied to
     * @param update    set to true when files should be only copied when the source files
     *                  are newer compared to the target files.
     * @throws IOException              when a file could not be copied
     * @throws IllegalArgumentException when the directory is not a directory.
     * @throws NullArgumentException    文件为null时
     */
    public static void copyDirectoryContents(File directory, File targetDir, boolean update) throws IOException {
        if (null == directory || null == targetDir) {
            throw new NullArgumentException("directory and targetDir");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Cannot copy contents of the file [" + directory.getAbsolutePath()
                    + "]: specify a directory instead.");
        }
        String[] fileNames = directory.list();
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            File file = new File(directory, fileName);
            if (file.isDirectory()) {
                copyDirectoryContents(file, targetDir.getAbsolutePath() + File.separatorChar + fileName, update);
            } else {
                File targetFile = new File(targetDir, fileName);
                // update only when the source file is newer:
                if ((!targetFile.exists()) || !update || (file.lastModified() > targetFile.lastModified())) {
                    copy(file, targetFile);
                }
            }
        }
    }

    /**
     * Deletes a file or a directory.
     *
     * @param file the file or directory which should be deleted.
     * @return true when the file could be deleted
     */
    public static boolean delete(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = delete(new File(file, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // The directory is now empty so delete it
        return file.delete();
    }

    /**
     * List all files in the given directory satisfy the filter.
     *
     * @param dir       Directory to list.
     * @param filter    A file filter.
     * @param recursive If true, all the sub directories will be listed too.
     * @return Array of files in the given directory and satisfy the filter.
     */
    public static List<File> listFiles(File dir, FileFilter filter, boolean recursive) {
        List<File> result = new ArrayList<File>();
        if (FileUtils.isFileExists(dir) && dir.isDirectory()) {
            File[] fArray = dir.listFiles(filter);
            if (fArray != null) {
                List<File> fList = Arrays.asList(fArray);
                if (!recursive) {
                    return fList;
                }

                LinkedList<File> linkedList = new LinkedList<File>(fList);
                while (!linkedList.isEmpty()) {
                    File f = linkedList.removeFirst();
                    result.add(f);
                    if (f.isDirectory()) {
                        File[] array = f.listFiles(filter);
                        if (array != null) {
                            for (int i = 0; i < array.length; i++) {
                                linkedList.addLast(array[i]);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Extracts the bytes from a file.
     *
     * @param file the file from which the bytes should be extracted from
     * @return 文件不存在或参数为<code>null</code>时返回<code>null</code>
     * @throws IOException
     */
    public static byte[] getBytes(File file) throws IOException {
        if (!isFileExists(file)) {
            return null;
        }
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            byte[] buffer = new byte[4096];
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            int read;
            while ((read = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            return bos.toByteArray();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }

    /**
     * 生成备份文件的存放路径
     *
     * @param path 原文件路径
     * @return 备份文件的路径
     */
    public static String genBackupFilePath(String path) {
        return path + EXT_BAK;
    }

    /**
     * @see {@link #writeBytes(String, String, byte[], byte[], int, int, boolean)}
     */
    public static boolean writeBytes(String directory, String fileName, byte[] headData, byte[] bodyData) throws IOException {
        if (bodyData == null) {
            return false;
        }
        return writeBytes(directory, fileName, headData, bodyData, 0, bodyData.length, false);
    }

    /**
     * @see {@link #writeBytes(String, String, byte[], byte[], int, int, boolean)}
     */
    public static boolean writeBytes(String directory, String fileName, byte[] data) throws IOException {
        if (data == null) {
            return false;
        }
        return writeBytes(directory, fileName, data, 0, data.length);
    }

    /**
     * 请尽量将这个函数放入ThreadManager的后台线程执行，防止引入严重卡顿
     *
     * @param directory  文件存放的目录,路径以"/"结尾
     * @param fileName   文件名
     * @param headData   文件头
     * @param bodyData   文件内容
     * @param bodyOffset bodyData数组的偏移量
     * @param bodyLen    bodyData数组的长度
     * @param forceFlush 请慎重使用这个参数，如果设置为true可能导致严重卡顿，甚至ANR，如果不是极为重要的数据，请设置为false。
     * @return 成功写入返回true，否则返回false
     */
    public static boolean writeBytes(String directory, String fileName, byte[] headData, byte[] bodyData,
                                     int bodyOffset, int bodyLen, boolean forceFlush) throws IOException {
        if (TextUtils.isEmpty(directory) || TextUtils.isEmpty(fileName) || bodyData == null) {
            return false;
        }
        String tempFileName = System.currentTimeMillis() + fileName;
        File tempFile = createNewFile(directory + tempFileName);
        boolean result = writeBytes(tempFile, headData, bodyData, bodyOffset, bodyLen, forceFlush);
        if (!result) {
            return false;
        }

        String srcPath = directory + fileName;
        if (!tempFile.renameTo(new File(srcPath))) {
            // rename srcPath到bakPath后再 delete bakPath，替代直接 delete srcPath
            /**
             * 这部分有点绕，首先删除.bak文件，然后将原文件重命名为.bak文件，然后将tempFile重命名为原文件，
             * 如果成功则删除.bak文件，如果失败则返回false
             */
            String bakPath = genBackupFilePath(srcPath);
            delete(bakPath);
            new File(srcPath).renameTo(new File(bakPath));
            result = tempFile.renameTo(new File(srcPath));
            if (!result) {
                return false;
            }

            delete(bakPath);
        }

        return true;
    }

    /**
     * 将指定内容写入到文件的指定位置
     *
     * @param path         文件的完整路径
     * @param mode         {@link #WRITE_POS_BEGIN,#WRITE_POS_CURRENT_POS,#WRITE_POS_END,#WRITE_POS_SPECIFIED}之一
     * @param specifiedPos 需要写入的位置
     * @param data         需要写入的数据
     * @return 成功写入返回true，否则返回false
     * @throws IOException
     */
    public static boolean writeBytes(String path, byte mode, int specifiedPos, byte[] data) throws IOException {
        RandomAccessFile raf = openFile(path, false);
        try {
            return writeBytes(raf, mode, specifiedPos, data);
        } finally {
            IOUtil.safeClose(raf);
        }
    }

    /**
     * 将指定内容写入文件的指定位置，分为四种模式
     * {@link #WRITE_POS_BEGIN,#WRITE_POS_CURRENT_POS,#WRITE_POS_END,#WRITE_POS_SPECIFIED}
     *
     * @param raf,          RandomAccessFile object
     * @param mode
     * @param specifiedPos, the position start to write
     * @param data,         byte[] type
     * @return 成功写入返回true，否则返回false
     */
    public static boolean writeBytes(RandomAccessFile raf, byte mode, int specifiedPos, byte[] data) throws IOException {
        if (null == raf || data == null || data.length == 0) {
            return false;
        }
        switch (mode) {
            case WRITE_POS_CURRENT_POS:
                break;
            case WRITE_POS_BEGIN:
                raf.seek(0);
                break;
            case WRITE_POS_END: {
                long len = raf.length();
                raf.seek(len);
                break;
            }
            case WRITE_POS_SPECIFIED: {
                raf.seek(specifiedPos);
                break;
            }
            default:
                break;
        }
        raf.write(data);
        return true;
    }

    /**
     * 详见{@link #writeBytes(String, String, byte[], byte[], int, int, boolean)}
     */
    public static boolean writeBytes(String directory, String fileName, byte[] data, int offset, int len) throws IOException {
        return writeBytes(directory, fileName, null, data, offset, len, false);
    }

    /**
     * 请尽量将这个函数放入ThreadManager的后台线程执行，防止引入严重卡顿
     *
     * @param file       待写入文件
     * @param headData   文件头
     * @param bodyData   文件内容
     * @param bodyOffset bodyData的偏移量
     * @param bodyLen    写入文件的byte数组的长度
     * @param forceFlush 请慎重使用这个参数，如果设置为true可能导致严重卡顿，甚至ANR，如果不是极为重要的数据，请设置为false。
     * @return 成功写入返回true，否则返回false
     */
    public static boolean writeBytes(File file, byte[] headData, byte[] bodyData, int bodyOffset, int bodyLen,
                                     boolean forceFlush) throws IOException {
        FileOutputStream fileOutput = null;
        try {
            fileOutput = new FileOutputStream(file);
            if (headData != null) {
                fileOutput.write(headData);
            }
            fileOutput.write(bodyData, bodyOffset, bodyLen);
            fileOutput.flush();
            if (forceFlush) {
                FileDescriptor fd = fileOutput.getFD();
                if (fd != null) {
                    fd.sync(); // 立刻刷新，保证文件可以正常写入;
                }
            }
            return true;
        } finally {
            IOUtil.safeClose(fileOutput);
        }
    }

    /**
     * 详见{@link #writeBytes(File, byte[], byte[], int, int, boolean)}
     */
    public static boolean writeBytes(File file, byte[] data, int offset, int len) throws IOException {
        return writeBytes(file, null, data, offset, len, false);
    }

    /**
     * 如果文件已存在，则先删除，然后创建新的文件
     *
     * @param path 绝对路径
     * @return 创建成功返回true，否则返回false
     * @throws IOException
     */
    public static File createNewFile(String path) throws IOException {
        return createNewFile(path, false);
    }

    /**
     * 传入路径，获取文件大小，不存在则文件大小为0
     *
     * @param path 文件的绝对路径
     * @return 文件大小 单位Byte
     */
    public static long getFileSize(String path) {
        long size = 0;
        File file = new File(path);
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    /**
     * 以RandomAccess模式打开一个文件
     *
     * @param path   文件路径
     * @param append 为true时，跳转到文件末尾，否则在文件开头位置
     * @throws IOException
     */
    public static RandomAccessFile openFile(String path, boolean append) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "rw");
        if (append) {
            long len = file.length();
            if (len > 0) {
                file.seek(len);
            }
        }
        return file;
    }

    /**
     * 删除文件或文件夹
     *
     * @param path 文件的绝对路径
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String path) {
        return delete(new File(path));
    }

    /**
     * 创建文件，当文件已存在并且append为false时，先删除原文件，然后再新建一个空文件
     *
     * @param path   ：文件路径
     * @param append ：若存在是否插入原文件
     * @return 创建成功返回true，否则返回false
     */
    public static File createNewFile(String path, boolean append) throws IOException {
        File newFile = new File(path);
        if (!append) {
            if (newFile.exists()) {
                newFile.delete();
            }
        }
        if (!newFile.exists()) {
            File parent = newFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            newFile.createNewFile();
        }
        return newFile;
    }

    /**
     * 读取文件内容
     *
     * @param path 文件的完整路径
     * @return 返回文件内容，文件不存在时返回null
     */
    public static byte[] readBytes(String path) throws IOException {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return readBytes(new File(path));
    }

    /**
     * 读取文件内容
     *
     * @param file 待读取的文件
     * @return 返回文件内容，如果文件不存在时返回null
     * @throws IOException
     */
    public static byte[] readBytes(File file) throws IOException {
        if (!isFileExists(file)) {
            return null;
        }
        FileInputStream fileInput = new FileInputStream(file);
        try {
            return IOUtil.readFullBytes(fileInput);
        } finally {
            IOUtil.safeClose(fileInput);
        }
    }


    /**
     * 复制一个文件，将原文件的内容直接追加到目标文件的末尾
     *
     * @param sourceFile 源文件
     * @param destFile   目标文件
     * @throws IOException
     */
    private static void append(File sourceFile, File destFile) throws IOException {
        copy(sourceFile, destFile, true);
    }

    /**
     * 拷贝文件
     *
     * @param source 源文件
     * @param target 目标文件
     * @param append true：如果目标文件存在，则将原文件内容追加到目标文件末尾，否则正常拷贝
     * @throws IOException
     * @throws NullArgumentException 源文件或目标文件为空时
     */
    private static void copy(File source, File target, boolean append) throws IOException {
        if (null == source || null == target) {
            throw new NullArgumentException("sourceFile and destFile");
        }
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target, append));
        byte[] b = new byte[1024 * 8];
        int len;
        try {
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
        } finally {
            IOUtil.safeClose(bis);
            IOUtil.safeClose(bos);
        }

    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return 存在返回true，否则返回false
     */
    public static boolean isFileExists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        try {
            File file = new File(path);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件或者文件夹
     * @return false：文件为空或者不存在。
     */
    public static boolean isFileExists(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    /**
     * 拷贝asset目录下文件到指定文件
     *
     * @param assets {@link AssetManager}
     * @param source 相对路径
     * @param target 绝对路径
     * @throws NullArgumentException 参数为空时
     */
    public static void copyAssetFile(AssetManager assets, String source, String target) throws IOException {
        if (assets == null || source == null || target == null) {
            throw new NullArgumentException(null);
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assets.open(source);
            out = new FileOutputStream(target);
            byte[] buffer = new byte[8096];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } finally {
            IOUtil.safeClose(in);
            IOUtil.safeClose(out);
        }
    }

    /**
     * 判断传入的某个目录是否存在，如果不存在，会创建该目录，如果该目录已经被同名文件占用，会在传入路径后面拼上"(index)"，再创建目录
     * 如果当前路径不可写，有可能死循环，这个问题由下载模块后续处理 @zhangyf
     *
     * @param path 文件的绝对路径
     * @return 路径为空时返回null，否则返回一个创建成功的可用路径
     */
    public static String makeDir(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        File file = new File(path);
        if (file.isDirectory()) {
            return path;
        }

        String parentPath = file.getParent();
        if (!TextUtils.isEmpty(parentPath)) {
            // 递归处理父目录被文件占用的情况
            path = makeDir(parentPath);
        }

        String name = file.getName();
        if (!TextUtils.isEmpty(name)) {
            if (path.length() > 1) {
                path = StringUtils.merge(path, "/");
            }
            path = StringUtils.merge(path, name);
            path = ensureDirExits(path);
        }

        return path;
    }

    /**
     * 传入的路径的父目录下，可能已经存在一个同名的文件
     * 这种情况下会使用path(index)的方式获取可用的路径，确保返回一个可用的文件夹路径
     */
    private static String ensureDirExits(String path) {
        do {
            File file = new File(path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    return path;
                } else {
                    path = FilePathUtils.generateRepeatFileName(path);
                    continue;
                }
            }
            break;
        } while (true);

        File file = new File(path);
        boolean successful = file.mkdir();
        if (!successful) {
            // 有可能文件正在创建中或其他异常情况，这时候通过file.exits()无法正确判断文件是否存在
            // 故针对这种情况做兼容处理，把目标文件删除，并且创建一个目标文件的index+1的文件，保证用户能正常下载。
            file.delete();
            String resultPath = FilePathUtils.generateRepeatFileName(path);
            file = new File(resultPath);
            if (!file.exists()) {
                successful = file.mkdir();
            }

            if (successful || (file.exists() && file.isDirectory())) {
                return resultPath;
            }
        }

        return path;
    }

    /**
     * 如果本地有相同的文件，则把重复文件删除
     *
     * @param path 文件路径
     */
    public static void delDumplicateFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 设置文件读写模式对应的文件权限
     *
     * @param name             文件名
     * @param mode             读写模式
     * @param extraPermissions 其他权限
     */
    public static void setFilePermissionsFromMode(String name, int mode,
                                                  Integer extraPermissions) throws ClassNotFoundException {
        if (null == name) {
            return;
        }
        if (extraPermissions == null) {
            extraPermissions = 0;
        }
        Class fileUtils = Class.forName("android.os.FileUtils");
        int S_IRUSR = (int) ReflectionHelper.getStaticFieldValue(fileUtils, "S_IRUSR");
        int S_IWUSR = (int) ReflectionHelper.getStaticFieldValue(fileUtils, "S_IWUSR");
        int S_IRGRP = (int) ReflectionHelper.getStaticFieldValue(fileUtils, "S_IRGRP");
        int S_IWGRP = (int) ReflectionHelper.getStaticFieldValue(fileUtils, "S_IWGRP");
        int S_IROTH = (int) ReflectionHelper.getStaticFieldValue(fileUtils, "S_IROTH");
        int S_IWOTH = (int) ReflectionHelper.getStaticFieldValue(fileUtils, "S_IWOTH");


        int perms = S_IRUSR | S_IWUSR
                | S_IRGRP | S_IWGRP
                | extraPermissions;
        if ((mode & Context.MODE_WORLD_READABLE) != 0) {
            perms |= S_IROTH;
        }
        if ((mode & Context.MODE_WORLD_WRITEABLE) != 0) {
            perms |= S_IWOTH;
        }
        ReflectionHelper.invokeObjectMethod(fileUtils, "setPermissions", new Class[]{String.class, Integer.class, Integer.class, Integer.class}, new Object[]{name, perms, -1, -1});
    }

    /**
     * 解析properties文件
     *
     * @return Properties对象，解析失败时返回null
     */
    @Nullable
    public static Properties parseProperties(File file) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(file));
            return props;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
