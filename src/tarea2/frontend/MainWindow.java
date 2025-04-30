package tarea2.frontend;

import javax.swing.*;

public class MainWindow extends JFrame{
    private JPanel MainPanel;
    private JTextField textField1;
    private JButton button1;

    public MainWindow(){
        this.setContentPane(this.MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }
}
