package rsa.ejercicio03;

import java.security.KeyPair;

public class Main {

    public static void main(String[] args) {

        //Declaramso las variables

        //Generamos las claves pública y privada del emisor y del receptor
        KeyPair clavesEmisor = ClavesEmisor.generarClavesEmisor();
        KeyPair clavesReceptor = ClavesReceptor.generarClavesReceptor();

        //Guardamos las claves en un fichero
        ClavesEmisor.guardarClaves(clavesEmisor);
        ClavesReceptor.guardarClaves(clavesReceptor);

        //Ciframos el fichero
        Encriptar.encriptarFichero();

        //Desciframos el fichero
        Descifrar.descifrarFichero();
    }
}
