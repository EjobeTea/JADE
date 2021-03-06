package gui;
import store.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

import store.*;

//MainWin is now our GUI window!
public class MainWin extends JFrame{ 
    protected enum Display {PRODUCTS, PEOPLE, CHANGE};
    public String nm,ph;
    private String ssn;
    public String name; 
    double price, cost;
    public Frosting frosting;
    public Filling filling;
    public String donutName;
    public double donutPrice, donutCost;
    public String jName;
    public double javaPrice, javaCost;
    public Darkness dark;
    public Shot shot;
    //MainWin CONSTRUCTOR:
    public MainWin(String title) {
        super(title); 
        store = new Store("JADE"); //FIXED. FORGOT TO DECLARE STORE AS NEW OBJECT ON P07***
        filename = new File("untitled.jade");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setResizable(false);
        
        JMenuBar menubar = new JMenuBar();
        
        JMenu file = new JMenu("File"); //FILE -> m
        JMenu edit = new JMenu("Edit");
        JMenu reports = new JMenu("Create Reports");
        JMenu create = new JMenu("Create"); // CREATE -> mjava | mDonut
        JMenu help = new JMenu("Help"); //HELP -> mabout
        
        JMenuItem mquit = new JMenuItem("Quit");
        JMenuItem mabout = new JMenuItem("About");
        mjava = new JMenuItem("New Java");
        mDonut = new JMenuItem("New Donut");
        mCustomer = new JMenuItem("New Customer");
        mServer = new JMenuItem("New Server");
        mOrder = new JMenuItem("New Order");
        
        mEditProduct = new JMenuItem("Change Product");
        
        mReports = new JMenuItem("Profit & Loss");
        
        mNew = new JMenuItem("New Store");
        mOpen = new JMenuItem("Open a Store");
        mSave = new JMenuItem("Save Store");
        mSaveAs = new JMenuItem("Save Store As...");
        
        data = new JLabel();
        
        jTotalCost  = 0; dTotalCost = 0;
        jTotalPrice = 0; dTotalPrice = 0;
        add(data, BorderLayout.CENTER);
        
        //MENU BAR:
        //each choice leads to helper method / actionlistener
        mquit.addActionListener(event -> System.exit(0)); //LAMBDA used (event ->)
        
        mNew.addActionListener(event -> onNewClick());
        mOpen.addActionListener(event ->onOpenClick());
        mSave.addActionListener(event -> onSaveClick());
        mSaveAs.addActionListener(event -> onSaveAsClick());
        
        mabout.addActionListener(event -> onAboutClick());
        mjava.addActionListener(event -> onCreateJavaClick());
        mDonut.addActionListener(event -> onCreateDonutClick());
        mCustomer.addActionListener(event -> onCreateCustomerClick());
        
        mEditProduct.addActionListener(event -> onEditProductClick());
        
        mReports.addActionListener(event -> onReportsClick());
        
        file.add(mquit);
        file.add(mNew);
        file.add(mOpen);
        file.add(mSave);
        file.add(mSaveAs);
        
        create.add(mjava);
        create.add(mDonut);
        create.add(mCustomer);
        
        edit.add(mEditProduct);
        
        reports.add(mReports);
        
        help.add(mabout);
        
        menubar.add(file);
        menubar.add(create);
        menubar.add(edit);
        menubar.add(reports);
        menubar.add(help);
        
        setJMenuBar(menubar);
        


        //TOOL BAR:
        
        JToolBar toolbar = new JToolBar("JADE");
        toolbar.add(Box.createHorizontalStrut(25));
        
        bjava  = new JButton("Create a Coffee");
          bjava.setActionCommand("Create Java");
          bjava.setToolTipText("Create a coffee");
          toolbar.add(bjava);
          bjava.addActionListener(event -> onCreateJavaClick());
          
        bdonut  = new JButton("Create a Donut");
          bdonut.setActionCommand("Create Donut");
          bdonut.setToolTipText("Create a donut");
          toolbar.add(bdonut);
          bdonut.addActionListener(event -> onCreateDonutClick());
          
        bcustomer = new JButton("Create a Customer");
          bcustomer.setActionCommand("Create Customer");
          bcustomer.setToolTipText("Create a customer");
          toolbar.add(bcustomer);
          bcustomer.addActionListener(event -> onCreateCustomerClick());
         
        bserver = new JButton("Create a Server");
          bserver.setActionCommand("Create Customer");
          bserver.setToolTipText("Create a customer");
          toolbar.add(bserver);
          bserver.addActionListener(event -> onCreateServerClick());
          
        border = new JButton("Create an Order");
          border.setActionCommand("Create Customer");
          border.setToolTipText("Create a customer");
          toolbar.add(border);
          border.addActionListener(event -> onCreateOrderClick());
          
        JButton JadeB  = new JButton("About");
          JadeB.setActionCommand("Know about JADE");
          JadeB.setToolTipText("Know about JADE");
          toolbar.add(JadeB);
          JadeB.addActionListener(event -> onAboutClick());
        
        toolbar.add(Box.createHorizontalStrut(25));
        getContentPane().add(toolbar, BorderLayout.PAGE_START);
        
    }
    
    
    protected void onSaveAsClick(){
        final JFileChooser fc = new JFileChooser(filename);
        FileFilter jadeFiles = new FileNameExtensionFilter("JadeFiles", "jade");
        fc.addChoosableFileFilter(jadeFiles);
        fc.setFileFilter(jadeFiles);
        
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) { 
            filename = fc.getSelectedFile();
            
            if(!filename.getAbsolutePath().endsWith(".jade"))  
                filename = new File(filename.getAbsolutePath() + ".jade");
            onSaveClick();
        }
    }
    protected void onSaveClick(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(MAGIC_COOKIE + '\n');
            bw.write(FILE_VERSION + '\n');
            store.save(bw); 
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to save " + filename + '\n' + e,
                "Failed", JOptionPane.ERROR_MESSAGE); 
        }
    }
    protected void onOpenClick(){
        final JFileChooser fc = new JFileChooser(filename);
        FileFilter jadeFiles = new FileNameExtensionFilter("JadeFiles", "jade");
        fc.addChoosableFileFilter(jadeFiles);   
        fc.setFileFilter(jadeFiles);    
        
        int result = fc.showOpenDialog(this);       
        if (result == JFileChooser.APPROVE_OPTION) { 
            File fname = fc.getSelectedFile(); 
            
            try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
                String magicCookie = br.readLine();
                if(!magicCookie.equals(MAGIC_COOKIE)) throw new RuntimeException("Not a jade file");
                String fileVersion = br.readLine();
                if(!fileVersion.equals(FILE_VERSION)) throw new RuntimeException("Incompatible jade file format");
                
                /// LOAD DATA BACK IN ///
                
                Store newStore = new Store(br); 
                store = newStore;
                filename = fname;          
                }
                catch (Exception e) {
                  JOptionPane.showMessageDialog(this,"Unable to open " + filename + '\n' + e, 
                      "Failed", JOptionPane.ERROR_MESSAGE); 
                }
                updateDisplay(Display.PRODUCTS);
                updateDisplay(Display.PEOPLE);
            
        }
    }
    protected void onNewClick(){
        MainWin newMW = new MainWin("Welcome, again!");
        newMW.setVisible(true);
        updateDisplay(Display.PRODUCTS);
    }    
    protected void onCreateServerClick(){
        try{
            JDialog srvDialog = new JDialog();
            srvDialog.setLayout(new GridBagLayout());
            
            GridBagConstraints gc = new GridBagConstraints();
            gc.fill = GridBagConstraints.HORIZONTAL;   
            
            JLabel name = new JLabel("Please enter server's name: "); 
             gc.gridx = 0;
             gc.gridy = 0;
             gc.gridwidth = 1;
             srvDialog.add(name, gc);

            JTextField tfName = new JTextField(20);
             gc.gridx = 1; 
             gc.gridy = 0;
             gc.gridwidth = 2;
             srvDialog.add(tfName, gc);
            
            JLabel phone = new JLabel("Please enter server's phone number: ");
             gc.gridx = 0;
             gc.gridy = 1;
             gc.gridwidth = 1;
             srvDialog.add(phone, gc);

            JTextField tfPhone = new JTextField(20);
             gc.gridx = 1;
             gc.gridy = 1;
             gc.gridwidth = 2;
             srvDialog.add(tfPhone, gc);
             
            JLabel ssnLabel = new JLabel("Please enter ssn: ");
             //String phone = JOptionPane.showInputDialog(this, "Please enter customer's phone number:");
             //String ss;
             gc.gridx = 0;
             gc.gridy = 2;
             gc.gridwidth = 1;
             srvDialog.add(ssnLabel, gc);

            JTextField tfSsn = new JTextField(20);
             gc.gridx = 1;
             gc.gridy = 2;
             gc.gridwidth = 2;
             srvDialog.add(tfSsn, gc);
            
            
            
            JButton ok = new JButton("OK");
             gc.gridx = 1;
             gc.gridy = 3;
             gc.gridwidth = 1;
            ok.addActionListener(event ->{
                //Customer cu = new Customer(nm, ph);
                
                nm = tfName.getText();
                ph = tfPhone.getText();
                ssn = tfSsn.getText();
                if(nm.isEmpty() || ph.isEmpty() || ssn.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Do not leave name or phone number empty. No server created. Try again.");
                    return;
                }
                srv = new Server(nm, ph, ssn);
                store.addPerson(srv); 
                srvDialog.setVisible(false); 
                updateDisplay(Display.PEOPLE);
                } );
            srvDialog.add(ok, gc);    
            
            srvDialog.pack(); 
            srvDialog.setResizable(false);
            srvDialog.setVisible(true);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to create a server.", "Failed", JOptionPane.ERROR_MESSAGE); 
        }
    }
    protected void onCreateOrderClick(){
        try{
            ArrayList<Customer> customers = new ArrayList<>();
            ArrayList<Server> servers = new ArrayList<>();
            
            for(Object p : store.getPeople()) {
                if(p instanceof Customer) customers.add((Customer) p);
                if(p instanceof Server)   servers.add((Server) p);
            }
            
            JDialog orderDialog = new JDialog();
            orderDialog.setLayout(new GridBagLayout());
                
            GridBagConstraints gc = new GridBagConstraints();
            gc.fill = GridBagConstraints.HORIZONTAL;
            
            
            JLabel custLabel = new JLabel("Select customer: "); 
             gc.gridx = 0;
             gc.gridy = 0;
             gc.gridwidth = 1;
             orderDialog.add(custLabel, gc);
            
            JComboBox<Object> selectCustomerBox = new JComboBox<>(customers.toArray());
             gc.gridx = 1;
             gc.gridy = 0;
             gc.gridwidth = 1;
             orderDialog.add(selectCustomerBox, gc);
             
            JLabel serverLabel = new JLabel("Select server: "); 
             gc.gridx = 0;
             gc.gridy = 1;
             gc.gridwidth = 1;
             orderDialog.add(serverLabel, gc);
            
            JComboBox<Object> selectServerBox = new JComboBox<>(servers.toArray());
             gc.gridx = 1;
             gc.gridy = 1;
             gc.gridwidth = 1;
             orderDialog.add(selectServerBox, gc);
            
             
            JButton ok = new JButton("OK");
             gc.gridx = 1;
             gc.gridy = 2;
             gc.gridwidth = 1;
            
            
            Object[] objects = {custLabel, serverLabel, selectCustomerBox, selectServerBox};
            
            //Credit to prof rice: https://github.com/prof-rice/cse1325-prof/
            ok.addActionListener(event ->{
                Order newOrder = new Order((Customer) selectCustomerBox.getSelectedItem(), 
                                           (Server)   selectServerBox.getSelectedItem() );
                updateDisplay(Display.PEOPLE);
                orderDialog.setVisible(false);
            });
            gc.gridx = 1;
            gc.gridy = 2;
            gc.gridwidth = 1;
            orderDialog.add(ok, gc);
            
            orderDialog.pack();
            orderDialog.setResizable(false);
            orderDialog.setVisible(true);
            //orderDialog(Display.PEOPLE);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to create a customer.", "Failed", JOptionPane.ERROR_MESSAGE); 
        }
    }
    protected void onCreateCustomerClick(){
        try{
            JDialog customerDialog = new JDialog();
            customerDialog.setLayout(new GridBagLayout());
            
            GridBagConstraints gc = new GridBagConstraints();
            gc.fill = GridBagConstraints.HORIZONTAL;

            JLabel name = new JLabel("Please enter customer's name: ");
             gc.gridx = 0;
             gc.gridy = 0;
             gc.gridwidth = 1;
             customerDialog.add(name, gc);

            JTextField tfName = new JTextField(20);
             gc.gridx = 1;
             gc.gridy = 0;
             gc.gridwidth = 2;
             customerDialog.add(tfName, gc);
                     
            JLabel phone = new JLabel("Please enter customer's phone number: ");
             gc.gridx = 0;
             gc.gridy = 1;
             gc.gridwidth = 1;
             customerDialog.add(phone, gc);

            JTextField tfPhone = new JTextField(20);
             gc.gridx = 1;
             gc.gridy = 1;
             gc.gridwidth = 2;
             customerDialog.add(tfPhone, gc);
            
            JButton ok = new JButton("OK");
             gc.gridx = 1;
             gc.gridy = 2;
             gc.gridwidth = 1;
            
            ok.addActionListener(event ->{
                nm = tfName.getText();
                ph = tfPhone.getText();
                if(nm.isEmpty() || ph.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Do not leave name or phone number empty. No customer created. Try again.");
                    return;
                }
                cu = new Customer(nm, ph);
                store.addPerson(cu); 
                customerDialog.setVisible(false); 
                updateDisplay(Display.PEOPLE);
            });
            
            customerDialog.add(ok, gc);    
            customerDialog.pack(); 
            customerDialog.setResizable(false);
            customerDialog.setVisible(true);
            updateDisplay(Display.PEOPLE);
        }
       catch(Exception e) {
            JOptionPane.showInputDialog(null, "An error has occurred creating a customer.");
       }
    }
    protected void onCreateJavaClick(){
        try{
            price=0;
            cost=0;
            
            JDialog javaDialog = new JDialog();
            javaDialog.setLayout(new GridBagLayout());
            GridBagConstraints jc = new GridBagConstraints();
            jc.fill = GridBagConstraints.HORIZONTAL;

            JLabel coffeeName = new JLabel("Please enter a coffee name: "); //set location where the words on Jlabel go
             jc.gridx = 0;
             jc.gridy = 0;
             jc.gridwidth = 1;
             javaDialog.add(coffeeName, jc);

            JTextField tfName = new JTextField(20);
             jc.gridx = 1; //set the location of the text field for name
             jc.gridy = 0;
             jc.gridwidth = 2;
            javaDialog.add(tfName, jc);
            name = tfName.getText();

            if(name != null){
                name = name;
            }
            else{
                name = "Regular";
                JOptionPane.showMessageDialog(null, "Given: "+name);
            }
            
            
            JLabel priceLabel = new JLabel("Please enter a price: "); //set location where the words on Jlabel go
             jc.gridx = 0;
             jc.gridy = 1;
             jc.gridwidth = 1;
             javaDialog.add(priceLabel, jc);

            JTextField tfPrice = new JTextField(20);
             jc.gridx = 1; //set the location of the text field for name
             jc.gridy = 1;
             jc.gridwidth = 2;
             javaDialog.add(tfPrice, jc);        
            
            JLabel costLabel = new JLabel("Please enter a cost: "); //set location where the words on Jlabel go
             jc.gridx = 0;
             jc.gridy = 2;
             jc.gridwidth = 1;
             javaDialog.add(costLabel, jc);

            JTextField tfCost = new JTextField(20);
             jc.gridx = 1; //set the location of the text field for name
             jc.gridy = 2;
             jc.gridwidth = 2;
             javaDialog.add(tfCost, jc);

            JLabel darkLabel = new JLabel("Please select a darkness: "); //set location where the words on Jlabel go
             jc.gridx = 0;
             jc.gridy = 3;
             jc.gridwidth = 1;
             javaDialog.add(darkLabel, jc);
             
            JComboBox c = new JComboBox();
            c.addItem(Darkness.blond);
            c.addItem(Darkness.light);
            c.addItem(Darkness.medium);
            c.addItem(Darkness.dark);
            c.addItem(Darkness.extreme);
             jc.gridx = 1;
             jc.gridy = 3;
             jc.gridwidth = 1;
             javaDialog.add(c, jc);
            //JOptionPane.showMessageDialog(null, c);

            dark = Darkness.blond;
            if(c.getSelectedItem() == Darkness.blond){
                dark = Darkness.blond;
            }
            else if(c.getSelectedItem() == Darkness.light){
                dark = Darkness.light;
            }
            else if(c.getSelectedItem() == Darkness.medium){
                dark = Darkness.medium;
            }
            else if(c.getSelectedItem() == Darkness.dark){
                dark = Darkness.dark;
            }
            else if(c.getSelectedItem() == Darkness.extreme){
                dark = Darkness.extreme;
            }
            
            //covefe = new Java(name, price, cost, dark); 

            //ADD SHOTS:
            shot = Shot.none;
            JFrame buttonFrame=new JFrame("Pick a shot"); 
            
            FlowLayout flow = new FlowLayout();

            JButton shotButton1=new JButton("None");  
            JButton shotButton2=new JButton("Chocolate");
            JButton shotButton3=new JButton("Vanilla");
            JButton shotButton4=new JButton("Peppermint");
            JButton shotButton5=new JButton("Hazelnut");
            JButton shotButton6=new JButton("Finished");
            
            shotButton1.addActionListener(event -> { // "none"
                covefe.addShot(Shot.none); 
                buttonFrame.dispose(); 
                store.addProduct(covefe);
                shot = Shot.none;
            }); 
            shotButton2.addActionListener(event -> covefe.addShot(Shot.chocolate));
            shotButton3.addActionListener(event -> covefe.addShot(Shot.vanilla));
            shotButton4.addActionListener(event -> covefe.addShot(Shot.peppermint));   
            shotButton5.addActionListener(event -> covefe.addShot(Shot.hazelnut));   
            shotButton6.addActionListener(event -> { // "Finished"
                buttonFrame.dispose(); 
                store.addProduct(covefe);
            });

            buttonFrame.add(shotButton1);
            buttonFrame.add(shotButton2);
            buttonFrame.add(shotButton3);
            buttonFrame.add(shotButton4);
            buttonFrame.add(shotButton5);
            buttonFrame.add(shotButton6);
            
            buttonFrame.setLayout(flow);
            buttonFrame.setSize(400,200); 
            buttonFrame.setResizable(true);
            buttonFrame.setVisible(true);  
            
            JButton ok = new JButton("OK");
             jc.gridx = 1;
             jc.gridy = 4;
             jc.gridwidth = 1;
            ok.addActionListener(event ->{
                try{
                    jName = tfName.getText();
                    
                    cost = Double.parseDouble(tfCost.getText());
                    jTotalCost +=cost;
                    
                    price = Double.parseDouble(tfPrice.getText());
                    jTotalPrice += price;
                    
                    dark= (Darkness)c.getSelectedItem();
                    
                    covefe = new Java(name, price, cost, dark);
                    
                    //if(covefe.shots.isEmpty()){
                    
                    /*
                    if(!covefe.getShots().equals(Shot.none) || !covefe.getShots().equals(Shot.chocolate) ){
                        JOptionPane.showMessageDialog(null, covefe.getShots());
                        throw new IllegalStateException("No selected shots to coffee!");
                    }
                    */
                }
                catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Enter Values In TextField", "Invalid TextFields", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                catch(IllegalStateException e){
                    JOptionPane.showMessageDialog(null, "Check Second Window to Select Shots", "Invalid Shots Chosen", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                
                javaDialog.setVisible(false);
                buttonFrame.setVisible(false);
                store.addProduct(covefe); 
                updateDisplay(Display.PRODUCTS);
            });
            javaDialog.add(ok, jc);    
            javaDialog.pack(); //PACK window down to most fitting size.
            javaDialog.setResizable(false);
            javaDialog.setVisible(true);
            }
            catch(Exception e) {
               JOptionPane.showMessageDialog(null, "Unable to create a coffee.", "Failed", JOptionPane.ERROR_MESSAGE); 
            }
            
    }
    protected void onCreateDonutClick(){
        try{
            JDialog donutDialog = new JDialog();
            donutDialog.setLayout(new GridBagLayout());
                
            GridBagConstraints gc = new GridBagConstraints();
            gc.fill = GridBagConstraints.HORIZONTAL;
            
            
            JLabel nameLabel = new JLabel("Please enter donut name: "); 
             gc.gridx = 0;
             gc.gridy = 0;
             gc.gridwidth = 1;
             donutDialog.add(nameLabel, gc);

            JTextField tfName = new JTextField(20);
             gc.gridx = 1; 
             gc.gridy = 0;
             gc.gridwidth = 2;
             donutDialog.add(tfName, gc);
            
            JLabel priceLabel = new JLabel("Please enter donut price: "); 
             gc.gridx = 0;
             gc.gridy = 1;
             gc.gridwidth = 1;
             donutDialog.add(priceLabel, gc);

            JTextField tfPrice = new JTextField(20);
             gc.gridx = 1; 
             gc.gridy = 1;
             gc.gridwidth = 2;
             donutDialog.add(tfPrice, gc);
            
            JLabel costLabel = new JLabel("Please enter donut cost: ");
             gc.gridx = 0;
             gc.gridy = 2;
             gc.gridwidth = 1;
             donutDialog.add(costLabel, gc);

            JTextField tfCost = new JTextField(20);
             gc.gridx = 1; 
             gc.gridy = 2;
             gc.gridwidth = 2;
             donutDialog.add(tfCost, gc);
             
             
            JLabel frostLabel = new JLabel("Please enter type of frosting: ");
             gc.gridx = 0;
             gc.gridy = 3;
             gc.gridwidth = 1;
             donutDialog.add(frostLabel, gc);
             
            JComboBox combo = new JComboBox(Frosting.values());
            frosting = Frosting.unfrosted;
            if(combo.getSelectedItem() == Frosting.unfrosted){
                frosting = Frosting.unfrosted;
            }
            else if(combo.getSelectedItem() == Frosting.chocolate){
                frosting = Frosting.chocolate;
            }
            else if(combo.getSelectedItem() == Frosting.vanilla){
                frosting = Frosting.vanilla;
            }
            else if(combo.getSelectedItem() == Frosting.strawberry){
                frosting = Frosting.strawberry;
            }
             gc.gridx = 1;
             gc.gridy = 3;
             gc.gridwidth = 1;
             donutDialog.add(combo, gc);
             
             
            JLabel fillLabel = new JLabel("Please enter type of filling: "); 
             gc.gridx = 0;
             gc.gridy = 4;
             gc.gridwidth = 1;
             donutDialog.add(fillLabel, gc);
             
            JComboBox box = new JComboBox(Filling.values());
            
            filling = Filling.unfilled;
            if(box.getSelectedItem() == Filling.unfilled){
                filling = Filling.unfilled;
            }
            else if(box.getSelectedItem() == Filling.creme){
                filling = Filling.creme;
            }
            else if(box.getSelectedItem() == Filling.Bavarian){
                filling = Filling.Bavarian;
            }
            else if(box.getSelectedItem() == Filling.strawberry){
                filling = Filling.strawberry;
            }
             gc.gridx = 1;
             gc.gridy = 4;
             gc.gridwidth = 1;
             donutDialog.add(box, gc);
            
            JCheckBox sprinkleBox = new JCheckBox("Sprinkles");
            boolean sprinkles = sprinkleBox.isSelected();
            
            //P07 - Sprinkles, never added into final product.
            
            JButton ok = new JButton("OK");
             gc.gridx = 1;
             gc.gridy = 5; 
             gc.gridwidth = 1;
                
            ok.addActionListener(event ->{
                try{
                    donutName = tfName.getText();
                    donutPrice = Double.parseDouble(tfPrice.getText());
                    dTotalPrice += donutPrice;
                    
                    donutCost = Double.parseDouble(tfCost.getText());
                    dTotalCost += donutCost;
                    
                    frosting = (Frosting)combo.getSelectedItem();
                    filling = (Filling)box.getSelectedItem();
                    }
                catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Enter Values In TextField", "Invalid TextFields", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                donut = new Donut(donutName, donutPrice, donutCost, frosting, false , filling);
                //donut = new Donut(name, price, cost, frosting, sprinkles, filling);

                store.addProduct(donut);
                donutDialog.setVisible(false); 
                updateDisplay(Display.PRODUCTS);
            });
            donutDialog.add(ok, gc);    
            donutDialog.pack(); 
            donutDialog.setResizable(false);
            donutDialog.setVisible(true);
            updateDisplay(Display.PRODUCTS); 
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Unable to create a donut.", "Failed", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    protected void onEditProductClick(){
        try{
            JDialog editDialog = new JDialog();
            editDialog.setLayout(new GridBagLayout());
            GridBagConstraints ec = new GridBagConstraints();
            ec.fill = GridBagConstraints.HORIZONTAL;
            JLabel editLabel = new JLabel("Select Product to Edit"); 
             ec.gridx = 0;
             ec.gridy = 1;
             ec.gridwidth = 1;
            editDialog.add(editLabel, ec);
            
            Store s = new Store("Edited" + store);
            JComboBox box = new JComboBox( store.getProducts() );
             ec.gridx = 0;
             ec.gridy = 2;
             ec.gridwidth = 1;
            editDialog.add(box, ec);
            
            JButton change = new JButton("Change it!");
             ec.gridx = 0;
             ec.gridy = 4; 
             ec.gridwidth = 1;
            editDialog.add(change, ec);
            
            final String t = box.getSelectedItem().toString();
            
            if(box.getSelectedItem() instanceof Java){ change.addActionListener(event -> { editDialog.setVisible(false); onCreateJavaClick(); //updateDisplay(Display.CHANGE);
                    data.setText("<html>" + t.replaceAll("<","&lt;")
                                 .replaceAll(">", "&gt;")
                                 .replaceAll("\n", "<br/>")
                              + "</html>");
                });
            }
            else if(box.getSelectedItem() instanceof Donut){
                change.addActionListener(event -> {  editDialog.setVisible(false); onCreateDonutClick();}); 
                updateDisplay(Display.CHANGE);
            }
            
            editDialog.pack();
            editDialog.setResizable(false);
            editDialog.setVisible(true);
            //updateDisplay(Display.PRODUCTS); 
        }
        catch(Exception e) {
           JOptionPane.showMessageDialog(this, "Unable to edit a product.", "Failed", JOptionPane.ERROR_MESSAGE); 
        }
        
    }
    protected void onAboutClick(){
        JDialog about = new JDialog();
        about.setLayout(new FlowLayout());
        about.setTitle("Welcome to JADE");
        
        
        JLabel title = new JLabel("<html>"
          + "<p><font size=+4>JADE</font></p>"
          + "</html>");
        about.add(title);

        JLabel artists = new JLabel("<html>"
          + "<p>Version 1.2</p>"
          + "<p>Copyright 2021 by Ethan Jobe</p>"
          + "<p>Licensed under Gnu GPL 3.0</p>"
          + "<br>" + "<br>"
          + "<p>Special thanks to Professor Rice for being a reference for some of the code implemented.</p>"
          + "<p>Inlcuding, but not limited to: updateDisplay(), Order class, and some File I/O implementation.</p>"
          + "<br>"
          + "<p>Source: https://github.com/prof-rice/cse1325-prof</p>"
          + "<p></p>"
          + "<p><font size=-2></font></p>"
          + "<p></p>"
          + "<p><font size=-2></font></p>"
          + "</html>");
        about.add(artists);

        JButton ok = new JButton("OK");
        ok.addActionListener(event -> about.setVisible(false));
        about.add(ok);
        
        about.setSize(700,400);
        about.setVisible(true);

    }
    protected void onReportsClick(){
        try{
            //DISPLAYS: Product, Income, Costs, and Profit
            JDialog reportDialog = new JDialog();
            reportDialog.setLayout(new FlowLayout());
            reportDialog.setTitle("Report");
            
            
            JLabel reportTitle = new JLabel("<html>"
              + "<p><font size=+2>Profits & Losses Report</font></p>"
              + "</html>");
            reportDialog.add(reportTitle);
            
            
            String js = "Java", ds = "Donuts", ts = "Total";
            double jp = jTotalPrice, jc = jTotalCost, ji =jp-jc;
            double dp = dTotalPrice, dc = dTotalCost, di=dp-dc;
            double tp = jp+dp, tc = jTotalCost+dTotalCost, ti = ji+di;
            JLabel reportTop = new JLabel();
            
            reportTop.setText("<html>"
              + "<p>"  
              + "Products Income\tPrice\tCost"
              + "<br>" 
              + js + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;"
              + ji + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;"
              + jp + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;"
              + jc + " "
              
              + "<br>" 
              + ds + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&thinsp;"
              + di + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;"
              + dp + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;"
              + dc + " "
              
              + "<br>" 
              + ts + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;"  + "&thinsp;"
              + ti + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;"
              + tp + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;"
              + tc + " "
              + "</p>"
              + "</html>");
            reportDialog.add(reportTop);
                 
            JButton ok = new JButton("OK");
            ok.addActionListener(event -> reportDialog.setVisible(false));
            reportDialog.add(ok);
            
            reportDialog.setSize(400,400);
            reportDialog.setVisible(true);
            reportDialog.setResizable(true);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Unable to create a report.", "Failed", JOptionPane.ERROR_MESSAGE); 
        }
    }
    //updateDisplay() is from Professor Rice's code: https://github.com/prof-rice/cse1325-prof/blob/main/P09/full-credit/gui/MainWin.java
    private void updateDisplay(Display dis) {
        String s = "ERROR: Invalid display request: " + dis;
        if(dis == Display.PRODUCTS) s = store.toString();
        if(dis == Display.PEOPLE)  s = store.peopleToString();
        
        data.setText("<html>" + s.replaceAll("<","&lt;")
                                 .replaceAll(">", "&gt;")
                                 .replaceAll("\n", "<br/>")
                              + "</html>");
    }
    
    public static void main(String[] args) {
        MainWin App = new MainWin("Welcome");
        App.setVisible(true);
    }
    
    
    private double jTotalPrice;
    private double jTotalCost;
    private double dTotalPrice;
    private double dTotalCost;
    
    private Java covefe;
    private Donut donut;
    private Person person;
    private Order order;
    private Customer cu;
    private Server srv;
    
    private Store store;
    
    private JLabel data;
    private JMenuItem mjava;
    private JButton bjava;
    private JMenuItem mDonut;
    private JButton bdonut;
    private JMenuItem mCustomer;
    private JButton bcustomer;
    private JMenuItem mServer;
    private JButton bserver;
    private JMenuItem mOrder;
    private JButton border;
    
    private JMenuItem mNew;
    private JButton bNew;
    private JMenuItem mOpen;
    private JButton bOpen;
    private JMenuItem mSave;
    private JButton bSave;
    private JMenuItem mSaveAs;
    private JButton bSaveAs;
    
    private JMenuItem mEditProduct;
    
    private JMenuItem mReports;
    
    private File filename;
    
    private String NAME = "JADE";
    private String VERSION = "1.4J";
    private String FILE_VERSION = "1.2";
    private String MAGIC_COOKIE = "Javad";
}
