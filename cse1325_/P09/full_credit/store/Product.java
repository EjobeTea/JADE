package store;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

abstract class Product{
    //constructor
    
    public Product(String name, double price, double cost){
        this.name = name;
        this.price = price;
        this.cost = cost;
    }
    
    public Product(BufferedReader in) throws IOException {
        this.name  = in.readLine();
        this.cost  = Double.parseDouble(in.readLine());
        this.price = Double.parseDouble(in.readLine());
    }
    
    
    public void save(BufferedWriter out) throws IOException {
        out.write("" + name  + '\n');
        out.write("" + cost  + '\n');
        out.write("" + price + '\n');
    }
    
    public String name(){
        return name;
    }
    
    @Override
    public String toString(){
        return "$"+price + " "+name;
    }
    protected String name;
    protected double price;
    protected double cost;
}

