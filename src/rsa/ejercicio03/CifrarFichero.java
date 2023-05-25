package rsa.ejercicio03;

import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Scanner;

import static javax.crypto.Cipher.getInstance;

public class CifrarFichero {
    public static void main(String[] args) {

        byte [] mensajeCifradoPrivadaEmisor;
        byte [] mensajeCifradoPublicaReceptor;
        byte [] mensajeFichero = leerFichero().getBytes(StandardCharsets.UTF_8);

        try {

            //Tomamos primero la clave privada del emisor
            PrivateKey clavePrivadaEmisor = EmisorClaves.getClavePrivada();

            //Tomamos la clave pública del receptor
            PublicKey clavePublicaReceptor = ReceptorClaves.getClavePublica();

            //Creamos el cipher del emisor del mensaje con el algoritmo RSA
            Cipher cifradorEmisor = getInstance("RSA/ECB/PKCS1Padding");
            //Ciframos la clave privada del emisor
            cifradorEmisor.init(Cipher.ENCRYPT_MODE, clavePrivadaEmisor);


            //Creamos el cipher del receptor con el algoritmo RSA
            Cipher cifradorReceptor = getInstance("RSA");
            cifradorReceptor.init(Cipher.ENCRYPT_MODE, clavePublicaReceptor);


            mensajeCifradoPrivadaEmisor = cifradorEmisor.doFinal(mensajeFichero);
            mensajeCifradoPublicaReceptor = cifrarContenido(mensajeCifradoPrivadaEmisor, clavePublicaReceptor);

            //Guardamos el mensaje cifrado en un fichero
            guardarFichero(mensajeCifradoPublicaReceptor);


        } catch (NoSuchPaddingException e) {
            System.err.println("No existe el padding seleccionado");
            e.getLocalizedMessage();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("El algoritmo seleccionado no existe");
            e.getLocalizedMessage();
        } catch (IllegalBlockSizeException e) {
            System.err.println("El tamaño del bloque utilizado no es correcto");
            e.getLocalizedMessage();
        } catch (BadPaddingException e) {
            System.err.println("El padding utilizado es erróneo");
            e.getLocalizedMessage();
        } catch (InvalidKeyException e) {
            System.err.println("La clave introducida no es válida para cifrar el fichero");
            e.getLocalizedMessage();
        } catch (IOException e) {
            System.err.println("Error de lectura del fichero");
            e.getLocalizedMessage();
        } catch (Exception e) {
            System.out.println("Error en la entrada/salida de datos");
            e.getLocalizedMessage();
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

    private static String leerFichero(){
        BufferedReader br = null;
        String linea = null;

        try {
            br = new BufferedReader(new FileReader("src/rsa/ejercicio03/prueba.txt"));

            linea = br.readLine();

            while(linea != null) {
                linea += " ";
                linea += br.readLine();
            }

        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el fichero");
            e.getLocalizedMessage();
        } catch (IOException e) {
            System.err.println("Error de lectura del fichero");
            e.getLocalizedMessage();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el fichero");
                e.getLocalizedMessage();
            }
        }

        return linea;

    }

    private static void guardarFichero (byte [] mensajeCifrado){

        BufferedWriter bw = null;
        String mensajeCifradoString = new String(mensajeCifrado, StandardCharsets.UTF_8);
        try {

            bw = new BufferedWriter(new FileWriter("src/rsa/ejercicio03/mensajeCifrado.txt"));
            bw.write(mensajeCifradoString);
            bw.flush();

        } catch (IOException e) {
            System.err.println("Error de escritura del fichero");
            e.getLocalizedMessage();
        }finally {
            try {
                if (bw != null){
                    bw.close();
                }

            } catch (IOException e) {
                System.err.println("Error al cerrar el fichero");
                e.getLocalizedMessage();
            }
        }
    }
}
