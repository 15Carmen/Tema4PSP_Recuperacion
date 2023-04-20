package hash.ejercicio01;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CalculoHash {

    /**
     * Método que compara el resumen del fichero con el resumen introducido por teclado
     * @param resumenFichero Resumen del fichero
     * @param resumenIntroducidoTeclado Resumen introducido por teclado
     * @return Devuelve true si son iguales, false si no lo son
     */
    public static boolean compararResumenes(String resumenFichero, String resumenIntroducidoTeclado){
        return resumenFichero.equals(resumenIntroducidoTeclado);
    }

    /**
     * Método que calcula el resumen de un mensaje
     * @param mensajeACodificar Mensaje que se quiere codificar
     * @return Devuelve el resumen del mensaje
     */
    public static byte[] getDigest(String mensajeACodificar){

        //Declaramos las variables
        MessageDigest algoritmo;            //Algoritmo de codificación
        byte[] byteMensaje;                 //Array de bytes del mensaje
        byte[] resumen = new byte[0];       //Array de bytes del resumen

        try {
            //Saco los bytes del mensaje
            byteMensaje = mensajeACodificar.getBytes(StandardCharsets.UTF_8);

            //Obtengo el algoritmo de codificación
            algoritmo = MessageDigest.getInstance("SHA-256");

            //Reseteo el algoritmo
            algoritmo.reset();

            //Actualizo el algoritmo con el mensaje
            algoritmo.update(byteMensaje);

            //Guardo el resumen obtenido
            resumen = algoritmo.digest();

        } catch (NoSuchAlgorithmException e) {
            System.err.println("El algoritmo seleccionado no existe");
            e.printStackTrace();
        }

        //Devuelvo el resumen
        return resumen;
    }
}
