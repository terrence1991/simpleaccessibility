package com.zy.accessibilitydemo;

/**
 * created by zhangyong
 * on 2019/7/31
 */
public class TestUtil {

    private static char[] jgZ = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String al(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            stringBuilder.append(jgZ[(bArr[i] & 240) >>> 4]);
            stringBuilder.append(jgZ[bArr[i] & 15]);
        }
        return stringBuilder.toString();
    }

    public static String cU(String str, String str2) {
        try {
            int i;
            int[] iArr = new int[256];
            byte[] bArr = new byte[256];
            for (i = 0; i < 256; i++) {
                iArr[i] = i;
            }
            for (i = 0; i < 256; i = (short) (i + 1)) {
                bArr[i] = (byte) str2.charAt(i % str2.length());
            }
            int i2 = 0;
            for (i = 0; i < 255; i++) {
                i2 = ((i2 + iArr[i]) + bArr[i]) % 256;
                int i3 = iArr[i];
                iArr[i] = iArr[i2];
                iArr[i2] = i3;
            }
            char[] toCharArray = str.toCharArray();
            char[] cArr = new char[toCharArray.length];
            i2 = 0;
            int i4 = 0;
            for (i = 0; i < toCharArray.length; i = (short) (i + 1)) {
                i2 = (i2 + 1) % 256;
                i4 = (i4 + iArr[i2]) % 256;
                int i5 = iArr[i2];
                iArr[i2] = iArr[i4];
                iArr[i4] = i5;
                cArr[i] = (char) (((char) iArr[(iArr[i2] + (iArr[i4] % 256)) % 256]) ^ toCharArray[i]);
            }
            return new String(cArr);
        } catch (Throwable e) {
            // ab.e("MicroMsg.ShortcutManager", "Exception in rc4, %s", new
            // Object[]{e.getMessage()});
            // ab.printErrStackTrace("MicroMsg.ShortcutManager", e, "", new Object[0]);
            return null;
        }
    }

    public static final byte[] Ew(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
        }
        return bArr;
    }
}
