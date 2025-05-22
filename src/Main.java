import tarea2.frontend.PruebaPreguntas;
import vistoEnClases.frontend.*;
import vistoEnClases.bakend.Tablero;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {



        /*-Antiguo de los visto en clases-*/
        //Tablero tablero = new Tablero();
        //MainWindow ventana = new MainWindow(tablero);
        //ventana.setVisible(true);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        PruebaPreguntas pruebaPreguntas = new PruebaPreguntas();
        frame.setContentPane(pruebaPreguntas.getPruebaPanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}