package it.unibas.progettorest.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordUtil {

    public static String sha256hash(final String password) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(password.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            log.error("Errore durante il calcolo dello sha", ex);
            throw new RuntimeException(ex);
        }
    }

    public static String md5hash(String password) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(password.getBytes());
            final StringBuilder hashString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                hashString.append(Integer.toHexString(
                        (hash[i] & 0xFF) | 0x100
                ).toLowerCase().substring(1, 3));
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException ex) {
            log.error("Errore durante il calcolo dell'md5", ex);
            throw new RuntimeException(ex);
        }
    }
}
