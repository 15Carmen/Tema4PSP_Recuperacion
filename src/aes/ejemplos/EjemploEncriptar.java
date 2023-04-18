package aes.ejemplos;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class EjemploEncriptar {

    static final int LONGITUD_BLOQUE = 16;
    private static final String PASSWORD = "MeLlamoSpiderman";
    private static final String ALGORITMO = "AES/ECB/PKCS5Padding";
    private static final String TEXTO = "Este es el texto que se va a cifrar";

    public static void main(String[] args) {
        // 1 - Crear la clave. Al ser el algoritmo AES tenemos que indicarle la longitud del bloque
        // La longitud puede ser 16, 24 ó 32 bytes
        // La longitud de la contraseña tiene que coincidir con la longitud indicada
        Key clave = new SecretKeySpec(PASSWORD.getBytes(), 0, LONGITUD_BLOQUE, "AES");

        try {
            // 2 - Crear un Cipher
            Cipher cipher = Cipher.getInstance(ALGORITMO);

            // 3 - Iniciar el cifrado con la clave que hemos creado previamente
            cipher.init(Cipher.ENCRYPT_MODE, clave);

            // 4 - Llevar a cabo el cifrado
            byte[] cipherText = cipher.doFinal(TEXTO.getBytes());

            // Imprimimos el mensaje cifrado en Base 64:
            System.out.println(Base64.getEncoder().encodeToString(cipherText));

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
