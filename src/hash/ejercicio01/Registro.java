package hash.ejercicio01;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Registro {
    public static void main(String[] args) {
        //Declaramos las variables
        String usuario;
        String password;

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

    private static void guardarCredenciales(String nombre, String password) {

        //Declaramos las variables
        byte[] resumen;             //Variable para almacenar el resumen
        String resumenHexadecimal;  //Variable para almacenar el resumen en hexadecimal
        BufferedWriter bw;          //Variable para escribir en el fichero

        //TODO: Que los registros no se borren cada vez que se ejecuta el programa

        try {

            //Obtenemos el resumen de la contraseña
            resumen = CalculoHash.getDigest(password);

            //Convertimos el resumen a hexadecimal
            resumenHexadecimal = String.format("%064x", new BigInteger(1, resumen));

            //Preparo el fichero para escribir
            bw = new BufferedWriter(new FileWriter("credenciales.cre"));

            //Escribo el fichero
            bw.write(nombre + " " + resumenHexadecimal);
            bw.newLine();

            //Cierro el fichero
            bw.close();
        } catch (IOException e) {
            System.err.println("Error al registrar el usuario");
            e.printStackTrace();
        }
    }
}