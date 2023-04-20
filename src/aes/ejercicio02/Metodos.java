package aes.ejercicio02;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class Metodos {

    static final int LONGITUD_BLOQUE = 16;
    private static final String ALGORITMO = "AES/ECB/PKCS5Padding";


    /**
     * Metodo que transfroma la contraseña introducidad por el usuario en una clave
     * @param claveUsuario contraseña introducida por el usuario
     * @return clave de cifrado
     */
    public static Key obtenerClave(String claveUsuario) {

        // Creamos la clave
        Key clave = new SecretKeySpec(claveUsuario.getBytes(), 0, LONGITUD_BLOQUE, "AES");

        return clave;
    }

    /**
     * Método que cifrará el texto pasado por parámetro
     * @param texto texto a cifrar
     * @param clave clave de cifrado
     * @return el texto cifrado
     */
    public static String cifrar(String texto, Key clave) {

        //Declaramos las variables
        String textoCifrado = "";   // Variable donde guardaremos el texto cifrado

        try {
            // Creamos un Cipher
            Cipher cipher = Cipher.getInstance(ALGORITMO);

            // Iniciamos el cifrado con la clave que hemos creado en el método anterior
            cipher.init(Cipher.ENCRYPT_MODE, clave);

            // Llevar a cabo el cifrado
            byte[] cipherText = cipher.doFinal(texto.getBytes());

            // Guardamos el String con el texto cifrado en Base 64 en una variable
            textoCifrado = Base64.getEncoder().encodeToString(cipherText);

            //Capturamos las excepciones y mostramos un mensaje de error correspondiente al tipo de excepción
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

        // Devolvemos el texto cifrado
        return textoCifrado;
    }

    /**
     * Método que descifra el texto pasado por parámetro
     * @param textoCifrado texto cifrado
     * @param clave clave de cifrado
     * @return el texto descifrado
     */
    public static String descifrar(String textoCifrado, Key clave) {

        //Declaramos las variables
        String textoDescifrado = "";    // Variable donde guardaremos el texto descifrado

        try {
            // Creamos un Cipher
            Cipher cipher = Cipher.getInstance(ALGORITMO);

            // Iniciamos el descifrado con la clave que hemos creado en el método anterior
            cipher.init(Cipher.DECRYPT_MODE, clave);

            // Llevar a cabo el descifrado
            byte[] byteTextoDescifrado = cipher.doFinal(Base64.getDecoder().decode(textoCifrado));

            // Guardamos el String con el texto descifrado en una variable
            textoDescifrado = new String(byteTextoDescifrado);

        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return textoDescifrado;

    }


}
