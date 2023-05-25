package rsa.ejercicio03;


import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class EmisorClaves {

    /**
     * Cadenas con los nombres de los ficheros donde se guardarán las claves
     */
    private static final String PUBLIC_KEY_FILE_EMISOR = "public_key_emisor.key";
    private static final String PRIVATE_KEY_FILE_EMISOR = "private_key_emisor.key";

    public static void main(String[] args) {
        KeyPair claves = generarClaves();
        guardarClaves(claves);
    }

    /**
     * Método que genera las claves pública y privada
     * @return un objeto KeyPair con las claves generadas
     */
    public static KeyPair generarClaves() {
        //Declaramos las variables
        KeyPairGenerator generador; //Variable para generar las claves
        KeyPair claves = null;    //Variable para almacenar las claves

        try {
            //Generamos las claves rsa
            generador = KeyPairGenerator.getInstance("RSA");
            generador.initialize(2048);
            claves = generador.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe el algoritmo especificado");
            e.getLocalizedMessage();
        }

        return claves;
    }

    /**
     * Método que guarda las claves en los ficheros correspondientes
     * @param claves objeto KeyPair con las claves a guardar
     */
    public static void guardarClaves(KeyPair claves) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(PUBLIC_KEY_FILE_EMISOR);
            fos.write(claves.getPublic().getEncoded());
            fos.close();

            fos = new FileOutputStream(PRIVATE_KEY_FILE_EMISOR);
            fos.write(claves.getPrivate().getEncoded());
            fos.close();

        } catch (FileNotFoundException e) {
            System.err.println("No se encuentra el fichero.");
            e.getLocalizedMessage();
        } catch (IOException e) {
            System.err.println("Se ha producido un error durante la escritura en el fichero.");
            e.getLocalizedMessage();
        }

    }

    /**
     * Método que lee la clave pública del fichero
     * @return objeto PublicKey con la clave pública
     */
    public static PublicKey getClavePublica() {

        //Declaramos las variables
        File ficheroClavePublica = new File(PUBLIC_KEY_FILE_EMISOR);    //Fichero donde se encuentra la clave pública
        PublicKey clavePublica = null;  //Variable para almacenar la clave pública

        try {
            //Leemos la clave pública del fichero
            byte[] bytesClavePublica = Files.readAllBytes(ficheroClavePublica.toPath());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytesClavePublica);
            clavePublica = keyFactory.generatePublic(publicKeySpec);

        } catch (IOException e) {
            System.err.println("Se ha producido en la lectura del fichero de clave pública del emisor");
            e.getLocalizedMessage();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe el algoritmo especificado");
            e.getLocalizedMessage();
        } catch (InvalidKeySpecException e) {
            System.err.println("La clave indicada en la clave publica del emisor no es válida");
            e.getLocalizedMessage();
        }
        return clavePublica;
    }

    /**
     * Método que lee la clave privada del fichero
     * @return objeto PrivateKey con la clave privada
     */
    public static PrivateKey getClavePrivada() {
        File ficheroClavePrivada = new File(PRIVATE_KEY_FILE_EMISOR);
        PrivateKey clavePrivada = null;
        try {

            byte[] bytesClavePrivada = Files.readAllBytes(ficheroClavePrivada.toPath());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytesClavePrivada);
            clavePrivada = keyFactory.generatePrivate(privateKeySpec);

        } catch (IOException e) {
            System.err.println("Se ha producido en la lectura del fichero de clave privada del emisor");
            e.getLocalizedMessage();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe el algoritmo especificado");
            e.getLocalizedMessage();
        } catch (InvalidKeySpecException e) {
            System.err.println("La clave indicada en la clave privada del emisor no es válida");
            e.getLocalizedMessage();
        }
        return clavePrivada;
    }
}
