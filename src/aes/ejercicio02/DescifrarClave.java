package aes.ejercicio02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.Scanner;

/**
 * Clase que descifra un texto cifrado con una clave dada por el usuario
 */
public class DescifrarClave {

    /**
     * Método principal
     *
     * @param args
     */
    public static void main(String[] args) {
        //Declaramos el scanner para leer por teclado
        Scanner sc = new Scanner(System.in);

        //Declaramos las variables que vamos a utilizar
        String clave;  //Variable que almacenará la contraseña que el usuario desea utilizar para descifrar el texto

        //Le pedimos al usuario que introduzca la contraseña que desea utilizar para descifrar el texto
        System.out.println("Introduce la contraseña para descifrar el texto (debe tener 16 caracteres): ");
        clave = sc.nextLine();

        //Mientras que la clave no tenga 16 caracteres, se le pedirá al usuario que la introduzca de nuevo
        while (clave.length() != 16){
            System.out.println("La clave debe tener 16 caracteres. Introduce la contraseña que desea utilizar para descifrar el texto: ");
            clave = sc.nextLine();
        }

        if (clave.equals(Metodos.obtenerClave(clave).toString())) {
            //Mostramos el texto descifrado por pantalla
            System.out.println("El texto descifrado es: ");
            System.out.println(leerTextCifrado(clave));
        }else {
            System.out.println("La clave introducida no es correcta");
        }

        sc.close();
    }

    /**
     * Precondición: El fichero textoCifrado.txt debe existir y contener un texto cifrado
     * Método que lee el texto cifrado de un fichero y lo descifra
     *
     * @param clave clave de descifrado
     * @return texto descifrado
     */
    public static String leerTextCifrado(String clave) {

        //Declaramos las variables
        String textoCifrado = null; //Variable que almacenará el texto cifrado
        Key claveCifrado = Metodos.obtenerClave(clave); //Variable que almacenará la clave de cifrado

        try {
            //Creamos un BufferedReader para leer el fichero
            BufferedReader br = new BufferedReader(new FileReader("src/aes/ejercicio02/textoCifrado.txt"));
            //Leemos el texto cifrado del fichero
            textoCifrado = br.readLine();
            //Cerramos el buffer
            br.close();
        } catch (IOException e) {   //Si no se ha podido leer el fichero, se mostrará un mensaje de error
            System.err.println("No se ha podido recuperar el texto");
            e.printStackTrace();
        }
        //Devolvemos el texto descifrado
        return Metodos.descifrar(textoCifrado,claveCifrado);
    }
}
