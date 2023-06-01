package rsa.ejercicio03;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeysManager {

    public static final String PUBLIC_KEY_FILE_RECEPTOR = "public_key_receptor.key";
    public static final String PRIVATE_KEY_FILE_RECEPTOR = "private_key_receptor.key";
    public static final String PUBLIC_KEY_FILE_EMISOR = "public_key_emisor.key";
    public static final String PRIVATE_KEY_FILE_EMISOR = "private_key_emisor.key";

    public static void main(String[] args) {
        KeyPair claves = generarClaves();
        guardarClaves(claves, "emisor");
        claves = generarClaves();
        guardarClaves(claves, "receptor");
    }

    public static KeyPair generarClaves() {
        KeyPairGenerator generador;
        KeyPair claves = null;
        try {
            generador = KeyPairGenerator.getInstance("RSA");
            generador.initialize(2048);
            claves = generador.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe el algoritmo especificado");
            System.out.println(e.getLocalizedMessage());
        }

        return claves;
    }

    public static void guardarClaves(KeyPair claves, String tipo) {
        FileOutputStream fos;
        try {

            if(tipo.equals("emisor")) {
                fos = new FileOutputStream(PUBLIC_KEY_FILE_EMISOR);
                fos.write(claves.getPublic().getEncoded());
                fos.close();

                fos = new FileOutputStream(PRIVATE_KEY_FILE_EMISOR);
                fos.write(claves.getPrivate().getEncoded());
                fos.close();
            }else{
                fos = new FileOutputStream(PUBLIC_KEY_FILE_RECEPTOR);
                fos.write(claves.getPublic().getEncoded());
                fos.close();

                fos = new FileOutputStream(PRIVATE_KEY_FILE_RECEPTOR);
                fos.write(claves.getPrivate().getEncoded());
                fos.close();
            }


        } catch (FileNotFoundException e) {
            System.err.println("No se encuentra el fichero.");
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println("Se ha producido un error durante la escritura en el fichero de claves del receptor.");
            System.out.println(e.getLocalizedMessage());
        }

    }

    public static PublicKey getClavePublica(String ruta) {
        File ficheroClavePublica = new File(ruta);
        PublicKey clavePublica = null;
        try {

            byte[] bytesClavePublica = Files.readAllBytes(ficheroClavePublica.toPath());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytesClavePublica);
            clavePublica = keyFactory.generatePublic(publicKeySpec);

        } catch (IOException e) {
            System.err.println("Se ha producido un error en la lectura del fichero de clave publica del receptor");
            System.out.println(e.getLocalizedMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe el algoritmo especificado");
            System.out.println(e.getLocalizedMessage());
        } catch (InvalidKeySpecException e) {
            System.err.println("La clave indicada en la clave publica del receptor no es válida");
            System.out.println(e.getLocalizedMessage());
        }
        return clavePublica;
    }

    public static PrivateKey getClavePrivada(String ruta) {
        File ficheroClavePrivada = new File(ruta);
        PrivateKey clavePrivada = null;
        try {

            byte[] bytesClavePrivada = Files.readAllBytes(ficheroClavePrivada.toPath());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytesClavePrivada);
            clavePrivada = keyFactory.generatePrivate(privateKeySpec);

        } catch (IOException e) {
            System.err.println("Se ha producido un error en la lectura del fichero de clave privada del receptor");
            System.out.println(e.getLocalizedMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe el algoritmo especificado");
            System.out.println(e.getLocalizedMessage());
        } catch (InvalidKeySpecException e) {
            System.err.println("La clave indicada en la clave privada del receptor no es válida");
            System.out.println(e.getLocalizedMessage());
        }
        return clavePrivada;
    }
}
