package rsa.ejemplos;

import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.*;

public class KeysManager {


    /**
     * Cadenas con los nombres de los ficheros donde se guardarán las claves
     */
    private static final String PUBLIC_KEY_FILE = "public_key.key";
    private static final String PRIVATE_KEY_FILE = "private_key.key";

    /**
     * Método que genera las claves pública y privada
     * @return un objeto KeyPair con las claves generadas
     */
    public static KeyPair generarClaves() {
        //Declaramos las variables
        KeyPairGenerator generador; //Variable para generar las claves
        KeyPair claves = null;      //Variable para almacenar las claves

        try {
            //Generamos las claves rsa
            generador = KeyPairGenerator.getInstance("RSA");
            generador.initialize(2048);
            claves = generador.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {  //Capturamos las excepciones y mostramos el error
            System.err.println("No existe el algoritmo especificado");
            e.printStackTrace();
        }

        //Devolvemos las claves
        return claves;
    }

    /**
     * Método que guarda las claves en los ficheros correspondientes
     * @param claves objeto KeyPair con las claves a guardar
     */
    public static void guardarClaves(KeyPair claves) {
        //Declaramos las variables
        FileOutputStream fos;   //Variable para escribir en los ficheros

        try {
            //Escribimos la clave pública en el fichero
            fos = new FileOutputStream(PUBLIC_KEY_FILE);
            fos.write(claves.getPublic().getEncoded());
            //Cerramos el fichero
            fos.close();

            //Escribimos la clave privada en el fichero
            fos = new FileOutputStream(PRIVATE_KEY_FILE);
            fos.write(claves.getPrivate().getEncoded());
            //Cerramos el fichero
            fos.close();

        } catch (FileNotFoundException e) { //Capturamos las excepciones y mostramos el error
            System.err.println("No se encuentra el fichero.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Se ha producido un error durante la escritura en el fichero.");
            e.printStackTrace();
        }

    }

    /**
     * Método que lee la clave pública del fichero
     * @return la clave pública leída
     */
    public static PublicKey getClavePublica() {

        //Declaramos las variables
        File ficheroClavePublica = new File(PUBLIC_KEY_FILE);   //Variable para almacenar el fichero de la clave pública
        PublicKey clavePublica = null;                          //Variable para almacenar la clave pública

        try {
            byte[] bytesClavePublica = Files.readAllBytes(ficheroClavePublica.toPath());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytesClavePublica);
            clavePublica = keyFactory.generatePublic(publicKeySpec);

        } catch (IOException e) {
            System.err.println("Se ha producido en la lectura del fichero");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe el algoritmo especificado");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            System.err.println("La clave indicada no es válida");
            e.printStackTrace();
        }
        return clavePublica;
    }

    public static PrivateKey getClavePrivada() {
        File ficheroClavePrivada = new File(PRIVATE_KEY_FILE);
        PrivateKey clavePrivada = null;
        try {

            byte[] bytesClavePrivada = Files.readAllBytes(ficheroClavePrivada.toPath());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytesClavePrivada);
            clavePrivada = keyFactory.generatePrivate(privateKeySpec);

        } catch (IOException e) {
            System.err.println("Se ha producido en la lectura del fichero");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe el algoritmo especificado");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            System.err.println("La clave indicada no es válida");
            e.printStackTrace();
        }
        return clavePrivada;
    }

    /**
     * Método principal de la clase que genera las claves y las guarda en los ficheros correspondientes
     * @param args
     */
    public static void main(String[] args) {
        KeyPair claves = generarClaves();
        guardarClaves(claves);
    }
}
