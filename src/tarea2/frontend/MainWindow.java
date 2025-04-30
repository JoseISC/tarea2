package tarea2.frontend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {
    private JPanel MainPanel;
    private JTextField textField1;
    private JButton copiarButton;
    private JLabel textoCopiado;

    public MainWindow(){
        this.setContentPane(this.MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.copiarButton.addActionListener(this);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String texto = this.textField1.getText();
        this.textoCopiado.setText(texto);
    }
}
