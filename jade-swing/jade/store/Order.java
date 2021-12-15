package store;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.swing.*;
import java.io.IOException;

//Credits to Professor Rice for creating half of this Order Class: https://github.com/prof-rice/cse1325-prof

public class Order{ 
    public static final String ID = "store.Order";
    
    public Order(Customer customer, Server server){
        this.id = nextID++;
        this.customer = customer;
        this.server = server;
    }
    public int getID(){
        return id;
    }
    
    public Order(BufferedReader in) throws IOException{
        //Credit to Prof Rice: https://github.com/prof-rice/cse1325-prof
        products = new HashMap<>();
        
        id = Integer.parseInt(in.readLine());
        
        if(id >= nextID){nextID = id+1;}
        customer = new Customer(in);
        server = new Server(in);
        
        int size = Integer.parseInt(in.readLine());
        for(int i=0; i<size; ++i) {
            int quantity = Integer.parseInt(in.readLine());
            Product product = Store.readProduct(in);
            products.put(product, quantity);
        }
    }
    public void save(BufferedWriter out) throws IOException{
        out.write(ID + '\n');
        customer.save(out);
        server.save(out);
        out.write("" + products.size() + '\n');
        for(var product : products.keySet()) {
            out.write("" + products.get(product) + "\n");
            product.save(out);
        }
    }
    
    //Credit to Prof Rice: https://github.com/prof-rice/cse1325-prof
    public void addProduct(int quantity, Product product){
        if(products.containsKey(product)) quantity += products.get(product);
        products.put(product, quantity);
    }
    
    //Credit to Prof Rice: https://github.com/prof-rice/cse1325-prof
    @Override
    public String toString() {
        String result = "Order " + id + " for " + customer
            + "\n    Server: " + server + "\n";
        double price = 0.00;
        for(var product : products.keySet()) {
            result += String.format(formatProduct, products.get(product), product);
            price += product.price();
        }
        result += String.format(formatPrice, price);
        return result;
    }
    
    private static int nextID = 0;
    private static int id = 0;
    private static String formatProduct = "%3d  %s\n"; 
    private static String formatPrice = "Total price: $%5.2f\n"; 
    
    private HashMap<Product, Integer> products;
    
    private Customer customer;
    private Server server;
}

