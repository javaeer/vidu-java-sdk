/*
 *
 *     Copyright (c) 2019 - forever, javaeer All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the haoheio.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * Author: javaeer (javaeer@aliyun.com)
 *
 */

package cn.net.yunlou.vidu.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 对MessageDigest进行扩展，将加密后的字节码数组转换为字符串
 * 为什么不命名为MD5Utils?
 * 其实对于MD5加密，Java已经做得很好了，包含上述提到的，还有DigestUtils(commons-codec)
 * 由于使用DigestUtils需要另行引入包，所以选择了Java.security.MessageDigest
 * </p>
 *
 * @Author javaeer(javaeer @ aliyun.com)
 * @Date 2019/7/9 11:56
 * @Version 1.0
 */
@Slf4j
public class MessageDigestUtils {

    //16进制数组
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    protected static MessageDigest messageDigest;

    //初始化，静态代码块
    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("class Instance has Error :{}", e);
        }
    }


    public static String digest(byte[] bytes) {

        byte[] byteResult = messageDigest.digest(bytes);

        return bytesToHex(byteResult);
    }

    public static String digest(String s) {

        byte[] bytes = s.getBytes();

        return digest(bytes);
    }

    public static String digest(InputStream is) {

        byte[] byteResult = null;

        //设置缓冲区大小
        byte[] buffer = new byte[0x10000];

        int byteCount = 0;
        try {
            while ((byteCount = is.read(buffer)) > 0) {
                messageDigest.update(buffer, 0, byteCount);
            }
            byteResult = messageDigest.digest();

        } catch (IOException e) {
            log.error("read InputStream Error :{}", e);
            e.printStackTrace();
        }
        return bytesToHex(byteResult);
    }


    /**
     * <p>
     * 生成文件的MD5值
     * </p>
     *
     * @Param
     * @Return
     * @Author javaeer(javaeer @ aliyun.com)
     * @Date 2019/7/9 14:52
     */
    public static String digest(File file) {

        String result = "";
        InputStream is = null;

        if (!file.exists()) {
            return result;
        }
        try {
            is = new FileInputStream(file);
            result = digest(is);
        } catch (FileNotFoundException e) {
            log.error("Read File has happened An Error :{}", e);
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("Close FileInputStream has happened An Error :{}", e);
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        //new一个字符数组，这个就是用来组成结果字符串的
        // （解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultBytes = new char[bytes.length * 2];

        /// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : bytes) {
            resultBytes[index++] = hexDigits[(b & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
            resultBytes[index++] = hexDigits[b & 0xf];// 取字节中低 4 位的数字转换
        }

        return new String(resultBytes);
    }
}
