/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.evoluti.otp.swing.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Image;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base32;

/**
 *
 * @author Rodrigo Cananea <rodrigoaguiar35@gmail.com>
 */
public class Util {

    private Util() {
    }

    public static String generateBase32SecretKey(String algorithm, int keySize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(keySize, new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();

            // Codifica a chave em Base32
            Base32 base32 = new Base32();
            return base32.encodeToString(secretKey.getEncoded()).trim();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar chave secreta: " + e.getMessage(), e);
        }
    }

    public static SecretKey getSecretKeyFromEncodedString(String encodedKey, String algorithm) {
        Base32 base32 = new Base32();
        byte[] decodedKey = base32.decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
    }

    public static String generateUri(SecretKey secretKey, String accountName, String issuer) {
        // Encoda a chave secreta em Base32
        Base32 base32 = new Base32();
        String secretKeyBase32 = base32.encodeToString(secretKey.getEncoded()).replace("=", "");

        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, accountName, secretKeyBase32, issuer);
    }

    public static Image generateQRCode(String uri) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(uri, BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
