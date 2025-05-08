package vistoEnClases.frontend;
import vistoEnClases.bakend.Tablero;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {
    private JPanel MainPanel;
    private JTextField textField1;
    private JButton copiarButton;
    private JLabel textoCopiado;
    private JButton copiarop2;
    private JButton lanzarDados;
    private JLabel valorDados;
    private JTextField nombreJugador;
    private JButton addJugador;
    private JLabel numeroJugadores;
    private Tablero eltablero;

    public MainWindow(Tablero tablero) {
        this.eltablero = tablero;
        this.setContentPane(this.MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.copiarButton.addActionListener(this);
        this.copiarop2.addActionListener(new CopiarListener(textField1,textoCopiado));

        this.lanzarDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado = eltablero.lanzarDados();
                valorDados.setText(String.valueOf(resultado));
            }
        });

        this.addJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreJugador.getText();
                eltablero.agregarJugador(nombre);
                numeroJugadores.setText(String.valueOf(eltablero.getNumeroJugadores()));
            }
        });

        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String texto = this.textField1.getText();
        this.textoCopiado.setText(texto);
    }
}
