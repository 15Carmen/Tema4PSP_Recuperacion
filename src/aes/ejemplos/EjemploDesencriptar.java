package aes.ejemplos;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 * Clase que muestra cómo desencriptar un texto cifrado con AES
 */
public class EjemploDesencriptar {

    /**
     * Longitud del bloque en bytes
     */
    static final int LONGITUD_BLOQUE = 16;

    /**
     * Contraseña que se utilizará para cifrar/descifrar
     */
    private static final String PASSWORD = "MeLlamoSpiderman";

    /**
     * Algoritmo que se utilizará para cifrar/descifrar
     */
    private static final String ALGORITMO = "AES/ECB/PKCS5Padding";

    /**
     * Texto cifrado en Base 64
     */
    private static final String TEXTO_CIFRADO = "Vk8dDXzIPa0o5i4B60koftTuq7l5nwu574ArdboFZwlQBzm1Af+t6V+WGYAtJxQ0";


    /**
     * Método principal
     * @param args
     */
    public static void main(String[] args) {

        // 1 - Crear la clave. Al ser el algoritmo AES tenemos que indicarle la longitud del bloque
        // La longitud puede ser 16, 24 ó 32 bytes
        // La longitud de la contraseña tiene que coincidir con la longitud indicada
        Key clave = new SecretKeySpec(PASSWORD.getBytes(), 0, LONGITUD_BLOQUE, "AES");

        try {
            // 2 - Crear un Cipher
            Cipher cipher = Cipher.getInstance(ALGORITMO);

            // 3 - Iniciar el descifrado con la clave
            cipher.init(Cipher.DECRYPT_MODE, clave);

            // 4 - Llevar a cabo el descifrado
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(TEXTO_CIFRADO));

            // Imprimimos el mensaje descifrado:
            System.out.println(new String(plainText));

            //Capturamos las excepciones que se puedan producir e imprimimos un mensaje de error según el caso
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe el algoritmo especificado");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.err.println("El padding seleccionado no existe");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.err.println("La clave utilizada no es válida");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.err.println("El tamaño del bloque elegido no es correcto");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.err.println("El padding seleccionado no es correcto");
            e.printStackTrace();
        }
    }
}
