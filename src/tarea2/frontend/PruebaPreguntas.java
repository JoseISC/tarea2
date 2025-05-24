package tarea2.frontend;

import tarea2.bakend.Pregunta;
import tarea2.bakend.QuizManager;

import javax.swing.*;
import java.awt.*;

public class PruebaPreguntas {

    private PreguntasDisplay preguntasDisplay = new PreguntasDisplay();
    private QuizManager quizManager;

    private JButton siguientePreguntaButton;
    private JPanel preguntasContainerPanel;
    private JLabel numPregunta;

    private JButton anteriorPreguntaButton;
    private JPanel pruebaPanel;

    public PruebaPreguntas(QuizManager quizManager) {
        this.quizManager = quizManager;
    }

    public JButton getAnteriorPreguntaButton() {
        return anteriorPreguntaButton;
    }

    public JButton getSiguientePreguntaButton() {
        return siguientePreguntaButton;
    }

    public JPanel getPreguntasContainerPanel() {
        return preguntasContainerPanel;
    }

    public JLabel getNumPregunta() {
        return numPregunta;
    }

    public JPanel getPruebaPanel() {
        return pruebaPanel;
    }

    public void mostrarPregunta(Pregunta pregunta){
        preguntasContainerPanel.removeAll();

        String numeroPreguntaString = String.valueOf(quizManager.getIndiceActual() + 1);
        numPregunta.setText(numeroPreguntaString + "/" + quizManager.getPreguntas().size());



        preguntasContainerPanel.add(preguntasDisplay.getAlternativasPanel(), BorderLayout.CENTER);
        preguntasDisplay.setPreguntasData(pregunta);


        preguntasContainerPanel.revalidate();
    }

}
