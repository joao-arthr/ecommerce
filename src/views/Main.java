package views;

import controllers.*;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args){
        var price = new BigDecimal("2.00");
        var p = new Product(1, "aaa", price,10);

        System.out.println(p.getName());
    }
}
