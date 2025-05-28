package tarea2.frontend;

import tarea2.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PruebaPreguntas {

    private PreguntasDisplay preguntasDisplay;
    private QuizManager quizManager;

    private JButton siguientePreguntaButton;
    private JButton anteriorPreguntaButton;
    private JPanel preguntasContainerPanel;
    private JLabel numPregunta;
    private JPanel pruebaPanel;

    public PruebaPreguntas(QuizManager quizManager) {
        this.quizManager = quizManager;
        this.preguntasDisplay = new PreguntasDisplay(quizManager);

        // Deshabilitir ir hacia atras y/o adelante.
        anteriorPreguntaButton.setEnabled(false);
        if (this.quizManager.getPreguntas().size() > 1){
            siguientePreguntaButton.setEnabled(true);
        }


        // Avanzar a la siguiente pregunta
        this.siguientePreguntaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                 if(quizManager.avanzar()){
                     mostrarPregunta(quizManager.getPreguntaActual());
                     evaluarRetrocederOAvanzar();
                     if(!quizManager.puedoAvanzar()){
                         siguientePreguntaButton.setText("Enviar respuestas");
                     }
                 }
                 else quizManager.devMostrarRespuestas();

            }
        });


        // Ir a la anterior pregunta.
        anteriorPreguntaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(quizManager.retroceder()){
                    mostrarPregunta(quizManager.getPreguntaActual());
                    evaluarRetrocederOAvanzar();
                    siguientePreguntaButton.setText("Avanzar a la siguiente");
                }
            }
        });
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
        preguntasContainerPanel.repaint();
    }

    public void evaluarRetrocederOAvanzar(){
        //siguientePreguntaButton.setEnabled(quizManager.puedoAvanzar());
        anteriorPreguntaButton.setEnabled(quizManager.puedoRetroceder());
    }

}
