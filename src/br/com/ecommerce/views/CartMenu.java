package br.com.ecommerce.views;

import br.com.ecommerce.controllers.Cart;
import br.com.ecommerce.controllers.CartProduct;
import br.com.ecommerce.models.dao.CartDAO;
import br.com.ecommerce.models.dao.CartProductDAO;
import br.com.ecommerce.models.dao.ProductDAO;

import java.math.BigDecimal;
import java.util.List;

import static br.com.ecommerce.views.utils.Constants.*;
import static br.com.ecommerce.views.utils.MenuUtils.*;
public class CartMenu {
    private final ProductDAO p = new ProductDAO();
    private final CartDAO c = new CartDAO();
    private final CartProductDAO cp = new CartProductDAO();

    private static Cart cart;
    protected CartMenu(){
        cart = c.openCartSelect();
        int choice;
        do {
            System.out.println("---Cart Management---");
            System.out.println("1. View Cart");
            System.out.println("2. Add Products to Cart");
            System.out.println("3. Update Product Quantity");
            System.out.println("4. Remove Product");
            System.out.println("5. Total Price");
            System.out.println("6. Confirm Sale");
            System.out.println("0. Back");
            choice = readInteger();

            switch (choice) {
                case 1 -> listCartProducts(cart);

                case 2 -> System.out.println(addCartProductsMenu());

                case 3 -> System.out.println(updateCartProductsMenu());

                case 4 -> System.out.println(removeCartProductsMenu());

                case 5 -> System.out.println(totalPriceMenu());

                case 6 -> confirmSaleMenu();

                case 0 -> System.out.println(getReturningMessage());

                default -> System.out.println(getInvalidChoiceMessage());
            }
            System.out.println();
        } while (choice != 0);
    }


    private String addCartProductsMenu(){
        var cart_product = new CartProduct();
        cart_product.setCart(cart);

        listProducts(false);
        System.out.println("Product ID: ");
        cart_product.setProduct(p.findById(readInteger()));
        System.out.println("Quantity: ");
        cart_product.setQuantityInCart(readInteger());

        var product = cart_product.getProduct();
        product.setQuantity(product.getQuantity() - cart_product.getQuantityInCart());

        if(isCartProductAdded(cart, cart_product)){
            return "This product is already in the cart";
        } else{
            cp.create(cart_product);
            p.update(product);
            return "Product added to cart";
        }

    }

    private String updateCartProductsMenu(){
        var cart_product = new CartProduct();


        cart_product.setCart(cart);

        listCartProducts(cart);
        System.out.println("Product ID: ");
        cart_product.setProduct(p.findById(readInteger()));
        System.out.println("Quantity: ");
        cart_product.setQuantityInCart(readInteger());

        var product = p.findById(cart_product.getProduct().getId());
        var cart_quantity = cp.findById(cart_product.getProduct().getId(), cart.getId()).getQuantityInCart();

        var difference = cart_product.getQuantityInCart() - cart_quantity;
        product.setQuantity(product.getQuantity() - difference);

        if(isCartProductAdded(cart,cart_product)){
            cp.update(cart_product);
            p.update(product);
            return "Product has been updated successfully" ;
        } else{
            return "This product are not in the cart";
        }
    }

    private String removeCartProductsMenu(){
        listCartProducts(cart);
        System.out.println("Product ID: ");
        var product_id = readInteger();

        var cart_product = cp.findById(product_id, cart.getId());

        var product = p.findById(product_id);
        product.setQuantity(product.getQuantity() + cart_product.getQuantityInCart());

        if(isCartProductAdded(cart,cart_product)){
            cp.delete(cart_product.getId());
            p.update(product);
            return "Product has been deleted successfully" ;
        } else{
            return "This product are not in the cart" ;
        }
    }

    private String totalPriceMenu(){
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<CartProduct> cart_products = cart.getCartProducts();
        for (CartProduct cart_product : cart_products) {
            totalPrice = totalPrice.add(cart_product.getProduct().getPrice().multiply(BigDecimal.valueOf(cart_product.getQuantityInCart())));
        }
        return "Total Price: " + totalPrice;
    }

    private void confirmSaleMenu(){
        System.out.println("Do you want to close the purchase? (yes or no) ");
        System.out.println("1. Yes");
        System.out.println("0. No ");
        var confirmation = readInteger();
        do {
            switch (confirmation){
                case 1 -> {
                    System.out.println("Purchase completed! A new cart will be opened.");
                    c.closeCart(cart);
                    confirmation = 0;
                }

                case 0 -> {
                    System.out.println("Back to Shopping");
                    confirmation = 0;
                }

                default -> System.out.println(getInvalidChoiceMessage());
            }
        }while (confirmation != 0);
    }
}
