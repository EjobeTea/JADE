package store;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Server extends Person{
    public static final String ID = "store.Server";
    public Server(String name, String phone, String ssn){
        super(name, phone);
    }
    public Server(BufferedReader in) throws IOException{
        super(in);
        ssn = in.readLine();
    }
    @Override
    public void save(BufferedWriter out) throws IOException {
        out.write(ID + '\n');
        super.save(out); 
        out.write(ssn);
    }
    private String ssn;
}
