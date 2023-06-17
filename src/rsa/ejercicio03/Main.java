package rsa.ejercicio03;

import java.security.KeyPair;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Declaramso las variables
        int opcion; //Opción del menú

        //Declaramos el scanner para leer la opción del menú
        Scanner sc = new Scanner(System.in);

        //Generamos las claves pública y privada del emisor y del receptor
        KeyPair clavesEmisor = ClavesEmisor.generarClavesEmisor();
        KeyPair clavesReceptor = ClavesReceptor.generarClavesReceptor();

        //Guardamos las claves en un fichero
        ClavesEmisor.guardarClaves(clavesEmisor);
        ClavesReceptor.guardarClaves(clavesReceptor);

        do { //Mientras el usuario no elija la opción de salir

            //Mostramos el menú y leemos la opción elegida por el usuario
            menu();
            opcion = sc.nextInt();

            switch (opcion){
                case 1->{ //Cifrar

                    //Declaramos las variables
                    String rutaFichero; //Ruta del fichero a cifrar

                    //Pedimos la ruta del fichero a cifrar
                    System.out.println("Introduce la ruta del fichero a cifrar:");
                    rutaFichero = sc.next();

                    //Ciframos el fichero
                    Encriptar.encriptarFichero(rutaFichero);
                }

                case 2->{ //Descifrar

                    //Declaramos las variables
                    String rutaFichero; //Ruta del fichero a descifrar

                    //Pedimos la ruta del fichero a descifrar
                    System.out.println("Introduce la ruta del fichero a descifrar:");
                    rutaFichero = sc.next();

                    Descifrar.descifrarFichero(rutaFichero);
                }

                case 3->{ //Salir
                    System.out.println("Adios!");
                }

                default -> System.out.println("Opción no válida");
            }


        }while (opcion != 3);

        //Cerramos el scanner
        sc.close();
    }

    private static void menu(){
        System.out.println("Elige una opción:");
        System.out.println("[1] Cifrar fichero");
        System.out.println("[2] Descifrar fichero");
        System.out.println("[3] Salir");
    }
}
