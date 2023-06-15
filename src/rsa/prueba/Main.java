package rsa.prueba;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Main {
    public static void main(String[] args) {
        try {
            // Generamos un par de claves RSA
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // Ciframos un archivo
            File inputFile = new File("src/rsa/prueba/texto.txt");
            File encryptedFile = new File("src/rsa/prueba/texto_encriptado.txt");
            FileEncryption.encrypt(inputFile, encryptedFile, publicKey);

            // Desciframos el archivo cifrado
            File decryptedFile = new File("src/rsa/prueba/texto_descifrado.txt");
            FileEncryption.decrypt(encryptedFile, decryptedFile, privateKey);

            System.out.println("Cifrado y descifrado completados correctamente.");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
