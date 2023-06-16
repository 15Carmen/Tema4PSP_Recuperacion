package rsa.ejercicio03;
import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class Desencriptar {
    private static final String PRIVATE_KEY_FILE = "private.key";
    private static final String ENCRYPTED_FILE = "src/rsa/ejercicio03/ficheroEncriptado.txt";
    private static final String DECRYPTED_FILE = "src/rsa/ejercicio03/ficheroDescifrado.txt";

    public static void decryptFile() throws Exception {
        PrivateKey privateKey = loadPrivateKey();
        byte[] encryptedBytes = readEncryptedFile();
        byte[] decryptedBytes = decryptWithPrivateKey(encryptedBytes, privateKey);
        saveDecryptedFile(decryptedBytes);
    }

    private static PrivateKey loadPrivateKey() throws Exception {
        FileInputStream keyFis = new FileInputStream(PRIVATE_KEY_FILE);
        byte[] privateKeyBytes = new byte[keyFis.available()];
        keyFis.read(privateKeyBytes);
        keyFis.close();
        return generatePrivateKey(privateKeyBytes);
    }

    private static PrivateKey generatePrivateKey(byte[] privateKeyBytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    private static byte[] readEncryptedFile() throws Exception {
        FileInputStream fis = new FileInputStream(ENCRYPTED_FILE);
        byte[] encryptedBytes = new byte[fis.available()];
        fis.read(encryptedBytes);
        fis.close();
        return encryptedBytes;
    }

    private static byte[] decryptWithPrivateKey(byte[] encryptedBytes, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedBytes);
    }

    private static void saveDecryptedFile(byte[] decryptedBytes) throws Exception {
        FileOutputStream fos = new FileOutputStream(DECRYPTED_FILE);
        fos.write(decryptedBytes);
        fos.close();
    }
}


