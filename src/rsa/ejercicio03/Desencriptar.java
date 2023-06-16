package rsa.ejercicio03;
import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class Desencriptar {

    //Declaramos las constantes
    private static final String PRIVATE_KEY_FILE = "private.key";   //Ruta del archivo de clave privada
    private static final String ENCRYPTED_FILE = "src/rsa/ejercicio03/ficheroEncriptado.txt";   //Ruta donde vamos a crear el fichero con la información encriptada
    private static final String DECRYPTED_FILE = "src/rsa/ejercicio03/ficheroDescifrado.txt";   //Ruta donde vamos a crear el fichero con la información desencriptada


    /**
     * Método que desencriptará el archivo ficheroEncriptado.txt
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    public static void desencriptarFicheros() throws Exception {

        //Generamos la clave privada del emisor
        PrivateKey clavePrivada = leerClavePrivada();

        //Leemos el archivo encriptado y lo guardamos en un array de bytes
        byte[] ficheroEncriptadoBytes = leerArchivoEncriptado();

        //Desencriptamos el archivo con la clave privada del emisor
        byte[] bytesDesencriptados = desencriptarClavePrivada(ficheroEncriptadoBytes, clavePrivada);

        //Guardamos el archivo desencriptado llamando al método guardarFicheroDesencriptado
        guardarFicheroDesencriptado(bytesDesencriptados);
    }

    /**
     * Método que carga la clave privada del emisor
     * @return
     * @throws Exception
     */
    private static PrivateKey leerClavePrivada() throws Exception {

        //Leemos la clave privada del emisor
        FileInputStream keyFis = new FileInputStream(PRIVATE_KEY_FILE);
        //Guardamos la clave privada en un array de bytes
        byte[] privateKeyBytes = new byte[keyFis.available()];
        //Leemos la clave privada del emisor
        keyFis.read(privateKeyBytes);
        //Cerramos el flujo de lectura
        keyFis.close();

        //Devolvemos la clave privada del emisor
        return generatePrivateKey(privateKeyBytes);
    }

    /**
     * Método que genera la clave privada del emisor
     * @param clavePrivadaBytes
     * @return Clave privada del emisor
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    private static PrivateKey generatePrivateKey(byte[] clavePrivadaBytes) throws Exception {

        //Generamos un objeto de clave RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //Generamos la clave privada del emisor a partir de la clave privada en bytes
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(clavePrivadaBytes);
        //Devolvemos la clave privada del emisor
        return keyFactory.generatePrivate(privateKeySpec);
    }

    /**
     * Método que lee el archivo encriptado y lo guarda en un array de bytes
     * @return Array de bytes con el archivo encriptado
     */
    private static byte[] leerArchivoEncriptado() throws Exception {
        //Leemos el archivo encriptado
        FileInputStream fis = new FileInputStream(ENCRYPTED_FILE);
        //Guardamos el archivo encriptado en un array de bytes
        byte[] ficheroEncriptadoBytes = new byte[fis.available()];
        //Leemos el archivo encriptado
        fis.read(ficheroEncriptadoBytes);
        //Cerramos el flujo de lectura
        fis.close();

        //Devolvemos el array de bytes con el archivo encriptado
        return ficheroEncriptadoBytes;
    }

    /**
     * Método que desencripta el archivo con la clave privada del emisor
     * @param ficheroEncriptadoBytes array de bytes con el archivo encriptado
     * @param clavePrivada Clave privada del emisor
     * @return Array de bytes con el archivo desencriptado
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    private static byte[] desencriptarClavePrivada(byte[] ficheroEncriptadoBytes, PrivateKey clavePrivada) throws Exception {
        //Creamos el cipher del mensaje con el algoritmo RSA
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        //Inicializamos el cipher en modo desencriptación con la clave privada del emisor
        cipher.init(Cipher.DECRYPT_MODE, clavePrivada);
        //Devolvemos el archivo desencriptado
        return cipher.doFinal(ficheroEncriptadoBytes);
    }

    /**
     * Método que guarda el archivo desencriptado
     * @param archivoDesencriptadoBytes Array de bytes con el archivo desencriptado
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    private static void guardarFicheroDesencriptado(byte[] archivoDesencriptadoBytes) throws Exception {
        //Guardamos el archivo desencriptado
        FileOutputStream fos = new FileOutputStream(DECRYPTED_FILE);
        //Escribimos el archivo desencriptado
        fos.write(archivoDesencriptadoBytes);
        //Cerramos el flujo de escritura
        fos.close();
    }
}


