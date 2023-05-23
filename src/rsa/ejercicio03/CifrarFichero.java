package rsa.ejercicio03;

import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Scanner;

import static javax.crypto.Cipher.getInstance;

public class CifrarFichero {
    public static void main(String[] args) {

        byte [] mensajeCifradoPrivada;
        byte [] mensajeCifradoPublica;

        try {

            //Tomamos la clave privada del emisor
            Key clavePrivadaEmisor = EmisorClaves.getClavePrivada();

            //Creamos el cipher del emisor del mensaje con el algoritmo RSA
            Cipher cifradorEmisor = getInstance("RSA");

            //Desciframos la clave privada
            cifradorEmisor.init(Cipher.ENCRYPT_MODE, clavePrivadaEmisor);

            //Tomamos la clave pública del receptor
           Key clavePublicaReceptor = ReceptorClaves.getClavePublica();

            //Creamos el cipher del receptor con el algoritmo RSA
            Cipher cifradorReceptor = getInstance("RSA");
            cifradorReceptor.init(Cipher.ENCRYPT_MODE, clavePublicaReceptor);

            mensajeCifradoPrivada = cifradorEmisor.doFinal(leerFichero().readAllBytes());
            mensajeCifradoPublica = cifrarContenido(mensajeCifradoPrivada, clavePublicaReceptor);

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
        } catch (Exception e) {
            System.out.println("Error en la entrada/salida de datos");
            e.printStackTrace();
        }
    }

    public static byte[] cifrarContenido(byte[] contenido, Key clave) throws Exception {
        // Crear objeto Cipher
        Cipher cifrador = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // Inicializar cifrador en modo cifrado con la clave proporcionada
        cifrador.init(Cipher.ENCRYPT_MODE, clave);

        // Calcular tamaño del bloque
        int tamanoBloque = (((RSAPublicKey) clave).getModulus().bitLength() + 7) / 8 - 11;

        // Inicializar buffer de salida
        ByteArrayOutputStream bufferSalida = new ByteArrayOutputStream();

        // Cifrar el contenido en bloques
        int offset = 0;
        while (offset < contenido.length) {
            int tamanoBloqueActual = Math.min(tamanoBloque, contenido.length - offset);
            byte[] bloqueCifrado = cifrador.doFinal(contenido, offset, tamanoBloqueActual);
            bufferSalida.write(bloqueCifrado);
            offset += tamanoBloqueActual;
        }

        // Devolver contenido cifrado completo
        return bufferSalida.toByteArray();
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
