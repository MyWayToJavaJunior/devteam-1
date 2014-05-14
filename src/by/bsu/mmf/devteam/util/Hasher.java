package by.bsu.mmf.devteam.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    private static final String MD5 = "MD5";

    public static String getMD5(String value) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            byte[] digest = messageDigest.digest(value.getBytes());
            BigInteger number = new BigInteger(1, digest);
            String hash = number.toString(16);
            while (hash.length() < 32) {
                hash = "0" + hash;
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
