package com.zzh.algorithm.encryption;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 常见的不可逆加密算法有MD5，HMAC，SHA1、SHA-224、SHA-256、SHA-384，和SHA-512，其中SHA-224、SHA-256、SHA-384，
 * 和SHA-512我们可以统称为SHA2加密算法，SHA加密算法的安全性要比MD5更高，而SHA2加密算法比SHA1的要高。
 * 其中SHA后面的数字表示的是加密后的字符串长度，SHA1默认会产生一个160位的信息摘要。
 * <p>
 * 不可逆加密算法最大的特点就是密钥，但是HMAC是需要密钥的【手动狗头】。
 * <p>
 * 由于这些加密都是不可逆的，因此比较常用的场景就是用户密码加密，其验证过程就是通过比较两个加密后的字符串是否一样来确认身份的。
 * 网上也有很多自称是可以破解MD5密码的网站，其原理也是一样，就是有一个巨大的资源库，存放了许多字符串及对应的MD5加密后的字符串，
 * 通过你输入的MD5加密串来进行比较，如果过你的密码复杂度比较低，还是有很大机率验证出来的。
 *
 * @Author: yuhui.guan
 * @Date: 2020/7/29 9:59
 */
public class IrreversibleEncryption {

    /**
     * MD5信息摘要算法（英语：MD5 Message-Digest Algorithm），一种被广泛使用的密码散列函数，可以产生出一个128位（16字节）的散列值（hash value）
     * ，用于确保信息传输完整一致。
     *
     * @param text 输入信息
     * @return md5值
     */
    public static String md5(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] bytes = messageDigest.digest(text.getBytes());
        return Hex.encodeHexString(bytes);
    }

    /**
     * 安全散列算法（英语：Secure Hash Algorithm，缩写为SHA）是一个密码散列函数家族，是FIPS所认证的安全散列算法。
     * 能计算出一个数字消息所对应到的，长度固定的字符串（又称消息摘要）的算法。且若输入的消息不同，它们对应到不同字符串的机率很高。
     * 2005年8月17日的CRYPTO会议尾声中王小云、姚期智、姚储枫再度发表更有效率的SHA-1攻击法，能在2的63次方个计算复杂度内找到碰撞。
     *
     * @param text 输入信息
     * @return SHA-256值
     */
    public static String sha256(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] bytes = messageDigest.digest(text.getBytes());
        return Hex.encodeHexString(bytes);
    }

    /**
     * HMAC是密钥相关的哈希运算消息认证码（Hash-based Message Authentication  Code）的缩写，
     * 由H.Krawezyk，M.Bellare，R.Canetti于1996年提出的一种基于Hash函数和密钥进行消息认证的方法，并于1997年作为RFC2104被公布，
     * 并在IPSec和其他网络协议（如SSL）中得以广泛应用，现在已经成为事实上的Internet安全标准。它可以与任何迭代散列函数捆绑使用。
     * HMAC算法更像是一种加密算法，它引入了密钥，其安全性已经不完全依赖于所使用的Hash算法
     *
     * @param text 输入信息
     * @param sk   密钥
     * @return 加密串
     */
    public static String hmacSha256(String text, SecretKeySpec sk) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        try {
            mac.init(sk);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] rawHmac = mac.doFinal(text.getBytes());
//        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(rawHmac));
        return new String(Base64.getEncoder().encode(rawHmac));
    }
}
