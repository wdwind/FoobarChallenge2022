package foobargoogle.PP_Decode;

import org.junit.Test;

import java.util.Base64;

public class PP_Decryption {

    public static String decode(String s, String key) {
        byte[] b64Decoded = Base64.getMimeDecoder().decode(s.getBytes());
        byte[] keyBytes = key.getBytes();

        byte[] result = new byte[b64Decoded.length];
        for (int i = 0; i < b64Decoded.length; i++) {
            int j = i % keyBytes.length;
            result[i] = (byte) (b64Decoded[i] ^ keyBytes[j]);
        }

        return new String(result);
    }

    @Test
    public void test() {
        String s = "DEYdEgUGCxQbRkhbSEYQEwsGEkJCR08CBw0EBBYGGwJBRVRHTwQbFQ0EGgQKQEpFSQIOBwcTHBJQ QVRHQQwABBoEDAgKDRJGQkdBBA0PAQQeBAUEGRVJR1xFSRIGDQcCAwQTRkJHQRcPBQoIHBJPQU1B SRQHAwtAREFPBwcOUEFUR0ESBwlJRhU=";
        String key = "mine...";

        System.out.println(decode(s, key));
        // {'success' : 'great', 'colleague' : 'esteemed', 'efforts' : 'incredible', 'achievement' : 'unlocked', 'rabbits' : 'safe', 'foo' : 'win!'}
    }

}
