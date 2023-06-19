package br.com.ecommerce.views;

import static br.com.ecommerce.views.utils.Constants.*;
import static br.com.ecommerce.views.utils.MenuUtils.*;
public class CartMenu {
    protected CartMenu(){
        int choice;
        do {
            System.out.println("---Cart Management---");
            System.out.println("1. View Cart");
            System.out.println("2. Add Products to Cart");
            System.out.println("3. Update Cart");
            System.out.println("4. Total Price");
            System.out.println("5. Confirm Sale");
            System.out.println("0. Back");
            choice = readInteger();

            switch (choice) {
                case 1 -> System.out.println("Você escolheu a Opção 1");

                case 2 -> System.out.println("Você escolheu a Opção 2");

                case 3 -> System.out.println("Você escolheu a Opção 3");

                case 4 -> System.out.println("Você escolheu a Opção 4");

                case 0 -> System.out.println(getReturningMessage());

                default -> System.out.println(getInvalidChoiceMessage());
            }
            System.out.println();
        } while (choice != 0);
    }
}
