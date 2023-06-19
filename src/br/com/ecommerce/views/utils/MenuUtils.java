package br.com.ecommerce.views.utils;

import br.com.ecommerce.controllers.Cart;
import br.com.ecommerce.controllers.CartProduct;
import br.com.ecommerce.controllers.Product;
import br.com.ecommerce.models.dao.CartProductDAO;
import br.com.ecommerce.models.dao.ProductDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class MenuUtils {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductDAO p = new ProductDAO();

    private static final CartProductDAO cp = new CartProductDAO();

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

    public static void listProducts(boolean allProducts){
        System.out.println("-List of Products-");
        System.out.println("ID | Name | Price | Quantity" );
        for (Product product : p.list()) {
            if(allProducts){
                System.out.println(product.getId() + " | " + product.getName() + " | " + product.getPrice() +
                        " | " + product.getQuantity());
            }
            else {
                if(product.getQuantity() > 0) {
                    System.out.println(product.getId() + " | " + product.getName() + " | " + product.getPrice() +
                            " | " + product.getQuantity());
                }
            }
        }
    }

    public static void listCartProducts(Cart cart){
        System.out.println("-Cart Products-");
        System.out.println("ID | Name | Quantity | Price" );
        for (CartProduct cartProduct : cp.list(cart)) {
            BigDecimal totalPrice = cartProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(cartProduct.getQuantityInCart()));
            System.out.println(cartProduct.getProduct().getId() + " | "+ cartProduct.getProduct().getName()+ " | " +
                    cartProduct.getQuantityInCart()  + " | "+ totalPrice);
        }
    }

    public static boolean isCartProductAdded(Cart cart, CartProduct product){
        List<CartProduct> cartProducts = cart.getCartProducts();
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProduct().getId() == product.getProduct().getId()) {
                return true;
            }
        }
        return false;
    }

}
