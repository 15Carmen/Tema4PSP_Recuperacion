package rsa.ejercicio03;

import javax.crypto.*;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Scanner;

/**
 * Clase que descifrará un fichero cifrado con RSA
 */
public class DescifrarFichero {
    /**
     * Método principal
     *
     * @param args
     */
    public static void main(String[] args) {

        //Declaramos las variables
        byte[] mensajeDescifradoPublicoEmisor;  //Array de bytes que contendrá el mensaje descifrado por el receptor
        byte[] mensajeDescifradoPrivadoReceptor;    //Array de bytes que contendrá el mensaje descifrado por el emisor

        try {

            //Tomamos la clave privada del receptor
            PrivateKey clavePrivadaReceptor = KeysManager.getClavePrivada(KeysManager.PRIVATE_KEY_FILE_RECEPTOR);
            PublicKey clavePublicaEmisor = KeysManager.getClavePublica(KeysManager.PUBLIC_KEY_FILE_EMISOR);

            Cipher cifradorEmisor = Cipher.getInstance("RSA");
            cifradorEmisor.init(Cipher.DECRYPT_MODE, clavePublicaEmisor);

            Cipher cifradorReceptor = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cifradorReceptor.init(Cipher.DECRYPT_MODE, clavePrivadaReceptor);

            mensajeDescifradoPrivadoReceptor = descifrarContenido(leerFichero().readAllBytes(), clavePrivadaReceptor);
            mensajeDescifradoPublicoEmisor = cifradorEmisor.doFinal(mensajeDescifradoPrivadoReceptor);


            System.out.println("Este es el mensaje secreto: ");
            System.out.println(new String(mensajeDescifradoPublicoEmisor, StandardCharsets.UTF_8));

        } catch (NoSuchPaddingException e) {
            System.err.println("No existe el padding seleccionado");
            System.out.println(e.getLocalizedMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("El algoritmo seleccionado no existe");
            System.out.println(e.getLocalizedMessage());
        } catch (IllegalBlockSizeException e) {
            System.err.println("El tamaño del bloque utilizado no es correcto");
            System.out.println(e.getLocalizedMessage());
        } catch (BadPaddingException e) {
            System.err.println("El padding utilizado es erróneo");
            System.out.println(e.getLocalizedMessage());
        } catch (InvalidKeyException e) {
            System.err.println("La clave introducida no es válida para descifrar el fichero");
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println("La clave introducida no es válida");
            System.out.println(e.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println("Error en la entrada/salida de datos");
            System.out.println(e.getLocalizedMessage());
        }
    }

    private static byte[] descifrarContenido(byte[] contenido, Key clave) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        cipher.init(Cipher.DECRYPT_MODE, clave);

        // Calcular tamaño del bloque
        int tamanoBloque = (((RSAPrivateKey) clave).getModulus().bitLength() + 7) / 8 - 11;

        // Inicializar buffer de salida
        ByteArrayOutputStream bufferSalida = new ByteArrayOutputStream();

        // Procesar el texto en bloques del tamaño del bloque
        int offset = 0;
        //Mientras el offset sea menor que el tamaño del contenido
        while (offset < contenido.length) {
            //El tamaño del bloque actual será el mínimo entre el tamaño del bloque y el tamaño del contenido menos el offset
            int tamanoBloqueActual = Math.min(tamanoBloque, contenido.length - offset);
            //Ciframos el contenido desde el offset hasta el tamaño del bloque actual
            byte[] bloqueDescifrado = cipher.doFinal(contenido, offset, tamanoBloqueActual);
            //Escribimos el bloque cifrado en el buffer de salida
            bufferSalida.write(bloqueDescifrado);
            //Aumentamos el offset en el tamaño del bloque actual
            offset += tamanoBloqueActual;
        }

        // Devolver contenido cifrado completo
        return bufferSalida.toByteArray();
    }

    /**
     * Método que lee el fichero a descifrar
     *
     * @return
     */
    private static FileInputStream leerFichero() {

        //Declaramos las variables
        String ruta;

        //Declaramos el scanner para poder leer por consola
        Scanner sc = new Scanner(System.in);

        //Le pedimos al usuario que introduzca la ruta del fichero a descifrar
        System.out.println("Introduce la ruta del fichero a descifrar: ");
        ruta = sc.nextLine();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("src/rsa/ejercicio03/" + ruta);
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado");
            System.out.println(e.getLocalizedMessage());
        }
        return fileInputStream;
    }
}
