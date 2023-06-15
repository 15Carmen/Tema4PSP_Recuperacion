package rsa.prueba;

import java.io.*;
import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class FileEncryption {
    private static final String ALGORITHM = "RSA";
    private static final int BLOCK_SIZE = 245; // limite de la encriptacion RSA

    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     * Descripcion: Constructor de la clase FileEncryption
     * Precondiciones: Ninguna
     * Postcondiciones: Se genera un par de claves RSA
     *
     * @throws NoSuchAlgorithmException
     */
    public FileEncryption() throws NoSuchAlgorithmException {
        // Generamos un par de claves RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // Guardamos las claves
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }


    /**
     * Descripcion: Metodo que encripta un archivo con la clave publica
     * Precondiciones: Ninguna
     * Postcondiciones: Se encripta el archivo
     *
     * @param inputFile
     * @param outputFile
     * @param publicKey
     */
    public static void encrypt(File inputFile, File outputFile, PublicKey publicKey) {

        try {
            // creamos los streams de entrada y salida
            FileInputStream inputStream = new FileInputStream(inputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            // creamos el cifrador
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // creamos los buffers
            byte[] inputBuffer = new byte[BLOCK_SIZE];
            byte[] outputBuffer;
            int bytesRead;
            // leemos el archivo de entrada y escribimos el archivo de salida
            while ((bytesRead = inputStream.read(inputBuffer)) > 0) {
                outputBuffer = cipher.doFinal(inputBuffer, 0, bytesRead);
                outputStream.write(outputBuffer);
            }
            // cerramos los streams
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.err.println("Padding no encontrado");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.err.println("Tamaño incorrecto");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritmo erróneo");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de entrada/salida");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.err.println("Mal padding");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.err.println("Clave no válida");
            e.printStackTrace();
        }

    }

    /**
     * Descripcion: Metodo que desencripta un archivo con la clave privada
     * Precondiciones: Ninguna
     * Postcondiciones: Se desencripta el archivo
     *
     * @param inputFile
     * @param outputFile
     * @param privateKey
     */
    public static void decrypt(File inputFile, File outputFile, PrivateKey privateKey) {

        try {
            // creamos los streams de entrada y salida
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
            // creamos el descifrador
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // desciframos el archivo
            byte[] decryptedBytes = cipher.doFinal(inputBytes);
            // escribimos el archivo descifrado
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(decryptedBytes);
            // cerramos los streams
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.err.println("Padding no encontrado");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.err.println("Tamaño incorrecto");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritmo erróneo");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de entrada/salida");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.err.println("Mal padding");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.err.println("Clave no válida");
            e.printStackTrace();
        }

    }
}
