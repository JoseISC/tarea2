package tarea2.frontend;

import tarea2.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
                else{
                    // Validar que todas las preguntas estén respondidas antes de finalizar
                    if (!quizManager.todasLasPreguntasRespondidas()) {
                        List<Integer> sinResponder = quizManager.getPreguntasSinResponder();
                        int cantidadSinResponder = sinResponder.size();
                        int cantidadRespondidas = quizManager.getCantidadPreguntasRespondidas();
                        
                        String mensaje = "Tienes " + cantidadSinResponder + " pregunta(s) sin responder:\n";
                        mensaje += "Preguntas sin responder: " + sinResponder.toString() + "\n";
                        mensaje += "Progreso: " + cantidadRespondidas + "/" + quizManager.getPreguntas().size() + "\n\n";
                        mensaje += "¿Deseas finalizar el quiz de todas formas?";
                        
                        int opcion = JOptionPane.showConfirmDialog(
                            siguientePreguntaButton.getParent(),
                            mensaje,
                            "Preguntas sin responder",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                        );
                        
                        if (opcion != JOptionPane.YES_OPTION) {
                            return; // No finalizar el quiz
                        }
                    }
                    
                    mostrarResultados();
                    quizManager.devMostrarRespuestas();
                }
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
        int preguntasRespondidas = quizManager.getCantidadPreguntasRespondidas();
        int totalPreguntas = quizManager.getPreguntas().size();
        
        // Formato: "3/5 (4 respondidas)"
        String textoProgreso = numeroPreguntaString + "/" + totalPreguntas + 
                              " (" + preguntasRespondidas + " respondidas)";
        numPregunta.setText(textoProgreso);

        preguntasContainerPanel.add(preguntasDisplay.getAlternativasPanel(), BorderLayout.CENTER);
        preguntasDisplay.setPreguntasData(pregunta);

        preguntasContainerPanel.revalidate();
        preguntasContainerPanel.repaint();
    }

    public void evaluarRetrocederOAvanzar(){
        //siguientePreguntaButton.setEnabled(quizManager.puedoAvanzar());
        anteriorPreguntaButton.setEnabled(quizManager.puedoRetroceder());
    }

    public void mostrarResultados(){
        Resultados resultadosPantalla = new Resultados(quizManager);

        //quizManager.iniciarPrueba();
        // Create a new frame to display the quiz
        JFrame frame = new JFrame("Resultados");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        // Add the component to the frame
        frame.add(resultadosPantalla.getResultadosPanel());

        Component top = this.getPruebaPanel().getTopLevelAncestor();
        if (top instanceof JFrame) {
            ((JFrame) top).dispose();
        }

        // Make the new frame visible
        frame.setVisible(true);

        // Initialize the first question
        //prueba.mostrarPregunta(quizManager.getPreguntaActual());
    }

}
