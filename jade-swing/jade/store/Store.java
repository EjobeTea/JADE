package store;
import gui.MainWin;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Store{
    public Store(String storeName){ 
        this.storeName = storeName;
        this.products = new ArrayList<>();
        this.person = new ArrayList<>();
    }
    
    //Credit to Prof Rice https://github.com/prof-rice/cse1325-prof
    public Store(BufferedReader in) throws IOException {
        this(in.readLine());
        int size = Integer.parseInt(in.readLine());
        for(int i=0; i<size; i++) {
            String productType = in.readLine();
            switch(productType) {
                case Java.ID:  products.add(new Java(in));  break;
                case Donut.ID: products.add(new Donut(in)); break;
                default: throw new IOException("Invalid product type: " + productType);
            }
        }
        
        size = Integer.parseInt(in.readLine());
        for(int i=0; i<size; i++) {
            String personType = in.readLine();
            switch(personType) {
                case Customer.ID:  person.add(new Customer(in));  break;
                case Server.ID: person.add(new Server(in)); break;
                default: throw new IOException("Invalid person type: " + personType);
            }
        }
    }
    //Credit to Prof Rice:https://github.com/prof-rice
    static Product readProduct(BufferedReader in) throws IOException {
        String productType = in.readLine();
        switch(productType) {
            case Java.ID:  return new Java(in);
            case Donut.ID: return new Donut(in);
            default: throw new IOException("Invalid product type: " + productType);
        }
    }

    public void save(BufferedWriter out) throws IOException {
        out.write(storeName + '\n');
       
        out.write("" + products.size() + '\n');
        for(Product p : products)
            p.save(out);
        
        out.write("" + person.size() + '\n');
        for(Person p : person)
            p.save(out);
    }
    
    
    public void addPerson(Person p){
        person.add(p);
    }
    
    public int numberOfPeople(){
        int size = person.size();
        return size;
    }
    
    public String peopleToString(){
        String per = "";
        for(int i=0; i<person.size(); i++) {
            int j =i+1;
            per += j + ") " + person.get(i) + "\n";
        }
        return per;
    }
    
    public String personToString(int productIndex){
        return person.get(productIndex).toString();
    }
    
    public String storeName(){
        System.out.println("What is the Store Name?:");
        Scanner scan = new Scanner(System.in); 
        storeName = scan.next();
        System.out.println("Welcome to "+storeName+"!");
        return storeName;
    }
    
    public void addProduct(Product product){
        products.add(product);
    }
    
    public int numberOfProducts(){
        int size = products.size();
        return size;
    }
    
    public String toString(int productIndex){
        return products.get(productIndex).toString();
    } 
    
    @Override
    public String toString(){
        //convert arrayList products to product String so it can be returned as a string:
        String productString = " \n";
        int i =0;
        while(i<products.size()) {
            productString += i+1 +") "+products.get(i)+"\n";
            i++;
        }
        return productString;
    }
    //Credit to Prof Rice: https://github.com/prof-rice/cse1325-prof/
    public Object[] getPeople() {
        return person.toArray();
    }
    
    public Object[] getProducts(){
        return products.toArray();   
    }
    protected String storeName;
    protected ArrayList<Person> person = new ArrayList<>();
    protected ArrayList<Product> products = new ArrayList<>(); //filled with donut products and java products, includes our price!
    
}
