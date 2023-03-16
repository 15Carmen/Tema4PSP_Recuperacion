package hash.ejercicio01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Validar {
    public static void main(String[] args) {

        //Declaramos las variables
        String usuarioValidar;      //Variable para almacenar el nombre de usuario a validar
        String passwordValidar;     //Variable para almacenar la contraseña a validar

        //Declaramos el scanner
        Scanner sc = new Scanner(System.in);

        //Le pedimos al usuario que introduzca su nombre y su contraseña
        System.out.println("Introduce tu nombre de usuario: ");
        usuarioValidar = sc.next();
        System.out.println("Introduce tu password: ");
        passwordValidar = sc.next();

        //Si el usuario y la contraseña son válidos, imprimimos un mensaje de bienvenida
        if (validar(usuarioValidar, passwordValidar)) {
            System.out.println("Acceso validado, bienvenido");
        } else { //En caso contrario se lo informamos al usuario
            System.out.println("Acceso denegado, usuario o password incorrectas");
        }

    }

    /**
     * Método que comprueba si el
     * @param usuario  y la
     * @param password son iguales a los sacados del fichero credenciales.cre
     * @return true si son iguales y false si no lo son
     */
    private static boolean validar(String usuario, String password) {

        //Declaracion de variables
        BufferedReader br;      //Variable para leer el fichero
        String linea;                       //Variable para almacenar la linea leida del fichero
        boolean esValido = false;           //Variable para almacenar si el usuario y la contraseña son válidos
        byte[] resumenValidar;              //Variable para almacenar el resumen de la contraseña
        String resumenValidarHexadecimal;   //Variable para almacenar el resumen de la contraseña en hexadecimal

        try {
            //Paso la contraseña a resumenValidar con el método getDigest de la clase Coder
            resumenValidar = CalculoHash.getDigest(password);

            //Paso el resumenValidar a hexadecimal para poder compararlo con el resumen (hash) del fichero
            resumenValidarHexadecimal = String.format("%064x", new BigInteger(1, resumenValidar));

            //Preparo la lectura del fichero
            br = new BufferedReader(new FileReader("credenciales.cre"));

            linea = br.readLine();
            while (linea != null) {      //Mientras se lea una linea en el fichero

                //Si el usuario y el resumen de la contraseña son iguales a los del fichero, esValido es true
                if (CalculoHash.compararResumenes(linea.split(" ")[0], usuario) &&
                        CalculoHash.compararResumenes(linea.split(" ")[1], resumenValidarHexadecimal)) {
                    esValido = true;
                    break;
                }

                linea = br.readLine();  //Leemos la siguiente línea
            }
        } catch (IOException e) {
            System.err.println("Error, archivo no encontrado");
            e.printStackTrace();
        }

        //Devolvemos el valor de esValido
        return esValido;

    }
}
