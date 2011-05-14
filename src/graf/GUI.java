/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Magnus Grönvall
 */
public class GUI extends JFrame{
    JPanel panel = new JPanel(new BorderLayout());
    JPanel north = new JPanel();

    JTextArea textArea = new JTextArea("lol");

    JLabel lblfrom = new JLabel("from");
    JLabel lblat = new JLabel("At");
    JLabel lblto = new JLabel("to");

    JComboBox boxfrom = new JComboBox();
    JComboBox boxat = new JComboBox();
    JComboBox boxto = new JComboBox();

    JButton search = new JButton("Search");


    public GUI(){
        this.setSize(600, 400);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);    
        this.add(panel);
        panel.add(north, BorderLayout.NORTH);
        panel.add(textArea, BorderLayout.CENTER);
        north.add(lblfrom);
        north.add(boxfrom);
        north.add(lblat);
        north.add(boxat);
        north.add(lblto);
        north.add(boxto);
        north.add(search);
        

        this.setVisible(true);

    }

    public void addFromItem(String from){
        boxfrom.addItem(from);
    }

    public void addToItem(String to){
        boxto.addItem(to);
    }

    public void addAtItem(String at){
        boxat.addItem(at);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

}
