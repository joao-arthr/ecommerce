package br.com.ecommerce.views.utils;

import java.math.BigDecimal;
import java.util.Scanner;

public class MenuUtils {

    private static final Scanner scanner = new Scanner(System.in);


    private MenuUtils(){
        throw new IllegalStateException("Utility class");
    }



    public static int readInteger(){
        return scanner.nextInt();
    }

    public static String readString(){
        return scanner.nextLine();
    }

    public static BigDecimal readBigDecimal(){
        return new BigDecimal(scanner.nextLine());
    }

    public static void closeScanner() {
        scanner.close();
    }

}
