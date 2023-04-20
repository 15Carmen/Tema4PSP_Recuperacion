package hash.ejemplos;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class GenerarHash {
    public static void main(String[] args) {

        //Declaramos las variables
        String mensaje;             //Variable para almacenar el mensaje introducido por el usuario
        byte[] mensajeBytes;        //Variable para almacenar el mensaje en bytes
        byte[] resumen = null;      //Variable donde vamos a guardar el mensaje en hash
        String resumenHexadecimal;  //Variable donde vamos a guardar el mensaje en hash pasado a hexadecimal

        //Declaramos el scanner para leer por consola
        Scanner sc = new Scanner(System.in);

        //Le pedimos al usuario que introduzca el mensaje a cifrar
        System.out.println("Introduce el mensaje a cifrar:");
        mensaje = sc.nextLine();

        try {
            // Convierto el mensaje introducido por el usuario en un array de bytes
            mensajeBytes = mensaje.getBytes("UTF-8");

            // Creo una instancia de MessageDigest con el algoritmo SHA-256
            MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");

            // Reiniciamos el objeto por si contiene datos
            algoritmo.reset();

            // Añado el mensaje del cual quiero calcular su hash
            algoritmo.update(mensajeBytes);

            // Generamos el resumen
            resumen = algoritmo.digest();

            // Pasamos el resumen a hexadecimal y lo mostramos por pantalla
            resumenHexadecimal = String.format("%064x", new BigInteger(1, resumen));
            System.out.println(resumenHexadecimal);

            //Capturamos las excepciones
        } catch (NoSuchAlgorithmException e) {
            System.err.println("El algoritmo seleccionado no existe");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.err.println("No se conoce la codificación especificada");
            e.printStackTrace();
        }
    }


}

