package br.com.ecommerce;

import br.com.ecommerce.models.infrastructure.ConnectionDB;
import br.com.ecommerce.views.Menu;



public class Main{

    public static void main(String[] args){
        var db = new ConnectionDB();
        db.checkAndCreateDatabase();
         Menu.Menu();
    }
}
