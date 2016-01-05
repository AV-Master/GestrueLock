
package com.beiing.gestruelock.gestruelock;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class PatternUtils {

    private PatternUtils() {
    }

    private static String bytesToString(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    private static byte[] stringToBytes(String string) {
        return Base64.decode(string, Base64.DEFAULT);
    }

    public static byte[] patternToBytes(List<PatternView.Cell> pattern) {
        int patternSize = pattern.size();
        byte[] bytes = new byte[patternSize];
        for (int i = 0; i < patternSize; ++i) {
            PatternView.Cell cell = pattern.get(i);
            bytes[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
        }
        return bytes;
    }

    public static String patternToNoString(List<PatternView.Cell> pattern) {
        int patternSize = pattern.size();
        String patStr = "";
        for (int i = 0; i < patternSize; ++i) {
            PatternView.Cell cell = pattern.get(i);
            patStr = patStr + (cell.getRow() * 3 + cell.getColumn());
        }
        return patStr;
    }

    public static List<PatternView.Cell> bytesToPattern(byte[] bytes) {
        List<PatternView.Cell> pattern = new ArrayList<PatternView.Cell>();
        for (byte b : bytes) {
            pattern.add(PatternView.Cell.of(b / 3, b % 3));
        }
        return pattern;
    }

    @Deprecated
    public static String patternToString(List<PatternView.Cell> pattern) {
        return bytesToString(patternToBytes(pattern));
    }

    @Deprecated
    public static List<PatternView.Cell> stringToPattern(String string) {
        return bytesToPattern(stringToBytes(string));
    }

    private static byte[] sha1(byte[] input) {
        try {
            return MessageDigest.getInstance("SHA-1").digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] patternToSha1(List<PatternView.Cell> pattern) {
        return sha1(patternToBytes(pattern));
    }

    public static String patternToSha1String(List<PatternView.Cell> pattern) {
        return bytesToString(patternToSha1(pattern));
    }
}
