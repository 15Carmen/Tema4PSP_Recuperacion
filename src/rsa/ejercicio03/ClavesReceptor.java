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

public class ClavesReceptor {

    //Declaramos las constantes con los nombres de los ficheros donde se guardan las claves
    private static final String FICHEROCLAVEPUBLICA = "publicKeyReceptor.key";
    private static final String FICHEROCLAVEPRIVADA = "privateKeyReceptor.key";

    /**
     * Método que genera las claves con el algoritmo RSA
     * @return cCaves generadas
     */
    public static KeyPair generarClavesReceptor() {
        //Declaramos las variables
        KeyPairGenerator generador; //Generador de claves
        KeyPair claves = null;      //Claves generadas

        try {
            //Creamos el generador de claves con el algoritmo RSA
            generador = KeyPairGenerator.getInstance("RSA");

            //Inicializamos el generador de claves con un tamaño de 4096 porque es el doble de 2048
            generador.initialize(4096);

            //Generamos las claves
            claves = generador.generateKeyPair();

        } catch (NoSuchAlgorithmException e) {  //Si no existe el algoritmo especificado saltará una excepción
            System.err.println("No existe el algoritmo especificado");
            e.printStackTrace();
        }
        return claves;
    }

    /**
     * Método que guarda las claves en ficheros
     * @param claves KeyPair con las claves a guardar
     */
    public static void guardarClaves(KeyPair claves) {

        //Declaramos la variable para escribir en el fichero
        FileOutputStream fileOutputStream;

        try {
            //Creamos el flujo de salida para el fichero de la clave pública
            fileOutputStream = new FileOutputStream(FICHEROCLAVEPUBLICA);
            //Escribimos la clave pública en el fichero
            fileOutputStream.write(claves.getPublic().getEncoded());
            //Cerramos el flujo de salida
            fileOutputStream.close();

            //Creamos el flujo de salida para el fichero de la clave privada
            fileOutputStream = new FileOutputStream(FICHEROCLAVEPRIVADA);
            //Escribimos la clave privada en el fichero
            fileOutputStream.write(claves.getPrivate().getEncoded());
            //Cerramos el flujo de salida
            fileOutputStream.close();

        } catch (FileNotFoundException e) { //Si no se encuentra el fichero saltará una excepción
            System.err.println("No se encuentra el fichero.");
            e.printStackTrace();
        } catch (IOException e) { //Si se produce un error durante la escritura en el fichero saltará una excepción
            System.err.println("Se ha producido un error durante la escritura en el fichero.");
            e.printStackTrace();
        }
    }

    /**
     * Método que lee la clave pública del fichero
     * @return Clave pública
     */
    public static PublicKey getClavePublica() {

        //Declaramos laa variables para leer el fichero
        File ficheroClavePublica = new File(FICHEROCLAVEPUBLICA); //Fichero de la clave pública
        PublicKey clavePublica = null;                            //Clave pública

        try {
            //Leemos el fichero de la clave pública
            byte[] bytesClavePublica = Files.readAllBytes(ficheroClavePublica.toPath());

            //Creamos el objeto para generar claves con el algoritmo RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            //Creamos la especificación de la clave pública
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytesClavePublica);

            //Generamos la clave pública
            clavePublica = keyFactory.generatePublic(publicKeySpec);

        } catch (IOException e) { //Si se produce un error durante la lectura del fichero saltará una excepción
            System.err.println("Se ha producido en la lectura del fichero");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) { //Si no existe el algoritmo especificado saltará una excepción
            System.err.println("No existe el algoritmo especificado");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) { //Si la clave no es válida saltará una excepción
            System.err.println("La clave indicada no es válida");
            e.printStackTrace();
        }

        //Devolvemos la clave pública
        return clavePublica;
    }

    /**
     * Método que lee la clave privada del fichero
     * @return Clave privada
     */
    public static PrivateKey getClavePrivada() {

        //Declaramos laa variables para leer el fichero
        File ficheroClavePrivada = new File(FICHEROCLAVEPRIVADA); //Fichero de la clave privada
        PrivateKey clavePrivada = null;                           //Clave privada

        try {
            //Leemos el fichero de la clave privada
            byte[] bytesClavePrivada = Files.readAllBytes(ficheroClavePrivada.toPath());

            //Creamos el objeto para generar claves con el algoritmo RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            //Creamos la especificación de la clave privada
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytesClavePrivada);

            //Generamos la clave privada
            clavePrivada = keyFactory.generatePrivate(privateKeySpec);

        } catch (IOException e) { //Si se produce un error durante la lectura del fichero saltará una excepción
            System.err.println("Se ha producido en la lectura del fichero");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) { //Si no existe el algoritmo especificado saltará una excepción
            System.err.println("No existe el algoritmo especificado");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) { //Si la clave no es válida saltará una excepción
            System.err.println("La clave indicada no es válida");
            e.printStackTrace();
        }

        //Devolvemos la clave privada
        return clavePrivada;
    }
}
