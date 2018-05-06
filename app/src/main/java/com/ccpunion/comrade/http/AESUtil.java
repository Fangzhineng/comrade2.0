package com.ccpunion.comrade.http;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 */
public class AESUtil {
    private static final String IV = "5075428636499153";

    public static String encrypt(String strKey, String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(strIn.getBytes("UTF-8"));
        return Base64.encode(encrypted);
    }

    public static String decrypt(String strKey, String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = Base64.decode(strIn);
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;
    }

    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes("UTF-8");
        byte[] arrB = new byte[16];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
        return skeySpec;
    }

    public void text() throws Exception {
        System.out.println(System.currentTimeMillis());
        String code = "02350993282b43cab2a2a2e56f84a83d,1495419491014,553209";
        String key = "179df2ae661e4a2d805d3004f4da66eb";
        String decryptKey = "d6eab18b2e40461a8f74bb7886ba6615";
        String codE = encrypt(key, code);
        System.out.println("原文：" + code);
        System.out.println("密钥：" + key);
        System.out.println("密文：" + codE);

        String serverKeyStr= "nIYl273krWp+LHpIsxThPZbQ2UmSXS6qW6S2ooGcyswMh/+64j4aj2itf5v04XlZNsOGUogOYVQkJQTq6QPw13C1/7bymedfa3EsX54bhJ7mcorvEpSVvn0tXF9s1WSX1Ymb51cqmsGQKqMAKo55OwtzvjXgy15LuMVLbpgU2OzmQ/fTBrMDpuvmdWru2cORnqW++r3pktsAxDwHOxodPUlsuyWSg+MNQFWe8ztYmgWEHUk1bLLKCWmYBc1Rq8zLZi0VXH0V0g/MNt6JGsQlNed31xM+Yk0r8aghUu1PM/V/2Nrn9Ks1yr+A63Hdhme3k4/msjUmxlEF06EQi1H1rEFoDi0jFdL05H/TlH9wmK1ZBWeSydGRrxsK5nB1PMJx/0FneIwRy/sPXW2IbrQIRCj+s6OAiQpXqMX6c4aLuPMwhltmeAklnHMIZUBg869BWUK1R27XMbQcJkVeU/GM8ChrakdejaGPnnhSqLxV3L4=";

        System.out.println("解密：" + decrypt(decryptKey, serverKeyStr));
    }
}
