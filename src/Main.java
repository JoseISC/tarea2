import tarea2.bakend.*;
import tarea2.frontend.PruebaPreguntas;
import javax.swing.*;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        /*-Antiguo de los visto en clases-*/
        //Tablero tablero = new Tablero();
        //MainWindow ventana = new MainWindow(tablero);
        //ventana.setVisible(true);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // De prueba - para ver si funcioan cargar las preuntas desde un .json
        QuizManager quizManager = new QuizManager();
        quizManager.cargarPreguntas(quizManager.leerPreguntasJson());
        //quizManager.devMostrarPreguntas();

        PruebaPreguntas pruebaPreguntas = new PruebaPreguntas(quizManager);
        frame.setContentPane(pruebaPreguntas.getPruebaPanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        pruebaPreguntas.mostrarPregunta(quizManager.getPreguntaActual());



    }
}