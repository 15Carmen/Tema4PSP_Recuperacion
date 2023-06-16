package rsa.ejercicio03;

public class Main {

    //Declaramos como constante la ruta del fichero que se va a encriptar y desencriptar
    private static final String INPUT_FILE = "src/rsa/ejercicio03/fichero.txt";

    public static void main(String[] args) {
        try {
            //Generamos las claves pública y privada
            KeysManager.generateKeys();

            //Encriptamos el archivo
            Encriptar.encriptarFichero(INPUT_FILE);

            //Desencriptamos el archivo
            Desencriptar.desencriptarFicheros();

            //Mostramos un mensaje de éxito por consola
            System.out.println("Proceso de encriptación y desencriptación completado con éxito.");
        } catch (Exception e) {

            System.err.println("Error al encriptar o desencriptar el archivo");
            e.printStackTrace();
        }
    }
}

