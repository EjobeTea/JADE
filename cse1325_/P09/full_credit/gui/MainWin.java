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

//MainWin is now our GUI window!
public class MainWin extends JFrame{ 
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
        JMenu create = new JMenu("Create"); // CREATE -> mjava | mDonut
        JMenu help = new JMenu("Help"); //HELP -> mabout
        
        JMenuItem mquit = new JMenuItem("Quit");
        JMenuItem mabout = new JMenuItem("About");
        mjava = new JMenuItem("New Java");
        mDonut = new JMenuItem("New Donut");
        
        mNew = new JMenuItem("New Store");
        mOpen = new JMenuItem("Open a Store");
        mSave = new JMenuItem("Save Store");
        mSaveAs = new JMenuItem("Save Store As...");
        

        //JLABEL DECLARE ( USE UPDATE DISPLAY TO SET TEXT )
        data = new JLabel(); //declare jlabel
        //data.setText();
        
        add(data, BorderLayout.CENTER);
        
        //MENU BAR PART:
        //each choice leads to helper method / actionlistener
        mquit.addActionListener(event -> System.exit(0)); //LAMBDA used (event ->)
        
        mNew.addActionListener(event -> onNewClick());
        mOpen.addActionListener(event ->onOpenClick());
        mSave.addActionListener(event -> onSaveClick());
        mSaveAs.addActionListener(event -> onSaveAsClick());
        
        mabout.addActionListener(event -> onAboutClick());
        mjava.addActionListener(event -> onCreateJavaClick());
        mDonut.addActionListener(event -> onCreateDonutClick());
        
        file.add(mquit);
        file.add(mNew);
        file.add(mOpen);
        file.add(mSave);
        file.add(mSaveAs);
        
        create.add(mjava);
        create.add(mDonut);
        
        help.add(mabout);
        
        menubar.add(file);
        menubar.add(create);
        menubar.add(help);
        setJMenuBar(menubar);
        


        //TOOL BAR PART:
        
        JToolBar toolbar = new JToolBar("JADE");
        toolbar.add(Box.createHorizontalStrut(25));
        
        // Create the 3 buttons using the icons provided
        JButton buttonNewC  = new JButton("Create a Coffee");
          buttonNewC.setActionCommand("Create Java");
          buttonNewC.setToolTipText("Create a coffee");
          toolbar.add(buttonNewC);
          buttonNewC.addActionListener(event -> onCreateJavaClick());
          
        JButton buttonNewD  = new JButton("Create a Donut");
          buttonNewD.setActionCommand("Create Donut");
          buttonNewD.setToolTipText("Create a donut");
          toolbar.add(buttonNewD);
          buttonNewD.addActionListener(event -> onCreateDonutClick());
          
        JButton JadeB  = new JButton("About");
          JadeB.setActionCommand("Know about JADE");
          JadeB.setToolTipText("Know about JADE");
          toolbar.add(JadeB);
          JadeB.addActionListener(event -> onAboutClick());
        
        toolbar.add(Box.createHorizontalStrut(25));
        getContentPane().add(toolbar, BorderLayout.PAGE_START);
        
    }
    
    
    protected void onSaveAsClick(){
        final JFileChooser fc = new JFileChooser(filename);  // Create a file chooser dialog
        FileFilter jadeFiles = new FileNameExtensionFilter("JadeFiles", "jade");
        fc.addChoosableFileFilter(jadeFiles);         // Add "jade file" filter
        fc.setFileFilter(jadeFiles);                  // Show jade files only by default
        
        int result = fc.showSaveDialog(this);        // Run dialog, return button clicked
        if (result == JFileChooser.APPROVE_OPTION) { // Also CANCEL_OPTION and ERROR_OPTION
            filename = fc.getSelectedFile();         // Obtain the selected File object
            
            if(!filename.getAbsolutePath().endsWith(".jade"))  // Ensure file ends with ".jade"
                filename = new File(filename.getAbsolutePath() + ".jade");
            onSaveClick();
        }
    }
    protected void onSaveClick(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(MAGIC_COOKIE + '\n');
            bw.write(FILE_VERSION + '\n');
            store.save(bw); //BufferedWriter writes products from arrayList into the file.
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to save " + filename + '\n' + e,
                "Failed", JOptionPane.ERROR_MESSAGE); 
        }
    }
    protected void onOpenClick(){
        final JFileChooser fc = new JFileChooser(filename);  // Create a file chooser dialog
        FileFilter jadeFiles = new FileNameExtensionFilter("JadeFiles", "jade");
        fc.addChoosableFileFilter(jadeFiles);         // Add "JADE file" filter
        fc.setFileFilter(jadeFiles);                  // Show JADE files only by default
        
        int result = fc.showOpenDialog(this);        // Run dialog, return button clicked
        if (result == JFileChooser.APPROVE_OPTION) { // Also CANCEL_OPTION and ERROR_OPTION
            File fname = fc.getSelectedFile();        // Obtain the selected File object
            
            try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
                String magicCookie = br.readLine();
                if(!magicCookie.equals(MAGIC_COOKIE)) throw new RuntimeException("Not a jade file");
                String fileVersion = br.readLine();
                if(!fileVersion.equals(FILE_VERSION)) throw new RuntimeException("Incompatible jade file format");
                
                /// LOAD DATA BACK IN ///
                
                Store newStore = new Store(br); // opening a new store!
                store = newStore;
                filename = fname;               
                //updateDisplay();
                }
                catch (Exception e) {
                  JOptionPane.showMessageDialog(this,"Unable to open " + filename + '\n' + e, 
                      "Failed", JOptionPane.ERROR_MESSAGE); 
                }
                updateDisplay();
            
        }
    }
    protected void onNewClick(){
        MainWin newMW = new MainWin("Welcome, again!");
        newMW.setVisible(true);
    }
    
    
    protected void onCreateJavaClick(){
        String name;
        double price, cost;
        price=0;
        cost=0;
        Shot shot;
        Darkness dark;
        name = JOptionPane.showInputDialog(this, "Please enter a coffee name:");
        
        if(name != null){
            name = name;
        }
        else{
            name = "Regular";
            JOptionPane.showMessageDialog(null, "Given: "+name);
        }
        
        try{
            price= Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter a price:"));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Use numbers!");
        }
        
        try{
            cost = Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter the cost:"));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Use numbers!");
        }
        
        
        JComboBox c= new JComboBox();
        c.addItem(Darkness.blond);
        c.addItem(Darkness.light);
        c.addItem(Darkness.medium);
        c.addItem(Darkness.dark);
        c.addItem(Darkness.extreme);
        JOptionPane.showMessageDialog(null, c);
        
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
        
        covefe = new Java(name, price, cost, dark); 

        //ADD SHOTS:
        shot = Shot.none;
        JFrame buttonFrame=new JFrame("Pick a shot"); 
        
        FlowLayout flow = new FlowLayout();

        JButton shotButton1=new JButton("none");  
        JButton shotButton2=new JButton("chocolate");
        JButton shotButton3=new JButton("vanilla");
        JButton shotButton4=new JButton("peppermint");
        JButton shotButton5=new JButton("hazelnut");
        JButton shotButton6=new JButton("Finished");
        
        shotButton1.addActionListener(event -> { 
            covefe.addShot(Shot.none); 
            buttonFrame.dispose(); 
            store.addProduct(covefe);
            updateDisplay();
        }); //closes out after selecting none.
        shotButton2.addActionListener(event -> covefe.addShot(Shot.chocolate));
        shotButton3.addActionListener(event -> covefe.addShot(Shot.vanilla));
        shotButton4.addActionListener(event -> covefe.addShot(Shot.peppermint));   
        shotButton5.addActionListener(event -> covefe.addShot(Shot.hazelnut));   
        shotButton6.addActionListener(event -> {buttonFrame.dispose();
            store.addProduct(covefe);
            updateDisplay();
        }); //closes out, does not need to need none selected

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
    }
    protected void onCreateDonutClick(){
        String name;
        double price, cost;
        price=0;
        cost=0;
        Frosting frosting;
        Filling filling;
        name = JOptionPane.showInputDialog(this, "Please enter a donut name:");
        if(name != null){
            name = name;
        }
        else{
            name = "Regular";
            JOptionPane.showMessageDialog(null, "Given: "+name);
        }
        
        try{
            price= Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter a price:"));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Use numbers!");
        }
        
        try{
            cost = Double.parseDouble(JOptionPane.showInputDialog(this, "Please enter the cost:"));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Use numbers!");
        }
        
        //COMBO BOX
        JComboBox combo = new JComboBox();
        combo.addItem(Frosting.unfrosted);
        combo.addItem(Frosting.chocolate);
        combo.addItem(Frosting.vanilla);
        combo.addItem(Frosting.strawberry);
        JOptionPane.showMessageDialog(null, combo);
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
        
        
        JComboBox box = new JComboBox();
        box.addItem(Filling.unfilled);
        box.addItem(Filling.creme);
        box.addItem(Filling.Bavarian);
        box.addItem(Filling.strawberry);
        JOptionPane.showMessageDialog(null, box);
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
        
        JCheckBox sprinkleBox = new JCheckBox("Sprinkles");
        boolean sprinkles = sprinkleBox.isSelected();
        
        //FORGOT TO IMPLEMENT SPRINKLES ON P07 SPRINT****
        /*
        sprinkleBox.addActionListener(
            event -> {
                sprinkleBox.isSelected();
            });
        add(sprinkleBox);
        */
        
        donut = new Donut(name, price, cost, frosting, sprinkles, filling);
        store.addProduct(donut);
        updateDisplay();  
    }
    protected void onAboutClick(){        
        //EDIT THIS WHEN I ADD PICTURES
        JDialog about = new JDialog();
        about.setLayout(new FlowLayout());
        about.setTitle("Welcome to JADE");
        
        
        JLabel title = new JLabel("<html>"
          + "<p><font size=+4>JADE</font></p>"
          + "</html>");
        about.add(title);

        JLabel artists = new JLabel("<html>"
          + "<p>Version 1.0</p>"
          + "<p>Copyright 2020 by Ethan Jobe</p>"
          + "<p>Licensed under Gnu GPL 3.0</p>"
          + "<p></p>"
          + "<p><font size=-2></font></p>"
          + "<p></p>"
          + "<p><font size=-2></font></p>"
          + "</html>");
        about.add(artists);

        JButton ok = new JButton("OK");
        ok.addActionListener(event -> about.setVisible(false));
        about.add(ok);
        
        about.setSize(300,300);
        about.setVisible(true);

    }
    
    //updateDisplay() is from Professor Rice's code: https://github.com/prof-rice/cse1325-prof/blob/main/P07/full_credit/gui/MainWin.java
    private void updateDisplay() {
        data.setText("<html>" + store.toString()
                                     .replaceAll("<","&lt;")
                                     .replaceAll(">", "&gt;")
                                     .replaceAll("\n", "<br/>")
                              + "</html>");
    }
    
    
    public static void main(String[] args) {
        MainWin App = new MainWin("Welcome");
        App.setVisible(true);
    }
    
    
    private Java covefe;
    private Donut donut;
    
    private Store store;
    
    private JLabel data;
    private JMenuItem mjava;
    private JButton bjava;
    private JMenuItem mDonut;
    private JButton bdonut;
    
    private JMenuItem mNew;
    private JButton bNew;
    private JMenuItem mOpen;
    private JButton bOpen;
    private JMenuItem mSave;
    private JButton bSave;
    private JMenuItem mSaveAs;
    private JButton bSaveAs;
    
    private File filename;
    
    
    private String NAME = "JADE";
    private String VERSION = "1.4J";
    private String FILE_VERSION = "1.0";
    private String MAGIC_COOKIE = "Javad";
}

//fix lines 272, 170-173, line 49-50