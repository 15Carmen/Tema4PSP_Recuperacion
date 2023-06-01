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
            PrivateKey clavePrivadaEmisor = KeysManager.getClavePrivada(KeysManager.PRIVATE_KEY_FILE_EMISOR);

            //Tomamos la clave pública del receptor
            PublicKey clavePublicaReceptor = KeysManager.getClavePublica(KeysManager.PUBLIC_KEY_FILE_RECEPTOR);

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
            System.out.println(e.getLocalizedMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("El algoritmo seleccionado no existe");
            System.out.println(e.getLocalizedMessage());
        } catch (IllegalBlockSizeException e) {
            System.err.println("El tamaño del bloque utilizado no es correcto");
            System.out.println(e.getLocalizedMessage());
        } catch (BadPaddingException e) {
            System.err.println("El padding utilizado es erróneo");
            System.out.println(e.getLocalizedMessage());
        } catch (InvalidKeyException e) {
            System.err.println("La clave introducida no es válida para cifrar el fichero");
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println("Error de lectura del fichero");
            System.out.println(e.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println("Error en la entrada/salida de datos");
            System.out.println(e.getLocalizedMessage());
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

        // Procesar el texto en bloques del tamaño del bloque
        int offset = 0;
        //Mientras el offset sea menor que el tamaño del contenido
        while (offset < contenido.length) {
            //El tamaño del bloque actual será el mínimo entre el tamaño del bloque y el tamaño del contenido menos el offset
            int tamanoBloqueActual = Math.min(tamanoBloque, contenido.length - offset);
            //Ciframos el contenido desde el offset hasta el tamaño del bloque actual
            byte[] bloqueCifrado = cifrador.doFinal(contenido, offset, tamanoBloqueActual);
            //Escribimos el bloque cifrado en el buffer de salida
            bufferSalida.write(bloqueCifrado);
            //Aumentamos el offset en el tamaño del bloque actual
            offset += tamanoBloqueActual;
        }

        // Devolver contenido cifrado completo
        return bufferSalida.toByteArray();
    }

    private static String leerFichero(){
        BufferedReader br = null;
        String linea = null;
        String texto = null;

        try {
            br = new BufferedReader(new FileReader("src/rsa/ejercicio03/prueba.txt"));

            linea = br.readLine();
            texto = linea;

            while(linea != null) {
                linea = br.readLine();
                texto = texto + "\n" + linea;
            }

        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el fichero");
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println("Error de lectura del fichero");
            System.out.println(e.getLocalizedMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el fichero");
                System.out.println(e.getLocalizedMessage());
            }
        }

        return texto;

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
            System.out.println(e.getLocalizedMessage());
        }finally {
            try {
                if (bw != null){
                    bw.close();
                }

            } catch (IOException e) {
                System.err.println("Error al cerrar el fichero");
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
}
