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

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.security.*;
import java.util.*;

/**
 * <p>
 * 系统签名算法
 * </p>
 *
 * @Author javaeer(javaeer @ aliyun.com)
 * @Date 2019/7/9 11:10
 * @Version 1.0
 */
@Slf4j
public class SignUtils {

    private static final String SIGN_ALGORITHM = "SHA256withRSA";

    private static final String BASE_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    /**
     * 对参数执行签名
     *
     * @param params
     * @return
     */
    public static String sign(Map<String, String> params) {

        StringBuffer sb = new StringBuffer();

        List<Map.Entry<String, String>> entries = Lists.newArrayList(params.entrySet());

        //根据Key值先行排序
        Collections.sort(entries, Comparator.comparing(Map.Entry::getKey));
        //排除value为空的项不参与签名
        for (Map.Entry<String, String> entry : entries) {
            String k = entry.getKey();
            Object v = entry.getValue();
            //空值不传递，不参与签名组串
            if (null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        log.info("before sign:【{}】", sb.toString());
        String digest = MessageDigestUtils.digest(sb.toString());
        log.info("after sign:【{}】", digest);
        return digest;
    }

    /**
     * 验证签名
     * @param string
     * @param privateKey
     * @return
     */

    public static String sign(String string, PrivateKey privateKey) {
        try {
            Signature sign = Signature.getInstance(SIGN_ALGORITHM);
            sign.initSign(privateKey);
            sign.update(string.getBytes());
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持SHA256withRSA", e);
        } catch (SignatureException e) {
            throw new RuntimeException("签名计算失败", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效的私钥", e);
        }
    }


    /**
     * 生成随机字符串
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String genRandomStr(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE_STR.length());
            sb.append(BASE_STR.charAt(number));
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        /*log.info(System.currentTimeMillis());

        Map sigMap = Maps.newHashMap();
        sigMap.put("app_id", "sms-instance");
        sigMap.put("app_security", "51ec443660fa0a6e751aa19e19275a75");
        sigMap.put("timestamp", System.currentTimeMillis() + "");
        sigMap.put("private_key", "javaeer");
//
        String s = getSignature(sigMap);

//        String s = MessageDigestUtils.digest("123456");
        log.info(s);*/


        log.info(genRandomStr(32));
    }
}
