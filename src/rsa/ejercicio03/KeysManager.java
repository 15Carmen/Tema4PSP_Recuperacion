package rsa.ejercicio03;

import java.io.FileOutputStream;
import java.security.*;

public class KeysManager {

    //Declaramos como constantes la ruta de las claves pública y privada
    private static final String PRIVATE_KEY_FILE = "private.key";
    private static final String PUBLIC_KEY_FILE = "public.key";

    /**
     * Método que generará las claves
     * @throws Exception Excepcion que se lanza si ocurre un error
     */
    public static void generateKeys() throws Exception {

        KeyPair keyPair = generarKeyPair();
        guardarClavePrivada(keyPair.getPrivate());
        guardarClavePublica(keyPair.getPublic());
    }

    /**
     * Método que generará el KeyPair
     * @return un KeyPair
     * @throws NoSuchAlgorithmException Excepción que saltará cuando el algoritmo de alguna de las claves sea distinto a RSA
     */
    private static KeyPair generarKeyPair() throws NoSuchAlgorithmException {

        //Declaramos el generador de KeyPair y le indicamos el algoritmo de incriptación (RSA)
        KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
        //Inicializamos el generador
        generador.initialize(2048);
        //Devolvemos el KeyPair
        return generador.generateKeyPair();
    }

    /**
     * Método que guardará la clave privada
     * @param clavePrivada clave privada
     * @throws Exception Excepción que se lanza si ocurre un error
     */
    private static void guardarClavePrivada(PrivateKey clavePrivada) throws Exception {

        byte[] privateKeyBytes = clavePrivada.getEncoded();
        FileOutputStream privateFos = new FileOutputStream(PRIVATE_KEY_FILE);
        privateFos.write(privateKeyBytes);
        privateFos.close();
    }

    /**
     * Método que guarda la clave pública
     * @param clavePublica
     * @throws Exception Excepcion que se lanza si ocurre un error
     */
    private static void guardarClavePublica(PublicKey clavePublica) throws Exception {

        //Guardamos la clave publica en un array de bytes
        byte[] publicKeyBytes = clavePublica.getEncoded();
        //Leemos la clave pública
        FileOutputStream publicFos = new FileOutputStream(PUBLIC_KEY_FILE);
        //Escribimos el archivo de clave publica
        publicFos.write(publicKeyBytes);
        //Cerramos el flujo de escritura
        publicFos.close();
    }
}


