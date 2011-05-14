package steinerland;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Gui extends JFrame 
{
    protected Steinerland steinerland;
    protected Menu menu;
    protected Result result;

    public Gui(Steinerland steinerland)
    {
        System.out.println("BAJS!!!");
        this.steinerland = steinerland;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(100, 100, 800, 600);
        setLayout(new BorderLayout());

        // menu / control / input
        menu = new Menu();
        add(menu, BorderLayout.NORTH);
        menu.revalidate();

        // result
        result = new Result();
        add(result, BorderLayout.CENTER);
        result.revalidate();
    }

    public void updateLocations()
    {
        menu.updateLocations();
    }

    protected class Menu extends JPanel implements ActionListener
    {
        protected JLabel fromLabel, toLabel, timeLabel;
        protected JComboBox from, hours, minutes, to;

        public Menu()
        {
            setLayout(new FlowLayout());

            fromLabel = new JLabel("From");
            add(fromLabel);

            from = new JComboBox();
            //from.addActionListener(this);
            add(from);

            timeLabel = new JLabel("At");
            add(timeLabel);

            hours = new JComboBox(new String[]
                    {
                        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                  "11", "12", "13", "14", "15", "16", "17", "18", "20", "21",
                  "22", "23", } );
            //hours.addActionListener(this);
            add(hours);

            minutes = new JComboBox(new String[]
                    {
                        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                    "10", "11", "12", "13", "14", "15", "16", "17", "18", "20",
                    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                    "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                    "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
                    "51", "52", "53", "54", "55", "56", "57", "58", "59", });
            //minutes.addActionListener(this);
            add(minutes);

            toLabel = new JLabel("To");
            add(toLabel);

            to = new JComboBox();
            //to.addActionListener(this);
            add(to);

            JButton search = new JButton("Search");
            search.addActionListener(this);
            add(search);
        }

        protected void updateLocations()
        {
            // for each 
            for (String node : steinerland.getLocations())
            {
                //  add to both dropDowns
                from.addItem(node);
                to.addItem(node);
            }
        }

        /**
         * {@inheritDoc}
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("e=" + e);
            result.set(steinerland.search(from.getSelectedItem().toString(),
                        Short.parseShort((String)hours.getSelectedItem()),
                        Short.parseShort((String)minutes.getSelectedItem()),
                        (String)to.getSelectedItem()));
        }
    }

    protected class Result extends JPanel
    {
        protected JScrollPane scrollPane;
        protected JTextArea text;

        public Result()
        {
            setLayout(new GridLayout(1,1));
            text = new JTextArea();
            scrollPane = new JScrollPane(text);
            add(scrollPane);
        }

        public void set(String result)
        {
            text.setText(result);
        }
    }
}
