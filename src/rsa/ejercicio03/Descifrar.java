package rsa.ejercicio03;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

public class Descifrar {

    public static void descifrarFichero(String ruta){

        //Declaramos los arrays de bytes que vamos a utilizar
        byte [] mensajeDescifradoReceptor;  //array de bytes que contendrá el mensaje descifrado por el receptor
        byte [] mensajeDescifradoEmisor;    //array de bytes que contendrá el mensaje descifrado por el emisor

        try {
            //Inicializamos los cipher con las claves públicas y privadas
            PublicKey clavePublicaEmisor = ClavesEmisor.getClavePublica();
            Cipher cipherEmisor = Cipher.getInstance("RSA");
            cipherEmisor.init(Cipher.DECRYPT_MODE, clavePublicaEmisor);

            PrivateKey clavePrivadaReceptor = ClavesReceptor.getClavePrivada();
            Cipher cipherReceptor = Cipher.getInstance("RSA");
            cipherReceptor.init(Cipher.DECRYPT_MODE, clavePrivadaReceptor);

            //Desciframos el mensaje
            mensajeDescifradoReceptor = cipherReceptor.doFinal(leerFichero(ruta).readAllBytes());
            mensajeDescifradoEmisor = cipherEmisor.doFinal(mensajeDescifradoReceptor);

            //Mostramos el mensaje descifrado por consola y lo guardamos en un fichero
            System.out.println("Este es el mensaje secreto: ");
            System.out.println(new String(mensajeDescifradoEmisor, StandardCharsets.UTF_8));
            guardarFichero(mensajeDescifradoEmisor);
            System.out.println();   //Salto de línea estético
            System.out.println("Mensaje descifrado correctamente");

        } catch (NoSuchPaddingException e) { //Excepción que se lanza cuando no existe el padding seleccionado
            System.err.println("No existe el padding seleccionado");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) { //Excepción que se lanza cuando no existe el algoritmo seleccionado
            System.err.println("El algoritmo seleccionado no existe");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) { //Excepción que se lanza cuando el tamaño del bloque es incorrecto
            System.err.println("El tamaño del bloque utilizado no es correcto");
            e.printStackTrace();
        } catch (BadPaddingException e) { //Excepción que se lanza cuando el padding utilizado es erróneo
            System.err.println("El padding utilizado es erróneo");
            e.printStackTrace();
        } catch (InvalidKeyException e) { //Excepción que se lanza cuando la clave introducida no es válida
            System.err.println("La clave introducida no es válida");
            e.printStackTrace();
        } catch (IOException e) { //Excepción que se lanza cuando no se puede leer el fichero
            System.err.println("La clave introducida no es válida");
            e.printStackTrace();
        }
    }

    /**
     * Método que lee un fichero y lo devuelve como un FileInputStream
     * @return Datos del fichero en un FileInputStream
     */
    private static FileInputStream leerFichero (String ruta){

        //Declaramos el FileInputStream
        FileInputStream fileInputStream = null;

        try {
            //Inicializamos el FileInputStream
            fileInputStream = new FileInputStream(ruta);

        } catch (FileNotFoundException e) { //Si no se encuentra el fichero mostramos un mensaje de error
            System.err.println("Fichero no encontrado");
            e.printStackTrace();
        }

        //Devolvemos el FileInputStream
        return fileInputStream;
    }

    /**
     * Método que guarda un array de bytes en un fichero
     * @param mensajeDescifrado Array de bytes que se va a guardar en el fichero
     */
    private static void guardarFichero (byte [] mensajeDescifrado){

        try {
            //Inicializamos el FileOutputStream
            FileOutputStream fileOutputStream = new FileOutputStream("src/rsa/ejercicio03/ficheroDescifrado.txt");

            //Escribimos el array de bytes en el fichero
            fileOutputStream.write(mensajeDescifrado);

            //Cerramos el FileOutputStream
            fileOutputStream.close();

        } catch (IOException e) { //Si no se puede escribir en el fichero mostramos un mensaje de error
            System.err.println("Error de escritura del fichero");
            e.printStackTrace();
        }
    }
}