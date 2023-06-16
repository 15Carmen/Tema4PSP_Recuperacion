package rsa.ejercicio03;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class Encriptar {

    //Declaramos las constantes
    private static final String PUBLIC_KEY_FILE = "public.key";
    private static final String ENCRYPTED_FILE = "src/rsa/ejercicio03/ficheroEncriptado.txt";

    /**
     * Método que encripta un archivo con la clave pública del receptor.
     *
     * @param ficheroEntrada Nombre del archivo a encriptar
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    public static void encriptarFichero(String ficheroEntrada) throws Exception{

        //Cargamos la clave pública del receptor
        byte[] clavePublicaBytes = leerClavePublica();

        //Generamos la clave pública
        PublicKey clavePublica = generatePublicKey(clavePublicaBytes);

        //Leemos el archivo a encriptar y lo guardamos en un array de bytes
        byte[] ficheroEntradaBytes = leerArchivoEntrada(ficheroEntrada);

        //Encriptamos el archivo con la clave pública del receptor
        byte[] ficheroEncriptadoBytes = encriptar(ficheroEntradaBytes, clavePublica);

        //Guardamos el archivo encriptado llamando al método guardarFicheroEncriptado
        guardarFicheroEncriptado(ficheroEncriptadoBytes);
    }

    /**
     * Método que lee la clave pública del receptor
     *
     * @return Array de bytes con la clave pública del receptor
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    private static byte[] leerClavePublica() throws Exception {

        //Leemos la clave pública del receptor
        FileInputStream keyFis = new FileInputStream(PUBLIC_KEY_FILE);
        //Guardamos la clave pública en un array de bytes
        byte[] clavePublicaBytes = new byte[keyFis.available()];
        //Leemos la clave pública del receptor
        keyFis.read(clavePublicaBytes);
        //Cerramos el flujo de lectura
        keyFis.close();

        //Devolvemos la clave pública del receptor
        return clavePublicaBytes;
    }

    /**
     * Método que genera la clave pública del receptor
     *
     * @param clavePublicaBytes
     * @return Clave pública del receptor
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    private static PublicKey generatePublicKey(byte[] clavePublicaBytes) throws Exception {

        //Generamos un objeto de clave RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //Generamos la clave pública del receptor a partir de la clave pública en bytes
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(clavePublicaBytes);
        //Devolvemos la clave pública del receptor
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * Método que lee el archivo a encriptar y lo guarda en un array de bytes
     *
     * @param archivoEntrada Nombre del archivo a encriptar
     * @return Array de bytes con el archivo a encriptar
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    private static byte[] leerArchivoEntrada(String archivoEntrada) throws Exception {
        //Leemos el archivo a encriptar
        FileInputStream fis = new FileInputStream(archivoEntrada);
        //Guardamos el archivo a encriptar en un array de bytes
        byte[] archivoEncriptadoBytes = new byte[fis.available()];
        //Leemos el archivo a encriptar
        fis.read(archivoEncriptadoBytes);
        //Cerramos el flujo de lectura
        fis.close();
        //Devolvemos el array de bytes con el archivo a encriptar
        return archivoEncriptadoBytes;
    }

    /**
     * Método que encripta el archivo con la clave pública del receptor
     * @param archivoEncriptadoBytes Array de bytes con el archivo a encriptar
     * @param clavePublica Clave pública del receptor
     * @return  Array de bytes con el archivo encriptado
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    private static byte[] encriptar(byte[] archivoEncriptadoBytes, PublicKey clavePublica) throws Exception {

        //Creamos el cipher del mensaje con el algoritmo RSA
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        //Inicializamos el cipher en modo encriptación con la clave pública del receptor
        cipher.init(Cipher.ENCRYPT_MODE, clavePublica);
        //Devolvemos el archivo encriptado
        return cipher.doFinal(archivoEncriptadoBytes);
    }

    /**
     * Método que guarda el archivo encriptado
     * @param archivoEncriptadoBytes Array de bytes con el archivo encriptado
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    private static void guardarFicheroEncriptado(byte[] archivoEncriptadoBytes) throws Exception {
        //Guardamos el archivo encriptado
        FileOutputStream fos = new FileOutputStream(ENCRYPTED_FILE);
        //Escribimos el archivo encriptado
        fos.write(archivoEncriptadoBytes);
        //Cerramos el flujo de escritura
        fos.close();
    }
}
