package hash.ejercicio01;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Registro {
    /**
     * Metodo principal
     * @param args
     */
    public static void main(String[] args) {
        //Declaramos las variables
        String usuario;     //Variable para almacenar el nombre de usuario
        String password;    //Variable para almacenar la contraseña

        //Declaramos el scanner
        Scanner sc = new Scanner(System.in);

        //Le pedimos al usuario que introduzca su nombre de usuario y contraseña
        System.out.println("Introduce tu nombre de usuario: ");
        usuario = sc.next();
        System.out.println("Introduce tu password: ");
        password = sc.next();

        //Guardamos en un fichero los datos introducidos por el usuario
        guardarCredenciales(usuario, password);

    }

    /**
     * Método que guarda en un fichero las credenciales del usuario
     * @param nombre Nombre de usuario
     * @param password Contraseña
     */
    private static void guardarCredenciales(String nombre, String password) {

        //Declaramos las variables
        byte[] resumen;             //Variable para almacenar el resumen
        String resumenHexadecimal;  //Variable para almacenar el resumen en hexadecimal
        BufferedWriter bw;          //Variable para escribir en el fichero

        try {

            //Obtenemos el resumen de la contraseña
            resumen = CalculoHash.getDigest(password);

            //Convertimos el resumen a hexadecimal
            resumenHexadecimal = String.format("%064x", new BigInteger(1, resumen));

            //Preparo el fichero para escribir
            bw = new BufferedWriter(new FileWriter("credenciales.cre", true));

            //Escribo el fichero
            bw.write(nombre + " " + resumenHexadecimal);
            bw.newLine();
            bw.flush();

            //Cierro el fichero
            bw.close();
        } catch (IOException e) {
            System.err.println("Error al registrar el usuario");
            e.printStackTrace();
        }
    }
}