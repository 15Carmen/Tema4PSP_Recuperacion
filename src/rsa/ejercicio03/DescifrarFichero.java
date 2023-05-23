package rsa.ejercicio03;

import javax.crypto.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Scanner;

/**
 * Clase que descifrará un fichero cifrado con RSA
 */
public class DescifrarFichero {
    /**
     * Método principal
     * @param args
     */
    public static void main(String[] args) {

        //Declaramos las variables
        byte [] mensajeDescifradoReceptor;  //Array de bytes que contendrá el mensaje descifrado por el receptor
        byte [] mensajeDescifradoEmisor;    //Array de bytes que contendrá el mensaje descifrado por el emisor

        try {

            //Tomamos la clave privada del receptor
            PrivateKey clavePrivadaReceptor = ReceptorClaves.getClavePrivada();

            Cipher cifradorReceptor = Cipher.getInstance("RSA");
            cifradorReceptor.init(Cipher.DECRYPT_MODE, clavePrivadaReceptor);

            PublicKey clavePublicaEmisor = EmisorClaves.getClavePublica();

            Cipher cifradorEmisor = Cipher.getInstance("RSA");
            cifradorEmisor.init(Cipher.DECRYPT_MODE, clavePublicaEmisor);

            mensajeDescifradoReceptor = cifradorReceptor.doFinal(leerFichero().readAllBytes());
            mensajeDescifradoEmisor = cifradorEmisor.doFinal(mensajeDescifradoReceptor);

            System.out.println("Este es el mensaje secreto: ");
            System.out.println(new String(mensajeDescifradoEmisor, StandardCharsets.UTF_8));

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
            System.err.println("La clave introducida no es válida");
            e.printStackTrace();
        }
    }

    /**
     * Método que lee el fichero a descifrar
     * @return
     */
    private static FileInputStream leerFichero (){

        //Declaramos las variables
        String ruta;

        //Declaramos el scanner para poder leer por consola
        Scanner sc = new Scanner(System.in);

        //Le pedimos al usuario que introduzca la ruta del fichero a descifrar
        System.out.println("Introduce la ruta del fichero a descifrar: ");
        ruta = sc.nextLine();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("src/3jercicio3/mensajeCifrado.txt");
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado");
            e.printStackTrace();
        }
        return fileInputStream;
    }
}
