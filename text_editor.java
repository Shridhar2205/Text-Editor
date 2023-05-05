import javax.swing.*;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
// import javax.swing.JButton;
// import javax.swing.JColorChooser;
// import javax.swing.JComboBox;
// import javax.swing.JFileChooser;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JMenu;
// import javax.swing.JMenuBar;
// import javax.swing.JMenuItem;
// import javax.swing.JOptionPane;
// import javax.swing.JScrollPane;
// import javax.swing.JTextArea;
// import javax.swing.ScrollPaneConstants;
// import javax.swing.text.StyledEditorKit.FontSizeAction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.FlowLayout;
// import java.awt.Font;
// import java.awt.Graphics;
// import java.awt.GraphicsEnvironment;

import java.io.*;
// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.PrintWriter;
import java.util.Scanner;

public class text_editor extends JFrame implements ActionListener{

    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner fontsizeSpinner;
    JLabel fontlabel;
    JButton fontcolorButton;
    JComboBox fontBox;

    JMenuBar menuBar;
    JMenu filMenu;
    JMenu filMenu2;
    JMenuItem openitem;
    JMenuItem saveitem;
    JMenuItem exititem;
    JMenuItem m1;
    JMenuItem m2;
    JMenuItem m3;
    JCheckBox boldCheckBox;
    JCheckBox italicCheckBox;

    text_editor(){
        //bold text
        boldCheckBox = new JCheckBox("Bold");
        boldCheckBox.addActionListener(this);
        this.add(boldCheckBox);

        //italicise the text
        italicCheckBox = new JCheckBox("Italic");
        italicCheckBox.addActionListener(this);
        this.add(italicCheckBox);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Text editor");
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        textArea = new JTextArea();
        // textArea.setPreferredSize(new Dimension(450, 450));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        fontlabel = new JLabel("Font: ");

        //change font size
        fontsizeSpinner = new JSpinner();
        fontsizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontsizeSpinner.setValue(20);
        fontsizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontsizeSpinner.getValue()));
            }
        });

        //change color of text
        fontcolorButton = new JButton("Color");
        fontcolorButton.addActionListener(this);


        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        //change text font
        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        //menu bar
        menuBar = new JMenuBar();
        filMenu = new JMenu("File");
        filMenu2 = new JMenu("Edit");
        m1 = new JMenuItem("cut");
        m2 = new JMenuItem("copy");
        m3 = new JMenuItem("paste");

        openitem = new JMenuItem("Open");
        saveitem = new JMenuItem("Save");
        exititem = new JMenuItem("Exit");
        
        openitem.addActionListener(this);
        saveitem.addActionListener(this);
        exititem.addActionListener(this);
        m1.addActionListener(this);
        m2.addActionListener(this);
        m3.addActionListener(this);

        filMenu.add(openitem);
        filMenu.add(saveitem);
        filMenu.add(exititem);
        filMenu2.add(m1);
        filMenu2.add(m2);
        filMenu2.add(m3);

        menuBar.add(filMenu);
        menuBar.add(filMenu2);
        //menu bar

        this.setJMenuBar(menuBar);
        this.add(fontlabel);
        this.add(fontsizeSpinner);
        this.add(fontcolorButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == boldCheckBox) {
            int style = textArea.getFont().getStyle();
            if (boldCheckBox.isSelected()) {
                style = style | Font.BOLD;
            } else {
                style = style & ~Font.BOLD;
            }
            textArea.setFont(textArea.getFont().deriveFont(style));
        }

        if (e.getSource() == italicCheckBox) {
            int style = textArea.getFont().getStyle();
            if (italicCheckBox.isSelected()) {
                style = style | Font.ITALIC;
            } else {
                style = style & ~Font.ITALIC;
            }
            textArea.setFont(textArea.getFont().deriveFont(style));
        }

        if(e.getSource() == fontcolorButton){
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose a color", Color.black);

            textArea.setForeground(color);
        }

        if(e.getSource() == fontBox){
            textArea.setFont(new Font((String)fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
        }

        if(e.getSource() == openitem){
            JFileChooser j = new JFileChooser("f:");
            j.setCurrentDirectory(new File("."));
            int r = j.showOpenDialog(null);
 
            if (r == JFileChooser.APPROVE_OPTION) {
                File file = new File(j.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                try {
                    fileIn = new Scanner(file);
                    if(file.isFile()){
                        while(fileIn.hasNextLine()){
                            String line = fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                }
                catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                finally{
                    fileIn.close();
                }
            }
        }
        if(e.getSource() == saveitem){
            JFileChooser j1 = new JFileChooser("f:");
            j1.setCurrentDirectory(new File("."));
            int r1 = j1.showSaveDialog(null);

            if (r1 == JFileChooser.APPROVE_OPTION) {

                File file = new File(j1.getSelectedFile().getAbsolutePath());
                PrintWriter fileout = null;

                try {
                    fileout = new PrintWriter(file);
                    fileout.println(textArea.getText());
                }
                catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                finally{
                    fileout.close();
                }
            }
        }
        if(e.getSource() == exititem){
            System.exit(0);   
        }
        if (e.getSource() == m1) {
            textArea.cut();
        }
        else if (e.getSource() == m2) {
            textArea.copy();
        }
        else if (e.getSource() == m3) {
            textArea.paste();
        }
    }
}