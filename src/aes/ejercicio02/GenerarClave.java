package aes.ejercicio02;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.Scanner;

/**
 * Clase que genera una clave de cifrado y guarda en un fichero un texto ya cifrado
 */
public class GenerarClave {

    /**
     * Método principal
     *
     * @param args
     */
    public static void main(String[] args) {

        //Declaramos el scanner para leer por teclado
        Scanner sc = new Scanner(System.in);

        //Declaramos las variables que vamos a utilizar
        String texto;     //Variable que almacenará el texto que el usuario desea cifrar
        String clave;  //Variable que almacenará la contraseña que el usuario desea utilizar para cifrar el texto

        //Le pedimos al usuario que introduzca el texto que desea cifrar
        System.out.println("Introduce el texto que desea cifrar: ");
        texto = sc.nextLine();

        //Le pedimos al usuario que introduzca la contraseña que desea utilizar para cifrar el texto
        System.out.println("Introduce la contraseña que desea utilizar para cifrar el texto (debe tener 16 caracteres): ");
        clave = sc.nextLine();

        //Mientras que la clave no tenga 16 caracteres, se le pedirá al usuario que la introduzca de nuevo
        while (clave.length() != 16){
            System.out.println("La clave debe tener 16 caracteres. Introduce la contraseña que desea utilizar para cifrar el texto: ");
            clave = sc.nextLine();
        }

        //Guardamos el texto cifrado en un fichero
       if (guardarTextoCifrado(texto,clave)){
           System.out.println("El texto se ha cifrado correctamente y se ha guardado en el fichero cifrado.txt");
       }

        sc.close();
    }

    /**
     * Método que guarda el texto cifrado en un fichero
     *
     * @param texto texto a cifrar
     * @param clave clave de cifrado
     */
    public static boolean guardarTextoCifrado(String texto, String clave) {

        Key claveCifrado = Metodos.obtenerClave(clave);
        boolean cifradoCorrecto = false;

        try {
            //Creamos un BufferedWriter para escribir en el fichero
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/aes/ejercicio02/textoCifrado.txt"));

            if (claveCifrado != null){
                //Escribimos el texto cifrado en el fichero
                bw.write(Metodos.cifrar(texto,claveCifrado));
                //Hacemos un salto de línea
                bw.newLine();
                //Cerramos el buffer
                bw.close();
                cifradoCorrecto = true;
            } else {    //Si la clave es igual a null lanzamos un mensaje de error
                System.out.println("La clave de cifrado no es correcta");
            }


            //Capturamos las excepciones y mostramos un mensaje de error correspondiente al tipo de excepción
        } catch (IOException e) {
            System.err.println("No se ha podido guardar el texto cifrado en el fichero");
            e.printStackTrace();
        }

        return cifradoCorrecto;
    }
}
