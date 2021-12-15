package store;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Donut extends Product{
    public static final String ID = "store.Donut";
    public Donut(String name, double price, double cost, Frosting frosting, boolean sprinkles, Filling filling){
            super(name, price, cost); //ONLY the SUPERCLASS constructs this. Do not need to do super.name = this.name , etc
            this.frosting = frosting;
            this.sprinkles = sprinkles;
            this.filling = filling;
    }
    public Donut(BufferedReader in) throws IOException {
        super(in);
        this.frosting  = Frosting.valueOf(in.readLine());
        this.filling   = Filling.valueOf(in.readLine());
        this.sprinkles = Boolean.parseBoolean(in.readLine()); 
    }
    //Credit to Prof Rice https://github.com/prof-rice/cse1325-prof
    public void save(BufferedWriter out) throws IOException {
        out.write(ID + '\n');
        super.save(out);
        out.write("" + frosting  + '\n');
        out.write("" + filling  + '\n');
        out.write("" + sprinkles + '\n');
    }
    @Override
    public String toString(){
        if(sprinkles ==false){
            return super.toString() + " (Frosted with "+ frosting +", filled with "+ filling + ")";
        }
        else{
            return super.toString() + " (Frosted with "+ frosting +", filled with "+filling + ", " + "and topped with sprinkles";
        }
    }
    protected boolean sprinkles;
    protected Frosting frosting;
    protected Filling filling;
}
