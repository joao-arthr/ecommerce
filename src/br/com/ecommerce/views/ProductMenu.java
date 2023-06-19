package br.com.ecommerce.views;

import br.com.ecommerce.controllers.Product;
import br.com.ecommerce.models.dao.ProductDAO;

import static br.com.ecommerce.views.utils.Constants.*;
import static br.com.ecommerce.views.utils.MenuUtils.*;


public class ProductMenu {
    private final ProductDAO p  = new ProductDAO();

    protected ProductMenu(){
        int choice;
        do {
            System.out.println("---Product Management---");
            System.out.println("1. List Products");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("0. Back");
            choice = readInteger();
            readString();

            switch (choice) {
                case 1 -> listProducts(true);

                case 2 -> System.out.println(addProductMenu());

                case 3 -> System.out.println(updateProductMenu());

                case 4 -> System.out.println(deleteProductMenu());

                case 0 -> System.out.println(getReturningMessage());

                default -> System.out.println(getInvalidChoiceMessage());
            }
            System.out.println();
        } while (choice != 0);
    }



    private String addProductMenu(){
        String returnMessage = null;
        System.out.println("-Add new Product-");
        try{
            System.out.println("Name: ");
            var name = readString();
            System.out.println("Price: ");
            var price = readBigDecimal();
            System.out.println("Quantity: ");
            var quantity = readInteger();

            p.create(new Product(0, name, price, quantity));
            returnMessage = "Product inserted successfully";
        }catch(Exception e){
            returnMessage =  e.getMessage();
        }
        return returnMessage;
    }

        private String updateProductMenu(){
            String returnMessage = null;
            listProducts(true);
            System.out.println("-Update Product-");
            System.out.println("Product ID: ");
            var product = p.findById(readInteger());
            readString();
            System.out.println(product.toString());

            try{
                System.out.println("Name: ");
                product.setName(readString());
                System.out.println("Price: ");
                product.setPrice(readBigDecimal());
                System.out.println("Quantity: ");
                product.setQuantity(readInteger());

                p.update(product);
                returnMessage = "Product updated successfully";
            } catch (Exception e){
                returnMessage = e.getMessage();
            }
            return returnMessage;
        }

    private String deleteProductMenu(){
        String returnMessage = null;
        listProducts(true);
        System.out.println("-Delete Product-");
        System.out.println("Product ID: ");
        try{
            p.delete(readInteger());
            returnMessage = "Product deleted successfully";
        }catch (Exception e){
            returnMessage = e.getMessage();
        }
        return returnMessage;
    }

}
