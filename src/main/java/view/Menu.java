package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    private int option;

    public Menu() {
        super();
    }

    public int mainMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        do {

            System.out.println(" \nMENU PRINCIPAL \n");

            System.out.println("1. Crear Tablas ");//Listo
            System.out.println("2. Eliminar Tablas. ");//
            System.out.println("3. Mostrar Tablas");//
            System.out.println("4. Poblar Masivamente las tablas");//
            System.out.println("5. Seleccionar todos los elementos que contengan un texto en concreto");//
            System.out.println("6. Seleccionar un elemento en concreto y modificarlo");//
            System.out.println("7. Modificación de diferentes registros");//
            System.out.println("8. ELimincación de un registro en concreto");//
            System.out.println("9. Eliminación de un conjunto de registros");//

            System.out.println("0. Sortir. ");

            System.out.println("Esculli opció: ");
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no vàlid");
                e.printStackTrace();
            }
        } while (option != 1  && option != 0);

        return option;
    }
}