package rsa.ejercicio03;

import javax.crypto.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Scanner;

public class CifrarFichero {
    public static void main(String[] args) {

        byte [] mensajeCifradoPrivada;
        byte [] mensajeCifradoPublica;

        try {

            //Tomamos la clave privada del emisor
            PrivateKey clavePrivadaEmisor = EmisorClaves.getClavePrivada();

            Cipher cifradorEmisor = Cipher.getInstance("RSA");

            //Desciframos la clave privada
            cifradorEmisor.init(Cipher.ENCRYPT_MODE, clavePrivadaEmisor);

            PublicKey clavePublicaReceptor = ReceptorClaves.getClavePublica();

            Cipher cifradorReceptor = Cipher.getInstance("RSA");
            cifradorReceptor.init(Cipher.ENCRYPT_MODE, clavePublicaReceptor);

            mensajeCifradoPrivada = cifradorEmisor.doFinal(leerFichero().readAllBytes());
            mensajeCifradoPublica = cifradorReceptor.doFinal(mensajeCifradoPrivada);

            guardarFichero(mensajeCifradoPublica);

            System.out.println("Mensaje cifrado correctamente");

        } catch (NoSuchPaddingException e) {
            System.err.println("No existe el padding seleccionado");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("El algoritmo seleccionado no existe");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.err.println("El tamaño del bloque utilizado no es correcto");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.err.println("El padding utilizado es erróneo");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.err.println("La clave introducida no es válida");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de lectura del fichero");
            e.printStackTrace();
        }
    }
    private static FileInputStream leerFichero (){

        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce la ruta del fichero a cifrar: ");
        String ruta = sc.nextLine();

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(ruta);
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado");
            e.printStackTrace();
        }
        return fileInputStream;
    }

    private static void guardarFichero (byte [] mensajeCifrado){

        try {

            FileOutputStream fileOutputStream = new FileOutputStream("src/Ejercicio3/mensajeCifrado.txt");

            fileOutputStream.write(mensajeCifrado);
            fileOutputStream.close();
        } catch (IOException e) {
            System.err.println("Error de escritura del fichero");
            e.printStackTrace();
        }
    }
}
