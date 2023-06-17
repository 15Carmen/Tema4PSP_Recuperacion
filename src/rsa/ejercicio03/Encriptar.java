package rsa.ejercicio03;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

public class Encriptar {

    public static void encriptarFichero(String ruta){

        //Declaramos los arrays de bytes que vamos a utilizar
        byte [] mensajeCifradoPrivada;  //array de bytes que contendrá el mensaje cifrado por la clave privada del emisor
        byte [] mensajeCifradoPublica;  //array de bytes que contendrá el mensaje cifrado por la clave pública del receptor

        try {
            //Inicializamos los cipher con las claves públicas y privadas
            PrivateKey clavePrivadaEmisor = ClavesEmisor.getClavePrivada();
            Cipher cifradorEmisor = Cipher.getInstance("RSA");
            cifradorEmisor.init(Cipher.ENCRYPT_MODE, clavePrivadaEmisor);

            PublicKey clavePublicaReceptor = ClavesReceptor.getClavePublica();
            Cipher cifradorReceptor = Cipher.getInstance("RSA");
            cifradorReceptor.init(Cipher.ENCRYPT_MODE, clavePublicaReceptor);

            //Ciframos el mensaje
            mensajeCifradoPrivada = cifradorEmisor.doFinal(leerFichero(ruta).readAllBytes());
            mensajeCifradoPublica = cifradorReceptor.doFinal(mensajeCifradoPrivada);

            //Guardamos el mensaje cifrado en un fichero
            guardarFichero(mensajeCifradoPublica);
            System.out.println("Mensaje cifrado correctamente");

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
        }
    }

    /**
     * Método que lee un fichero y lo devuelve como un FileInputStream
     * @return Datos del fichero en un FileInputStream
     */
    private static FileInputStream leerFichero (String ruta){

        //Declaramos un FileInputStream para leer el fichero
        FileInputStream fileInputStream = null;

        try {
            //Inicializamos el FileInputStream con la ruta del fichero
            fileInputStream = new FileInputStream(ruta);

        } catch (FileNotFoundException e) { //Si el fichero no existe saltará una excepción
            System.err.println("Fichero no encontrado");
            e.printStackTrace();
        }

        //Devolvemos el FileInputStream
        return fileInputStream;
    }

    /**
     * Método que guarda un array de bytes en un fichero
     * @param mensajeCifrado Array de bytes que se va a guardar en el fichero
     */
    private static void guardarFichero (byte [] mensajeCifrado){

        try {
            //Inicializamos un FileOutputStream con la ruta del fichero
            FileOutputStream fileOutputStream = new FileOutputStream("src/rsa/ejercicio03/ficheroEncriptado.txt");

            //Escribimos el array de bytes en el fichero
            fileOutputStream.write(mensajeCifrado);

            //Cerramos el FileOutputStream
            fileOutputStream.close();

        } catch (IOException e) { //Si hay un error de escritura del fichero saltará una excepción
            System.err.println("Error de escritura del fichero");
            e.printStackTrace();
        }
    }
}