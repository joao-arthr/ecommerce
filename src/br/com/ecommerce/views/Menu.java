package br.com.ecommerce.views;

import static br.com.ecommerce.views.utils.Constants.*;
import static br.com.ecommerce.views.utils.MenuUtils.*;

public class Menu {

    public static void Menu(){
        int choice;
        do {
            System.out.println("---E-commerce---");
            System.out.println("1. Product Management");
            System.out.println("2. Cart Management");
            System.out.println("0. Exit");
            choice = readInteger();

            switch (choice) {
                case 1 -> new ProductMenu();

                case 2 -> new CartMenu();

                case 0 -> System.out.println("Exiting");
                default -> System.out.println(getInvalidChoiceMessage());
            }
            System.out.println();
        } while (choice != 0);

        closeScanner();
    }




}
