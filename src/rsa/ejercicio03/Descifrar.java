package rsa.ejercicio03;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Descifrar {

    // Declaramos una constante con la ruta del fichero encriptado
    public static final String RUTA_FICHERO_ENCRIPTADO = "src/rsa/ejercicio03/ficheroEncriptado.txt";
    public static final String RUTA_FICHERO_DESCIFRADO = "src/rsa/ejercicio03/ficheroDescifrado.txt";

    public static void descifrarFichero() {
        try {
            //Declaramos las claves pública y privada
            PrivateKey clavePrivadaReceptor = ClavesReceptor.getClavePrivada();
            PublicKey clavePublicaEmisor = ClavesEmisor.getClavePublica();

            //Declaramos el cifrador con la clave privada del receptor
            Cipher cipherReceptor = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipherReceptor.init(Cipher.DECRYPT_MODE, clavePrivadaReceptor);

            //Leemos el fichero encriptado
            byte[] mensajeCifradoReceptor = leerFichero(RUTA_FICHERO_ENCRIPTADO);

            //Desciframos el mensaje primero con la clave privada del receptor
            byte[] mensajeCifradoEmisor = descifrarContenido(mensajeCifradoReceptor, clavePrivadaReceptor);
            //Luego desciframos con la clave pública del emisor
            byte[] mensajeDescifrado = descifrarContenido(mensajeCifradoEmisor, clavePublicaEmisor);

            //Guardamos el mensaje descifrado en un fichero
            guardarFichero(mensajeDescifrado, RUTA_FICHERO_DESCIFRADO);

            System.out.println("Mensaje descifrado correctamente");
            System.out.println();   //Salto de línea estético

            //Mostramos el mensaje descifrado por pantalla
            System.out.println("Este es el mensaje secreto: ");
            System.out.println(new String(mensajeDescifrado, StandardCharsets.UTF_8));

        } catch (NoSuchPaddingException e) { //Si el padding no es válido saltará una excepción
            System.err.println("No existe el padding seleccionado");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) { //Si el algoritmo no es válido saltará una excepción
            System.err.println("El algoritmo seleccionado no existe");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) { //Si el tamaño del bloque no es válido saltará una excepción
            System.err.println("El tamaño del bloque utilizado no es correcto");
            e.printStackTrace();
        } catch (BadPaddingException e) { //Si el padding no es válido saltará una excepción
            System.err.println("El padding utilizado es erróneo");
            e.printStackTrace();
        } catch (InvalidKeyException e) { //Si la clave no es válida saltará una excepción
            System.err.println("La clave introducida no es válida");
            e.printStackTrace();
        } catch (IOException e) { //Si hay un error de lectura del fichero saltará una excepción
            System.err.println("Error de lectura del fichero");
            e.printStackTrace();
        }catch (Exception e) {  //Si hay un error al encriptar el fichero saltará una excepción
            System.err.println("Hubo un error al encriptar el fichero");
            e.printStackTrace();
        }
    }

    /**
     * Método que descifrará el contenido del fichero cifrado por bloques
     * @param contenidoCifrado Array de bytes que contiene los datos del fichero cifrado
     * @param clave Clave pública o privada
     * @return contenido descifrado en un array de bytes
     * @throws Exception Excepción que saltará si hay algún error al descifrar el contenido
     */
    public static byte[] descifrarContenido(byte[] contenidoCifrado, Key clave) throws Exception {
        // Crear objeto Cipher
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, clave);

        // Calcular tamaño del bloque
        int tamanoBloque = (((RSAKey) clave).getModulus().bitLength() + 7) / 8;

        // Inicializar buffer de salida
        ByteArrayOutputStream bufferSalida = new ByteArrayOutputStream();

        // Descifrar el contenido en bloques
        int offset = 0;
        while (offset < contenidoCifrado.length) {
            int tamanoBloqueActual = Math.min(tamanoBloque, contenidoCifrado.length - offset);
            byte[] bloqueDescifrado = cipher.doFinal(contenidoCifrado, offset, tamanoBloqueActual);
            bufferSalida.write(bloqueDescifrado);
            offset += tamanoBloqueActual;
        }

        // Devolver contenido descifrado completo
        return bufferSalida.toByteArray();
    }

    /**
     * Método que lee un fichero y lo devuelve como un array de bytes.
     *
     * @param rutaFichero Ruta del fichero a leer
     * @return Contenido del fichero en un array de bytes
     * @throws IOException Si ocurre un error de lectura del fichero
     */
    private static byte[] leerFichero(String rutaFichero) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(rutaFichero);
            return fileInputStream.readAllBytes();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    /**
     * Método que guarda un array de bytes en un fichero.
     *
     * @param contenido Array de bytes que se va a guardar en el fichero
     * @param rutaFichero Ruta del fichero en el que se va a guardar el contenido
     * @throws IOException Si ocurre un error de escritura en el fichero
     */
    private static void guardarFichero(byte[] contenido, String rutaFichero) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(rutaFichero);
            fileOutputStream.write(contenido);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }
}
